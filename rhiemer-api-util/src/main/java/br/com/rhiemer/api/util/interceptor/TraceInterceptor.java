package br.com.rhiemer.api.util.interceptor;

import static br.com.rhiemer.api.util.constantes.SequencialInterceptor.TRACE;

import java.util.Arrays;
import java.util.UUID;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;

import br.com.rhiemer.api.util.annotations.app.ProxyBuilderAplicacao;
import br.com.rhiemer.api.util.annotations.interceptor.SemTrace;
import br.com.rhiemer.api.util.annotations.interceptor.Trace;
import br.com.rhiemer.api.util.cdi.CDIUtil;
import br.com.rhiemer.api.util.enums.EnumTypeEventoSessao;
import br.com.rhiemer.api.util.log.LogAplicacao;
import br.com.rhiemer.api.util.proxy.ProxyMetodoBuilder;
import br.com.rhiemer.api.util.thread.LocalThreadContextAPI;
import br.com.rhiemer.api.util.thread.SessionThreadContextAPI;
import br.com.rhiemer.api.util.trace.AdvancedStopWatch;
import br.com.rhiemer.api.util.trace.EventoSessaoDto;
import br.com.rhiemer.api.util.trace.TransactionContext;

@Interceptor
@Trace
@Priority(TRACE)
public class TraceInterceptor {

	private static final String SUCESSO = "SUCESSO";
	private static final String FALHA = "FALHA";
	private static final String FORMAT_METHOD = "%s%s";
	private static final String FORMAT_LOG = "%s|Requisição %s|Execução %s";
	private static final String FORMAT_DEBUG_FALHA = "ERRO_MEIO-> %s|Exceção %s|Causa %s";
	private static final String CALLING = "EXECUTANDO -> %s";
	private static final String SUCESS_FORMAT = "SUCESSO -> %s|%s";
	private static final String FAIL_FORMAT = "FALHA -> %s|%s";

	@Inject
	@ProxyBuilderAplicacao
	private ProxyMetodoBuilder proxyMetodoBuilder;

	@Inject
	private Instance<Object> creator;

	@Inject
	@Any
	private Event<EventoSessaoDto> eventoSessao;

	@AroundInvoke
	public Object logMethodEntry(InvocationContext invocationContext) throws Exception {

		LogAplicacao logger = new LogAplicacao(LoggerFactory.getLogger(invocationContext.getTarget().getClass()));
		if (invocationContext.getMethod().getAnnotation(SemTrace.class) != null
				|| invocationContext.getTarget().getClass().getAnnotation(SemTrace.class) != null) {
			try {
				return invocationContext.proceed();
			} catch (Throwable t) {
				logger.error(retornarNomeMetodoComParametros(invocationContext), t);
				throw t;
			}

		}

		TransactionContext transactionContext = null;
		boolean localThread = false;

		SessionThreadContextAPI sessionThreadContextAPI = null;

		try {
			sessionThreadContextAPI = creator.select(SessionThreadContextAPI.class).get();
		} catch (Exception e) {
		}
		if (sessionThreadContextAPI != null) {
			try {
				transactionContext = sessionThreadContextAPI.getTransactionContext();
				localThread = true;
			} catch (Exception e) {
				logger.errorDebug(e.getMessage(), e);
				sessionThreadContextAPI = null;
			}
		}

		if (transactionContext == null) {
			LocalThreadContextAPI localThreadContextAPI = CDIUtil.createBeanByClasse(LocalThreadContextAPI.class);
			if (localThreadContextAPI != null) {
				transactionContext = localThreadContextAPI.getTransactionContext();
				localThread = true;
			}
		}

		if (transactionContext == null) {
			transactionContext = TransactionContext.builder();
		}

		String codigoMetodo = UUID.randomUUID().toString().toUpperCase();
		transactionContext.setServiceUUID(codigoMetodo);
		boolean isMetodoInicio = transactionContext.verificaCodigoServicoInicial();

		Throwable _t = null;
		boolean fail = false;
		AdvancedStopWatch watch = null;

		transactionContext.getStopWatch()
				.start(new StringBuilder(retornarNomeMetodoCompletoComParametros(invocationContext)).append("\t|")
						.append(transactionContext.getServiceUUID()).toString());

		watch = new AdvancedStopWatch(transactionContext.getServiceUUID());
		watch.start(methodLongName(invocationContext));
		transactionContext.getStackMethods().push(methodLongName(invocationContext));

		if (logger.isDebugEnabled()) {
			if (localThread)
				logger.trace(logarInicio(transactionContext, invocationContext));
			else
				logger.debug(logarInicio(transactionContext, invocationContext));
		}

		if (transactionContext.verificaCodigoServicoInicial()) {
			EventoSessaoDto eventoSessaoDto = criarEventoSessao(transactionContext, invocationContext);
			eventoSessaoDto.setEnumTypeEventoSessao(EnumTypeEventoSessao.INICIO_SESSAO);
			eventoSessao.fire(eventoSessaoDto);
		}

		EventoSessaoDto eventoSessaoDto = criarEventoSessao(transactionContext, invocationContext);
		eventoSessaoDto.setEnumTypeEventoSessao(EnumTypeEventoSessao.INICIO_METODO);
		eventoSessao.fire(eventoSessaoDto);

		if (sessionThreadContextAPI != null) {
			sessionThreadContextAPI.setTransactionContext(transactionContext);
		}

		try {
			return invocationContext.proceed();
		} catch (Throwable t) {
			fail = true;
			_t = t;
			if (transactionContext == null)
				logger.error(retornarNomeMetodoComParametros(invocationContext), t);

			throw t;
		} finally {

			if (transactionContext != null) {

				watch.stop();

				if (sessionThreadContextAPI != null) {
					transactionContext = sessionThreadContextAPI.getTransactionContext();
				}
				transactionContext.getStopWatch().stop(fail ? FALHA : SUCESSO);
				if (sessionThreadContextAPI != null) {
					sessionThreadContextAPI.setTransactionContext(transactionContext);
				}

				if (!fail) {
					if (localThread)
						logger.trace(logarSucesso(transactionContext, invocationContext, watch));
					else
						logger.debug(logarSucesso(transactionContext, invocationContext, watch));
				} else if (isMetodoInicio) {
					logger.error(logarFalha(transactionContext, invocationContext, watch).concat("\n"), _t);

				} else {
					logger.error(logarFalha(transactionContext, invocationContext, watch));
					logger.error(logarFalhaDebug(transactionContext, invocationContext, watch, _t));
				}

				eventoSessaoDto = criarEventoSessao(transactionContext, invocationContext);
				eventoSessaoDto.setEnumTypeEventoSessao(EnumTypeEventoSessao.FIM_METODO);
				eventoSessaoDto.setFalha(fail);
				eventoSessao.fire(eventoSessaoDto);

				if (isMetodoInicio) {
					logger.debug(transactionContext.getStopWatch().prettyPrint());
					eventoSessaoDto = criarEventoSessao(transactionContext, invocationContext);
					eventoSessaoDto.setEnumTypeEventoSessao(EnumTypeEventoSessao.FIM_SESSAO);
					eventoSessaoDto.setFalha(fail);
					eventoSessao.fire(eventoSessaoDto);
				}

			}

		}

	}

