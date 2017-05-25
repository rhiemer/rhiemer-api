package br.com.rhiemer.api.jpa.criteria.juncao;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface IBuilderMetodosJuncaoJPAWhere extends IBuilderMetodosJuncaoJPAAnd {
	
	default AbstractQuery builderQuery(CriteriaBuilder builder, Root root, AbstractQuery query)
	{
		Predicate predicate = builderMetodoJuncao(builder,root,query);
		return query.where(predicate);
	}

}
