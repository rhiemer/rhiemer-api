package br.com.rhiemer.api.util.interceptor;

import static br.com.rhiemer.api.util.constantes.SequencialInterceptor.DESLIGAR_EVENTO_DESTINO;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Priority;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.util.annotations.app.LogApp;
import br.com.rhiemer.api.util.annotations.evento.DesligaEventoDestino;
import br.com.rhiemer.api.util.annotations.evento.DesligaEventoInterceptorDestinoDiscovery;
import br.com.rhiemer.api.util.cdi.evento.desligar.DesligamentoEventoContext;
import br.com.rhiemer.api.util.cdi.evento.desligar.ParametrosBuscaDesligamentoEventoDto;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.PojoHelper;
import br.com.rhiemer.api.util.log.LogAplicacao;

@Interceptor
@DesligaEventoInterceptorDestinoDiscovery
@Priority(DESLIGAR_EVENTO_DESTINO)
public class DesligarEventoDestinoInterceptor {

	@Inject
	@LogApp
	private LogAplicacao logger;

	@Inject
	private Instance<Object> creator;

	private List<ParametrosBuscaDesligamentoEventoDto> relacionarDesligamentoEventoDestino(
			InvocationContext invocationContext) {

		List<ParametrosBuscaDesligamentoEventoDto> lista = new ArrayList<>();
		DesligaEventoDestino[] desligaEventoDestinoList = invocationContext.getMethod()
				.getAnnotationsByType(DesligaEventoDestino.class);
		for (DesligaEventoDestino desligaEventoDestino : desligaEventoDestinoList) {
			ParametrosBuscaDesligamentoEventoDto dto = new ParametrosBuscaDesligamentoEventoDto();
			dto.setChaveEvento(desligaEventoDestino.chaveEvento());
			Helper.addListByKey(lista, dto);
		}

		for (int i = 0; i < invocationContext.getMethod().getParameterAnnotations().length; i++) {

			Object parameter = invocationContext.getParameters()[i];
			invocationContext.getMethod().getAnnotatedParameterTypes();

			DesligaEventoDestino[] listaParam = invocationContext.getMethod().getAnnotatedParameterTypes()[i]
					.getAnnotationsByType(DesligaEventoDestino.class);
			for (DesligaEventoDestino desligaEventoDestino : listaParam) {
				ParametrosBuscaDesligamentoEventoDto dto = PojoHelper.addCollectionPojoKey(
						ParametrosBuscaDesligamentoEventoDto.class, lista, desligaEventoDestino.chaveEvento());
				dto.getValores().put(
						StringUtils.isBlank(desligaEventoDestino.nomeChaveParametro()) ? "parametro"
								: desligaEventoDestino.nomeChaveParametro(),
						StringUtils.isBlank(desligaEventoDestino.atributoChaveParametro()) ? parameter
								: Helper.getValueProperty(parameter, desligaEventoDestino.atributoChaveParametro()));
			}

		}

		return lista;

	}

	@AroundInvoke
	public Object buscaDesligamentoEventoEntry(InvocationContext invocationContext) throws Exception {
		DesligamentoEventoContext desligamentoEventoContext = null;
		try {
			desligamentoEventoContext = creator.select(DesligamentoEventoContext.class).get();
		} catch (Exception e) {
			logger.errorDebug(e.getMessage(), e);
		}
		List<ParametrosBuscaDesligamentoEventoDto> lista = relacionarDesligamentoEventoDestino(invocationContext);
		try {
			final DesligamentoEventoContext desligamentoEventoContext2 = desligamentoEventoContext;
			for (ParametrosBuscaDesligamentoEventoDto dtoLista : lista) {
				boolean temDesligamentoEvento = desligamentoEventoContext2.temDesligamentoEvento(dtoLista);
				if (temDesligamentoEvento)
					return null;

			}
		} catch (Exception e) {
			logger.errorDebug(e.getMessage(), e);
		}
		return invocationContext.proceed();

	}

}
