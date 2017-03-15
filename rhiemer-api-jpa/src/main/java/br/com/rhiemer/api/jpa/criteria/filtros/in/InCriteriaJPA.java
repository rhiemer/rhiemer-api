package br.com.rhiemer.api.jpa.criteria.filtros.in;

import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaArrayJPA;

public class InCriteriaJPA extends FiltroCriteriaArrayJPA {

	@Override
	protected Expression buildArray(Expression path, Object[] filtro) {
		return path.in(filtro);
	}

}
