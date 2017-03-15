package br.com.rhiemer.api.jpa.criteria.builder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.builder.ParametrizarCriteria;

public class ParametrizarCriteriaJPA implements ParametrizarCriteria {
	
	
	public ParametrizarCriteriaJPA()
	{
		super();
	}
	
	public void equals(String atributo,Object... filtros)
	{
		new ParametrizarCriteriaJPAParametro().setAtributo(atributo).setValues(filtros);
	}

	@Override
	public CriteriaQuery parametrizar(CriteriaBuilder builder, CriteriaQuery query, Root from,
			List<Predicate> predicates) {
		// TODO Auto-generated method stub
		return null;
	}

}
