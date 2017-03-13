package br.com.rhiemer.api.jpa.criteria.filtros.between;

import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;

public abstract class FiltroCriteriaIntervaloValorJPA extends FiltroCriteriaJPA {

	
	public abstract Expression buildFiltroIntervalo(Object filtro1, Object filtro2);
	
	
	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		// TODO Auto-generated method stub
		return null;
	}

}
