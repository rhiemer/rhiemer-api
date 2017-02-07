package br.com.rhiemer.api.jpa.service.factory;

import static br.com.rhiemer.api.jpa.constantes.ConstantesAPIJPA.SERVICE_APLICACAO;
import static br.com.rhiemer.api.util.helper.ConstantesAPI.BASE_PACKAGE_API;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.reflections.Reflections;

import br.com.rhiemer.api.jpa.annotations.InjetarDaoEntidade;
import br.com.rhiemer.api.jpa.dao.factory.DaoFactory;
import br.com.rhiemer.api.jpa.service.PersistenceServiceCrud;
import br.com.rhiemer.api.util.annotations.app.ProxyBuilderAplicacao;
import br.com.rhiemer.api.util.annotations.app.ReflectionBasePackage;
import br.com.rhiemer.api.util.cdi.CDIUtil;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.proxy.ProxyMetodoBuilder;
import br.com.rhiemer.api.util.service.PersistenceServiceBean;

@Dependent
public class PersistenceServiceBeanFacroty {

	@Inject
	private Instance<Object> creator;

	@Inject
	private DaoFactory daoFactory;

	@Inject
	@ProxyBuilderAplicacao
	private ProxyMetodoBuilder proxyBuilder;

	@Inject
	@ReflectionBasePackage
	private Reflections reflections;
	
	@Inject
	private BeanManager bm;

	private Map<Class<?>, Class<?>> mapPersistenceServiceBeanEntity = new HashMap<>();

	private Map<Class<?>, Object> mapPersistenceServiceBean = new HashMap<>();

	@PostConstruct
	public void init() {

		Set<Class<? extends PersistenceServiceBean>> clazzs = reflections.getSubTypesOf(PersistenceServiceBean.class);

		for (Class<?> clazz : clazzs) {
			adicionarPersistenceServiceBean((Class<? extends PersistenceServiceBean>) clazz);
		}

	}

	private <T> void adicionarPersistenceServiceBean(Class<T> clazz) {

		if (clazz.getPackage().getName().startsWith(BASE_PACKAGE_API))
			return;
		
		if (!Helper.isClasseParametrizada(clazz))
			return;

		Class<?> clazzParameter = Helper.getClassPrincipal(clazz);
		mapPersistenceServiceBeanEntity.put(clazzParameter, clazz);

	}

	public <T, K> K buscarPersistenceServiceBean(Class<T> clazz, Class<K> classeProxyService) {

		Object bean = null;

		Class<?> classeBean = mapPersistenceServiceBeanEntity.get(clazz);
		if (classeBean != null) {
			// recupera o bean do serviço
			try {
				bean = (T) creator.select(classeBean).get();
			} catch (Exception e) {
			}
		}

		if (bean == null) {
			bean = mapPersistenceServiceBean.get(clazz);
			if (bean == null) {

				bean = CDIUtil.createBeanByName(SERVICE_APLICACAO);
				if (bean == null) {
					bean = CDIUtil.createBeanByClasse(PersistenceServiceAPI.class);

				}
				
			}
			Helper.setValueMethodOrField(bean, "classeObjeto", clazz);
			mapPersistenceServiceBean.put(clazz, bean);
			if (bean.getClass().getAnnotation(InjetarDaoEntidade.class) != null) {

				Object dao = daoFactory.buscarDao(clazz);
				if (dao != null)
					Helper.setValueMethodOrField(bean, "dao", dao);
			}
		}
		Object proxyService = (K) proxyBuilder.builderBean(bean, classeProxyService);

		return (K) proxyService;
	}

	public <T, K> K buscarPersistenceServiceBean(Class<T> clazz) {

		return (K) buscarPersistenceServiceBean(clazz, PersistenceServiceCrud.class);
	}

}
