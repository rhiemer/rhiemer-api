package br.com.rhiemer.api.jpa.criteria.query.distinct;

import javax.persistence.criteria.CriteriaQuery;

import br.com.rhiemer.api.jpa.criteria.query.parametros.IQueryParametros;

public class QueryDistinct implements IQueryParametros {

	private Boolean distinct = true;
	private CriteriaQuery query;

	public QueryDistinct setQuery(CriteriaQuery query) {
		this.query = query;
		return this;
	}

	@Override
	public CriteriaQuery getQuery() {
		return this.query;
	}

	@Override
	public void build() {
		query.distinct(getDistinct());

	}

	public Boolean getDistinct() {
		return distinct;
	}

	public QueryDistinct setDistinct(Boolean distinct) {
		this.distinct = distinct;
		return this;
	}

	@Override
	public Boolean getAtributo() {
		// TODO Auto-generated method stub
		return null;
	}

}
