package br.com.rhiemer.api.rest.full;

import javax.inject.Inject;

import br.com.rhiemer.api.jpa.service.factory.PersistenceServiceBeanFacroty;
import br.com.rhiemer.api.util.annotations.interceptor.SemTrace;
import br.com.rhiemer.api.util.helper.JsonHelper;
import br.com.rhiemer.api.util.service.PersistenceServiceBean;

public class RESTFullPerisitenceService {
	
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
	
	@SemTrace
	protected Object toEntidade(String entity, Object json)
	{
		Class<?> clazz = restfullEntities.getNotFound(entity);
		Object entityObject = JsonHelper.toObject(json, clazz);
		return entityObject;
	}

}
