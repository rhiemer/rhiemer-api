package br.com.rhiemer.api.jpa.criteria.filtros.between;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.filtros.FiltroCriteriaJPA;

public abstract class FiltroCriteriaIntervaloAtributoJPA extends FiltroCriteriaJPA {

	
	public abstract Expression buildFiltroIntervalo(String path1,String path2, Object filtro);
	public abstract Expression buildFiltroIntervalo(Attribute[] path1,Attribute[] path2, Object filtro);
	
	
	@Override
	public Expression buildSingular(Expression path, Object filtro) {
		// TODO Auto-generated method stub
		return null;
	}

}
