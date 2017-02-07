package br.com.rhiemer.api.jpa.dao.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.reflections.Reflections;

import br.com.rhiemer.api.jpa.annotations.InjetarDaoEntidade;
import br.com.rhiemer.api.jpa.dao.DaoJPAImpl;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.mapper.EntityManagerMapClass;
import br.com.rhiemer.api.util.annotations.app.ProxyBuilderAplicacao;
import br.com.rhiemer.api.util.annotations.app.ReflectionBasePackage;
import br.com.rhiemer.api.util.cdi.CDIUtil;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.proxy.ProxyMetodoBuilder;

@Dependent
public class DaoFactory {

	@Inject
	private EntityManagerMapClass entityManagerMapClass;

	@Inject
	@Any
	private Instance<Object> creator;

	@Inject
	@ProxyBuilderAplicacao
	private ProxyMetodoBuilder proxyBuilder;

	@Inject
	@ReflectionBasePackage
	private Reflections reflections;

	protected Map<Class<?>, Object> mapClasseDao = new HashMap<>();

	private Class<?> clazzDaoApiBean;

	@PostConstruct
	public void init() {

		Set<Class<? extends DaoAPP>> clazzsDaoApiBean = reflections.getSubTypesOf(DaoAPP.class);
		if (clazzsDaoApiBean != null && clazzsDaoApiBean.size() > 0)
			clazzDaoApiBean = clazzsDaoApiBean.iterator().next();

	}

	public <T, K> K buscarDao(Class<T> clazz, Class<K> classeProxyDao) {

		Object dao = mapClasseDao.get(clazz);
		EntityManager em = null;
		if (dao == null) {

			em = entityManagerMapClass.getEntityManagerByEntityPadrao((Class<? extends Entity>) clazz);

			if (clazzDaoApiBean != null)
				try {
					dao = (T) creator.select(clazzDaoApiBean).get();
				} catch (Exception e) {
				}

			if (dao == null) {
				dao = CDIUtil.createBeanByClasse(DaoAPI.class);
			}

			if (dao == null)
				return null;

			if (em != null && dao.getClass().getAnnotation(InjetarDaoEntidade.class) == null) {
				Helper.setValueMethodOrField(dao, "entityManager", em);
			}

			mapClasseDao.put(clazz, dao.getClass());
		}

		Object proxyDao = proxyBuilder.builderBean(dao, classeProxyDao);
		return (K) proxyDao;

	}

	public <K> K buscarDao(Class<?> clazz) {

		return (K) buscarDao(clazz, DaoJPAImpl.class);

	}

}
