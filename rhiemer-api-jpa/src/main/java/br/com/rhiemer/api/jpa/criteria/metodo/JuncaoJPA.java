package br.com.rhiemer.api.jpa.criteria.metodo;

import br.com.rhiemer.api.jpa.criteria.create.CreateCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.interfaces.IMetodoCriteriaJPA;
import br.com.rhiemer.api.util.criteria.MetodoCriteria;

public abstract class JuncaoJPA<T extends CreateCriteriaJPA> extends MetodoCriteria<T> implements IMetodoCriteriaJPA<T> {
	
}
