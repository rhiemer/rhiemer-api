package br.com.rhiemer.api.jpa.criteria.juncao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;

public interface IBuilderMetodosJPA extends ICriteriaJPA {
	
	
	List<ParametrizarCriteriaJPAParametro> getFiltros();
	
	default IBuilderMetodosJPA addFiltros(List<ParametrizarCriteriaJPAParametro> filtros)
	{
		getFiltros().addAll(filtros);
		return this;
	}
	
	default List<Predicate> builderMetodos(CriteriaBuilder builder, Root root, AbstractQuery query)
	{   
		List<Predicate> predicates = new ArrayList<>();
		if (getFiltros() != null && getFiltros().size() > 0)
		 getFiltros().stream().forEach(t->t.build(builder, root, query, predicates));
		return predicates;		
	}
	
	
	

}
