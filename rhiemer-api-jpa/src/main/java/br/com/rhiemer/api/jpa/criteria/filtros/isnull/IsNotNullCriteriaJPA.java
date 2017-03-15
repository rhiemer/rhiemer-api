package br.com.rhiemer.api.jpa.criteria.filtros.isnull;

import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;

public class IsNotNullCriteriaJPA extends FiltroCriteriaJPA {

	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		return getBuilder().isNotNull(path);
	}
	

}