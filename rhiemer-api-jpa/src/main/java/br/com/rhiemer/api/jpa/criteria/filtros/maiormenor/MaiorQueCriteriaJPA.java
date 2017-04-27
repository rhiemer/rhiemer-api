package br.com.rhiemer.api.jpa.criteria.filtros.maiormenor;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;

public class MaiorQueCriteriaJPA extends FiltroCriteriaJPA {

	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		
		if (filtro instanceof Expression)
			return getBuilder().greaterThan(path, (Expression) filtro);
		else
			return getBuilder().greaterThan(path, (Comparable) filtro);
	}

}