	private EventoSessaoDto criarEventoSessao(TransactionContext transactionContext,
			InvocationContext invocationContext) {
		EventoSessaoDto eventoSessao = new EventoSessaoDto();
		eventoSessao.setServiceUUID(transactionContext.getServiceUUID());
		eventoSessao.setTransactionId(transactionContext.getTransactionId());
		eventoSessao.setNomeDoMetodo(methodLongName(invocationContext));
		eventoSessao.setNomeDoMetodoComParametros(retornarNomeMetodoCompletoComParametros(invocationContext));
		eventoSessao.setCodigoServicoInicial(transactionContext.getCodigoServicoInicial());
		return eventoSessao;

	}

	private String logarInicio(TransactionContext transactionContext, InvocationContext invocationContext) {
		return String.format(CALLING, gerarCabecalhoMetodo(transactionContext, invocationContext));
	}

	private String logarSucesso(TransactionContext transactionContext, InvocationContext invocationContext,
			AdvancedStopWatch watch) {
		return String.format(SUCESS_FORMAT, gerarCabecalhoMetodo(transactionContext, invocationContext),
				watch.escreverTempoDeExecucao());
	}

	private String logarFalha(TransactionContext transactionContext, InvocationContext invocationContext,
			AdvancedStopWatch watch) {
		return new StringBuilder(String.format(FAIL_FORMAT, gerarCabecalhoMetodo(transactionContext, invocationContext),
				watch.escreverTempoDeExecucao())).toString();
	}

	private String logarFalhaDebug(TransactionContext transactionContext, InvocationContext invocationContext,
			AdvancedStopWatch watch, Throwable t) {
		Throwable rootCause = ExceptionUtils.getRootCause(t);
		return String.format(FORMAT_DEBUG_FALHA, transactionContext.getServiceUUID(), t.getMessage(),
				rootCause == null ? "" : rootCause.getMessage());
	}

	private String gerarValoresParametros(InvocationContext invocationContext) {

		if (invocationContext.getParameters().length > 0) {
			String parametros = Arrays.toString(invocationContext.getParameters());
			parametros = new StringBuilder().append("(").append(parametros.substring(1, parametros.length() - 1))
					.append(")").toString();
			return parametros;
		} else {
			return "()";
		}
	}

	private String gerarCabecalhoMetodo(TransactionContext transactionContext, InvocationContext invocationContext) {
		return String.format(FORMAT_LOG, retornarNomeMetodoComParametros(invocationContext),
				transactionContext.getTransactionId(), transactionContext.getServiceUUID());
	}

	private String methodLongName(InvocationContext invocationContext) {
		return calculateShortNameClass(invocationContext.getTarget().getClass()) + "::"
				+ invocationContext.getMethod().getName();
	}

	private String retornarNomeMetodoComParametros(InvocationContext invocationContext) {
		return String.format(FORMAT_METHOD, invocationContext.getMethod().getName(),
				gerarValoresParametros(invocationContext));
	}

	private String retornarNomeMetodoCompletoComParametros(InvocationContext invocationContext) {
		return String.format(FORMAT_METHOD, methodLongName(invocationContext),
				gerarValoresParametros(invocationContext));
	}

	private String calculateShortNameClass(Class<?> clazz) {
		String name = clazz.getName();
		int beginIndex = name.lastIndexOf('.');
		if (beginIndex > 0) {
			return name.substring(beginIndex + 1);
		} else {
			return name;
		}
	}

}
