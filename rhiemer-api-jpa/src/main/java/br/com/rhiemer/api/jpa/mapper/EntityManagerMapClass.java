package br.com.rhiemer.api.jpa.mapper;

import static br.com.rhiemer.api.jpa.constantes.ConstantesAPIJPA.PERSITENCE_UNIT_NAME_APLICACAO;
import static br.com.rhiemer.api.jpa.constantes.ConstantesAPIJPA.PERSITENCE_UNIT_NAME_APLICACAO_DEFAULT;

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

import br.com.rhiemer.api.jpa.annotations.PuName;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.helper.JPAUtils;
import br.com.rhiemer.api.util.annotations.app.ReflectionBasePackage;
import br.com.rhiemer.api.util.cdi.ConfiguracoesAplicacao;

@Dependent
public class EntityManagerMapClass {

	private static final String PROPERTY_PERSISTENCE_UNIT_NAME = "hibernate.ejb.persistenceUnitName";
	private String puNameAplicacao;
	private EntityManager entityManagerAplicacao;

	@Inject
	@Any
	private Instance<Object> instance;

	private ConfiguracoesAplicacao configuracoesAplicacao;

	protected Map<Class<? extends Entity>, EntityManager> mapEntityManager = new HashMap<>();

	@Inject
	@ReflectionBasePackage
	private Reflections reflections;

	@PostConstruct
	public void init() {
		configuracoesAplicacao =  instance.select(ConfiguracoesAplicacao.class).get();
		puNameAplicacao = configuracoesAplicacao.getConfiguracao(PERSITENCE_UNIT_NAME_APLICACAO);
		entityManagerAplicacao = getEntityManagerByName(puNameAplicacao());

		Set<Class<? extends Entity>> classesEntitys = reflections.getSubTypesOf(Entity.class);
		for (Class<? extends Entity> classeEntity : classesEntitys) {
			PuName puName = classeEntity.getAnnotation(PuName.class);
			if (puName == null)
				continue;
			EntityManager em = getEntityManagerByName(puName.value());
			if (em != null)
				mapEntityManager.put(classeEntity, em);

		}

	}

	protected String puNameAplicacao() {
		return this.puNameAplicacao;
	}

	public EntityManager getEntityManagerByName(String puName) {

		if (puName == null)
			return null;

		EntityManager em = null;
		if (puName.equalsIgnoreCase(PERSITENCE_UNIT_NAME_APLICACAO_DEFAULT)) {
			em = instance.select(EntityManager.class).get();

		} else {
			em = JPAUtils.entityManagerByName(puName);
		}
		return em;

		/*
		 * while (instance.iterator().hasNext()) { EntityManager em =
		 * instance.iterator().next(); String puNameEm =
		 * em.getEntityManagerFactory().getProperties().get(
		 * PROPERTY_PERSISTENCE_UNIT_NAME) .toString(); if
		 * (puNameEm.equalsIgnoreCase(puName)) return em;
		 * 
		 * }
		 */

	}

	public EntityManager getEntityManagerAplicacao() {
		return entityManagerAplicacao;

	}

	public EntityManager getEntityManagerByEntityPadrao(Class<? extends Entity> classe) {

		EntityManager em = getEntityManagerByEntity(classe);
		if (em == null)
			return getEntityManagerAplicacao();
		else
			return em;

	}

	public EntityManager getEntityManagerByEntity(Class<? extends Entity> classe) {

		EntityManager em = mapEntityManager.get(classe);
		return em;

	}

}
