package br.com.rhiemer.api.rest.full;

import javax.enterprise.context.ApplicationScoped;

import org.apache.commons.lang3.exception.ExceptionUtils;

import br.com.rhiemer.api.util.annotations.rest.RESTful;
import br.com.rhiemer.api.util.cdi.PackageEntitiesCdi;
import br.com.rhiemer.api.util.exception.ConversaoTipoIdException;
import br.com.rhiemer.api.util.helper.Helper;

@ApplicationScoped
public class RestFullEntities extends PackageEntitiesCdi<RESTful> {

	@Override
	protected String notFoundMessage() {
		return "Entidade %s não existe.";
	}

	protected Object converterId(String classe, String id) {
		Class<?> clazz = get(classe);
		try {
			return Helper.validaPrimaryKeyValor(clazz, id);
		} catch (Exception e) {

			Class<?> classeRoot = ExceptionUtils.getRootCause(e).getClass();
			if (classeRoot.isAssignableFrom(NumberFormatException.class))
				throw new ConversaoTipoIdException(
						String.format("Problema na conversão do valor %s para numerico na chave %s", id, classe));
			else if (classeRoot.isAssignableFrom(IllegalArgumentException.class))
				throw new ConversaoTipoIdException(String.format("Problema na conversão do valor %s para chave:%s .%s",
						id, classe, e.getMessage()));
			else
				throw e;
		}

	}

}
