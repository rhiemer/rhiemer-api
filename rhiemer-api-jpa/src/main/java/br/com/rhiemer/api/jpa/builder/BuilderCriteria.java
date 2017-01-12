package br.com.rhiemer.api.jpa.builder;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.util.dto.Pager;

public class BuilderCriteria implements BuildJPA {

	private Class<?> createClass;
	private Class<?> resultClass;
	private Pager pager;
	private ParametrizarCriteria parametrizarCriteria;
	private String resultMaping;
	private Boolean transformMap = false;

	public BuilderCriteria(Builder builder) {
		super();
		this.createClass = builder.createClass;
		this.resultClass = builder.resultClass;
		this.pager = builder.pager;
		this.parametrizarCriteria = builder.parametrizarCriteria;
		this.resultMaping = builder.resultMaping;
		this.transformMap = builder.transformMap;
	}

	protected void paginar(Query query) {
		if (pager != null) {
			pager.paginarQuery(query);
		}
	}

	public Query buildQuery(EntityManager em) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		Class<?> _createClass = null;
		if (this.createClass == null)
			_createClass = this.resultClass;
		else
			_createClass = this.createClass;
		CriteriaQuery criteriaQuery = builder.createQuery(_createClass);
		Root from = criteriaQuery.from(this.resultClass);
		final List<Predicate> predicates = new ArrayList<Predicate>();
		if (this.parametrizarCriteria != null) {
			criteriaQuery = this.parametrizarCriteria.parametrizar(builder, criteriaQuery, from, predicates);
			criteriaQuery = criteriaQuery.select(from).where(predicates.toArray(new Predicate[] {}));
		}
		Query query = em.createQuery(criteriaQuery);
		paginar(query);
		return query;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private Class<?> createClass;
		private boolean transformMap;
		private Class<?> resultClass;
		private Pager pager;
		private ParametrizarCriteria parametrizarCriteria;
		private String resultMaping;

		public Builder createClass(Class<?> createClass) {
			this.createClass = createClass;
			return this;
		}

		public Builder resultClass(Class<?> resultClass) {
			this.resultClass = resultClass;
			return this;
		}

		public Builder pager(Pager pager) {
			this.pager = pager;
			return this;
		}

		public Builder parametrizarCriteria(ParametrizarCriteria parametrizarCriteria) {
			this.parametrizarCriteria = parametrizarCriteria;
			return this;
		}

		public Builder resultMaping(String resultMaping) {
			this.resultMaping = resultMaping;
			return this;
		}

		public Builder transformMap(boolean transformMap) {
			this.transformMap = transformMap;
			return this;
		}

		public BuilderCriteria build() {
			return new BuilderCriteria(this);
		}

	}

	public Class<?> getCreateClass() {
		return createClass;
	}

	public void setCreateClass(Class<?> createClass) {
		this.createClass = createClass;
	}

	public Class<?> getResultClass() {
		return resultClass;
	}

	public void setResultClass(Class<?> resultClass) {
		this.resultClass = resultClass;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public ParametrizarCriteria getParametrizarCriteria() {
		return parametrizarCriteria;
	}

	public void setParametrizarCriteria(ParametrizarCriteria parametrizarCriteria) {
		this.parametrizarCriteria = parametrizarCriteria;
	}

	public String getResultMaping() {
		return resultMaping;
	}

	public void setResultMaping(String resultMaping) {
		this.resultMaping = resultMaping;
	}

	public Boolean getTransformMap() {
		return transformMap;
	}

	public void setTransformMap(Boolean transformMap) {
		this.transformMap = transformMap;
	}

	

}
