package br.com.rhiemer.api.jpa.criteria.juncao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface IBuilderMetodosJuncaoJPA extends IBuilderMetodosJPA {
	
	Predicate builderJuncao(List<Predicate> predicates,CriteriaBuilder builder, Root root, CriteriaQuery query);
	default Boolean getNot()
	{
		return false;
	}
	
	default Predicate builderMetodoJuncao(CriteriaBuilder builder, Root root, CriteriaQuery query)
	{
		List<Predicate> predicates = builderMetodos(builder, root, query);
		Predicate _predicate = builderJuncao(predicates,builder, root, query);
		if (getNot())
		 _predicate=builder.not(_predicate);	
		return _predicate;
	}

}
