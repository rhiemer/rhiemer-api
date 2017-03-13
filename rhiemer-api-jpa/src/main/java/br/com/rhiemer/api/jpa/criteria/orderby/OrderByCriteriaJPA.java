package br.com.rhiemer.api.jpa.criteria.orderby;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

import br.com.rhiemer.api.jpa.criteria.join.AbstractJoinCriteriaJPA;

public abstract class OrderByCriteriaJPA extends AbstractJoinCriteriaJPA  {

	private CriteriaBuilder builder;

	public CriteriaBuilder getBuilder() {
		return builder;
	}

	protected void setBuilder(CriteriaBuilder builder) {
		this.builder = builder;
	}

	public Order build() {
		Path path = builderJoin();
		return buildOrderBy(path);
	}

	public abstract Order buildOrderBy(Expression path);
	


}
