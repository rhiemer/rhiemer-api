package br.com.rhiemer.api.jpa.criteria.filtros.equals;

import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;

public class NotEqualsCriteriaJPA extends FiltroCriteriaJPA {

	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		if (filtro instanceof Expression)
			return getBuilder().notEqual(path, (Expression) filtro);
		else
			return getBuilder().notEqual(path, filtro);
	}

}
