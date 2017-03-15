package br.com.rhiemer.api.jpa.criteria.filtros;

import javax.persistence.criteria.Expression;

public abstract class FiltroCriteriaArrayJPA extends FiltroCriteriaJPA {
	
	@Override
    protected abstract Expression buildArray(Expression path, Object[] filtro);	
	
	@Override
	protected Boolean getFiltroIsArray() {
		return true;
	}
	
	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		// TODO Auto-generated method stub
		return null;
	}

}
