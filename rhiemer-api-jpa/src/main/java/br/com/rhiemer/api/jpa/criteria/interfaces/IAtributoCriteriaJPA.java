package br.com.rhiemer.api.jpa.criteria.interfaces;

import java.util.List;

import javax.persistence.criteria.From;

import br.com.rhiemer.api.jpa.criteria.create.RetornoCreateCriteriaJPA;
import br.com.rhiemer.api.util.helper.Helper;

public interface IAtributoCriteriaJPA {
	
	
	
	
	default public From getJoin(From root,String atributo)
	{
		return root;
		
	}

}
