package br.com.rhiemer.api.jpa.criteria.orderby;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

import br.com.rhiemer.api.jpa.criteria.atributos.AbstractAtributoCriteriaJPA;

public abstract class OrderByCriteriaJPA extends AbstractAtributoCriteriaJPA {

	private CriteriaBuilder builder;

	public CriteriaBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(CriteriaBuilder builder) {
		this.builder = builder;
	}

	public Order build() {
		Path path = builderAtributoCriteria();		
		return buildOrderBy(path);
	}

	public abstract Order buildOrderBy(Expression path);

}
