package br.com.rhiemer.api.jpa.criteria.filtros;

public abstract class FiltroCriteriaArrayJPA extends FiltroCriteriaJPA {
	

	
	@Override
	protected Boolean getFiltroIsArray() {
		return true;
	}
	
	

}
