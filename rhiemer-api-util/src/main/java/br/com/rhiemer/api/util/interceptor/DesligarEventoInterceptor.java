package br.com.rhiemer.api.util.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Priority;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.util.annotations.evento.DesligaEvento;
import br.com.rhiemer.api.util.annotations.evento.DesligaEventoInterceptorDiscovery;
import br.com.rhiemer.api.util.cdi.evento.desligar.DesligamentoEventoContext;
import br.com.rhiemer.api.util.cdi.evento.desligar.DesligamentoEventoDto;
import br.com.rhiemer.api.util.helper.Helper;

@Interceptor
@DesligaEventoInterceptorDiscovery
@Priority(Interceptor.Priority.LIBRARY_BEFORE + 11)
public class DesligarEventoInterceptor {

	@Inject
	private Instance<Object> creator;

	private List<DesligamentoEventoDto> relacionarDesligamentoEvento(String chaveMetodo,
			InvocationContext invocationContext) {

		List<DesligamentoEventoDto> lista = new ArrayList<>();
		DesligaEvento[] desligaEventoList = invocationContext.getMethod().getAnnotationsByType(DesligaEvento.class);
		for (DesligaEvento desligaEvento : desligaEventoList) {
			DesligamentoEventoDto desligamentoEventoDto = new DesligamentoEventoDto();
			desligamentoEventoDto.setChaveMetodo(chaveMetodo);
			desligamentoEventoDto.setChaveEvento(desligaEvento.chaveEvento());
			Helper.addListByKey(lista, desligamentoEventoDto);
		}

		for (int i = 0; i < invocationContext.getMethod().getParameterAnnotations().length; i++) {

			Object parameter = invocationContext.getParameters()[i];
			invocationContext.getMethod().getAnnotatedParameterTypes();

			DesligaEvento[] desligaEventoListParametro = invocationContext.getMethod().getAnnotatedParameterTypes()[i]
					.getAnnotationsByType(DesligaEvento.class);
			for (DesligaEvento desligaEvento : desligaEventoListParametro) {
				DesligamentoEventoDto desligamentoEventoDto = new DesligamentoEventoDto();
				desligamentoEventoDto.setChaveMetodo(chaveMetodo);
				desligamentoEventoDto.setChaveEvento(desligaEvento.chaveEvento());
				if (lista.indexOf(desligamentoEventoDto) >= 0)
					continue;
				desligamentoEventoDto.setNomeChaveParametro(desligaEvento.nomeChaveParametro());
				if (!StringUtils.isBlank(desligaEvento.atributoChaveParametro()))
					desligamentoEventoDto.setValorChaveParametro(parameter);
				else
					desligamentoEventoDto.setValorChaveParametro(
							Helper.getValueProperty(parameter, desligaEvento.atributoChaveParametro()));
				lista.add(desligamentoEventoDto);
			}

		}

		return lista;

	}

	@AroundInvoke
	public Object desligarEventoEntry(InvocationContext invocationContext) throws Exception {

		DesligamentoEventoContext desligamentoEventoContext = null;
		try {
			desligamentoEventoContext = creator.select(DesligamentoEventoContext.class).get();
		} catch (Exception e) {
		}

		String chaveMetodo = UUID.randomUUID().toString();
		List<DesligamentoEventoDto> lista = null;
		if (desligamentoEventoContext != null) {
			lista = relacionarDesligamentoEvento(chaveMetodo, invocationContext);
			if (lista != null && lista.size() > 0) {
				try {
					final DesligamentoEventoContext desligamentoEventoContext2 = desligamentoEventoContext;
					lista.forEach(t -> desligamentoEventoContext2.addDesligamentoEventoDto(t));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		try {
			return invocationContext.proceed();
		} finally {
			if (desligamentoEventoContext != null) {
				try {
					desligamentoEventoContext.removeDesligamentoEventoDto(chaveMetodo);
				} catch (Exception e) {
				}
			}
		}

	}

}
