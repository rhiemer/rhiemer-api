package br.com.rhiemer.api.jpa.criteria.create;

import javax.persistence.EntityManager;

import br.com.rhiemer.api.jpa.criteria.metodo.MetodoCriteriaJPAFilter;

public class BuilderCreateCriteriaEntityJPA {

	private Class<?> createClass;
	private Class<?> resultClass;
	private EntityManager entityManager;
	private MetodoCriteriaJPAFilter operacoes;

	public BuilderCreateCriteriaEntityJPA setCreateClass(Class<?> createClass) {
		this.createClass = createClass;
		return this;
	}

	protected Class<?> getCreateClass() {
		return createClass;
	}

	protected BuilderCreateCriteriaEntityJPA setResultClass(Class<?> resultClass) {
		this.resultClass = resultClass;
		return this;
	}

	protected Class<?> getResultClass() {
		return resultClass;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	protected BuilderCreateCriteriaEntityJPA setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		return this;
	}

	protected MetodoCriteriaJPAFilter getOperacoes() {
		return operacoes;
	}

	protected BuilderCreateCriteriaEntityJPA setOperacoes(MetodoCriteriaJPAFilter operacoes) {
		this.operacoes = operacoes;
		return this;
	}

	public static BuilderCreateCriteriaEntityJPA builder() {
		return new BuilderCreateCriteriaEntityJPA();
	}

	public void build() {
		RetornoCreateCriteriaJPAEntity retornoCreateCriteriaJPA = new RetornoCreateCriteriaJPAEntity(getEntityManager(),
				getCreateClass(), getResultClass());
		CreateCriteriaJPA createCriteriaJPA = new CreateCriteriaJPA(retornoCreateCriteriaJPA);
		operacoes.builder(createCriteriaJPA);
	}

}
