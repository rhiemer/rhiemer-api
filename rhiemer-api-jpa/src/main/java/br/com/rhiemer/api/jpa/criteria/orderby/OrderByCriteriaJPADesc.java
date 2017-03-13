package br.com.rhiemer.api.jpa.criteria.orderby;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

public class OrderByCriteriaJPADesc extends OrderByCriteriaJPA {

	@Override
	public Order buildOrderBy(Expression path) {
		return getBuilder().desc(path);
	}

}
