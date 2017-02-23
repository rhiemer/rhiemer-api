package br.com.rhiemer.api.jpa.criteria.create;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class RetornoCreateCriteriaJPAEntity extends RetornoCreateCriteriaJPACreateQuery {

	private Class<?> createClass;
	private Class<?> resultClass;
	

	public RetornoCreateCriteriaJPAEntity(EntityManager entityManager,Class<?> createClass)
	{
		super(entityManager);
		setCreateClass(createClass);
	}
	
	public RetornoCreateCriteriaJPAEntity(EntityManager entityManager,Class<?> createClass,Class<?> resultClass)
	{
		super(entityManager);
		setCreateClass(createClass);
		setResultClass(createClass);
	}
	
	
	protected void setCreateClass(Class<?> createClass) {
		this.createClass = createClass;
	}

	protected void setResultClass(Class<?> resultClass) {
		this.resultClass = resultClass;
	}

	
	@Override
	protected CriteriaQuery createQuery() {
		
		return this.getBuilder().createQuery(getCreateClass());
	}

	@Override
	protected Root createRoot() {
		// TODO Auto-generated method stub
		return getCriteriaQuery().from(getResultClass());
	}

	public Class<?> getCreateClass() {
		return createClass;
	}

	public Class<?> getResultClass() {
		return resultClass == null?getCreateClass():resultClass;
	}

	
	

}
