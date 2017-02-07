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
public class RESTFullResource implements RestFull {

	@Inject
	protected RestFullEntities restfullEntities;

	@Inject
	protected PersistenceServiceBeanFacroty persistenceServiceBeanFacroty;

	protected PersistenceServiceBean persistenceService;

	@SemTrace
	protected Class<? extends PersistenceServiceBean> getClassePersistenceService() {
		return PersistenceServiceBean.class;
	}

	@SemTrace
	protected <K extends PersistenceServiceBean> K buscarPersitenceService(Class<?> classe, Class<K> classeProxy) {

		PersistenceServiceBean beanClass = getPersitenceService();
		if (beanClass == null) {
			Object o = persistenceServiceBeanFacroty.buscarPersistenceServiceBean(classe, classeProxy);
			return (K) o;
		} else
			return (K) beanClass;

	}

	@SemTrace
	protected PersistenceServiceBean buscarPersitenceService(Class<?> classe) {
		return buscarPersitenceService(classe, getClassePersistenceService());

	}

	@SemTrace
	protected PersistenceServiceBean getPersitenceService() {
		return this.persistenceService;
	}

	@SemTrace
	protected void setPersistenceService(PersistenceServiceBean persistenceService) {
		this.persistenceService = persistenceService;
	}

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
		Class<?> clazz = restfullEntities.getNotFound(entity);
		Object entityObject = JsonHelper.toObject(json, clazz);
		Object result = buscarPersitenceService(clazz).adicionar(entityObject);
		return result;
	}

	@Override
	public Object update(String entity, Object json) {
		Class<?> clazz = restfullEntities.getNotFound(entity);
		Object entityObject = JsonHelper.toObject(json, clazz);
		Object result = buscarPersitenceService(clazz).atualizar(entityObject);
		return result;
	}

	@Override
	public void delete(String entity, String id) {

		buscarPersitenceService(restfullEntities.getNotFound(entity))
				.removerPeloId(restfullEntities.converterId(entity, id));

	}

}
