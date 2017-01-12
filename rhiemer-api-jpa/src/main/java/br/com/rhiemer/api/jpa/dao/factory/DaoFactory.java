package br.com.rhiemer.api.jpa.dao.factory;

import static br.com.rhiemer.api.jpa.constantes.ConstantesAPIJPA.DAO_APLICACAO;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.rhiemer.api.jpa.annotations.InjetarDaoEntidade;
import br.com.rhiemer.api.jpa.dao.DaoJPAImpl;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.mapper.EntityManagerMapClass;
import br.com.rhiemer.api.util.annotations.ProxyBuilderAplicacao;
import br.com.rhiemer.api.util.cdi.CDIUtil;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.proxy.ProxyMetodoBuilder;

@Dependent
public class DaoFactory {

	@Inject
	private EntityManagerMapClass entityManagerMapClass;

	@Inject
	@ProxyBuilderAplicacao
	private ProxyMetodoBuilder proxyBuilder;

	protected Map<Class<?>, Object> mapClasseDao = new HashMap<>();

	public <T, K> K buscarDao(Class<T> clazz, Class<K> classeProxyDao) {

		Object dao = mapClasseDao.get(clazz);
		EntityManager em = null;
		if (dao == null) {

			em = entityManagerMapClass.getEntityManagerByEntityPadrao((Class<? extends Entity>) clazz);
			dao = CDIUtil.createBeanByName(DAO_APLICACAO);
			if (dao == null) {
				dao = CDI.current().select(DaoAPI.class).get();
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
