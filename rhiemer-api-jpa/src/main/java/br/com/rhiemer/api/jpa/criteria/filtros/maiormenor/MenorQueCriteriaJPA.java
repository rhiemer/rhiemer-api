package br.com.rhiemer.api.jpa.criteria.filtros.maiormenor;

import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;

public class MenorQueCriteriaJPA extends FiltroCriteriaJPA {

	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		if (filtro instanceof Expression)
			return getBuilder().lessThan(path, (Expression) filtro);
		else
			return getBuilder().lessThan(path, (Comparable) filtro);
	}

}
