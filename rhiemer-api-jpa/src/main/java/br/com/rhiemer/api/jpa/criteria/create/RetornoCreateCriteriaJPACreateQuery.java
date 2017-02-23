package br.com.rhiemer.api.jpa.criteria.create;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class RetornoCreateCriteriaJPACreateQuery extends RetornoCreateCriteriaJPA {

	private EntityManager entityManager;

	protected abstract CriteriaQuery createQuery();

	protected abstract Root createRoot();

	public RetornoCreateCriteriaJPACreateQuery(EntityManager entityManager) {
		super();
		setEntityManager(entityManager);
	}
	
	@Override
	protected void createAtributos()
	{
		setBuilder(createCriteriaBuilder());
		setCriteriaQuery(createQuery());
		setRoot(createRoot());
	}

	protected CriteriaBuilder createCriteriaBuilder() {
		return getEntityManager().getCriteriaBuilder();
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
