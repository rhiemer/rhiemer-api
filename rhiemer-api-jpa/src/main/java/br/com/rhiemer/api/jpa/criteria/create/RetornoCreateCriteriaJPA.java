package br.com.rhiemer.api.jpa.criteria.create;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class RetornoCreateCriteriaJPA {

	private CriteriaBuilder builder;
	private CriteriaQuery criteriaQuery;
	private Root root;
	
	protected RetornoCreateCriteriaJPA()
	{
		super();
	}

	public CriteriaBuilder getBuilder() {
		return builder;
	}

	protected void setBuilder(CriteriaBuilder builder) {
		this.builder = builder;
	}

	public CriteriaQuery getCriteriaQuery() {
		return criteriaQuery;
	}

	protected void setCriteriaQuery(CriteriaQuery criteriaQuery) {
		this.criteriaQuery = criteriaQuery;
	}

	public Root getRoot() {
		return root;
	}

	protected void setRoot(Root root) {
		this.root = root;
	}
	
	protected void createAtributos()
	{
	}

	public static Builder createBuilder() {
		return new Builder();
	}

	public static class Builder {

		private CriteriaBuilder builder;
		private CriteriaQuery criteriaQuery;
		private Root root;

		public Builder setBuilder(CriteriaBuilder builder) {
			this.builder = builder;
			return this;
		}

		public Builder setCriteriaQuery(CriteriaQuery criteriaQuery) {
			this.criteriaQuery = criteriaQuery;
			return this;
		}

		public Builder setRoot() {
			this.root = root;
			return this;
		}

		public RetornoCreateCriteriaJPA builder() {
			RetornoCreateCriteriaJPA result = new RetornoCreateCriteriaJPA();
			result.setBuilder(this.builder);
			result.setRoot(this.root);
			result.setCriteriaQuery(this.criteriaQuery);
			return result;
		}

	}

}
