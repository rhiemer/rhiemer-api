package br.com.rhiemer.api.rest.full;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import br.com.rhiemer.api.jpa.service.factory.PersistenceServiceBeanFacroty;
import br.com.rhiemer.api.rest.helper.JsonResponse;
import br.com.rhiemer.api.util.annotations.interceptor.SemTrace;
import br.com.rhiemer.api.util.annotations.interceptor.Trace;
import br.com.rhiemer.api.util.exception.NotFoundException;
import br.com.rhiemer.api.util.helper.JsonHelper;
import br.com.rhiemer.api.util.service.PersistenceServiceBean;

/**
 * Endpoint REST para buscar entidades
 * 
 * @author rodrigo.hiemer
 *
 */
@Trace
public class RESTFullResource extends RESTFullPerisitenceService implements RestFull {

	
	@Override
	public Object find(String entity) {

		List<?> result = buscarPersitenceService(restfullEntities.getNotFound(entity)).listarTodos();

		return result;

	}

	@Override
	public Response find(String entity, String id) {

		Object result = buscarPersitenceService(restfullEntities.getNotFound(entity))
				.procurarPeloIdLazy(restfullEntities.converterId(entity, id));

		if (result == null)
			throw new NotFoundException("Entidade " + entity + " com o id " + id + " não existe.");

		return JsonResponse.respondeOK(result);
	}

	@Override
	public Object add(String entity, Object json) {
		Object entityObject = toEntidade(entity,json);
		Object result = buscarPersitenceService(entity.getClass()).adicionar(entityObject);
		return result;
	}

	@Override
	public Object update(String entity, Object json) {
		Object entityObject = toEntidade(entity,json);
		Object result = buscarPersitenceService(entity.getClass()).atualizar(entityObject);
		return result;
	}

	@Override
	public void delete(String entity, String id) {

		buscarPersitenceService(restfullEntities.getNotFound(entity))
				.removerPeloId(restfullEntities.converterId(entity, id));

	}

}
