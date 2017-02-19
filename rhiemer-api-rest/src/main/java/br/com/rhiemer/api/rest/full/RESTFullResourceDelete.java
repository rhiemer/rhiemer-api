package br.com.rhiemer.api.rest.full;

import br.com.rhiemer.api.util.annotations.interceptor.Trace;

/**
 * Endpoint REST para buscar entidades
 * 
 * @author rodrigo.hiemer
 *
 */
@Trace
public class RESTFullResourceDelete extends RESTFullPerisitenceService implements RestFullDelete {

	@Override
	public void delete(String entity, String id) {

		buscarPersitenceService(restfullEntities.getNotFound(entity))
				.deletarPeloId(restfullEntities.converterId(entity, id));

	}

}
