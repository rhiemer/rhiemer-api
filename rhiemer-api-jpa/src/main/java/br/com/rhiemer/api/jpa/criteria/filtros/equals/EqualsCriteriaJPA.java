package br.com.rhiemer.api.jpa.criteria.filtros.equals;

import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;

public class EqualsCriteriaJPA extends FiltroCriteriaJPA {

	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		return getBuilder().equal(path, filtro);
	}
	

}
