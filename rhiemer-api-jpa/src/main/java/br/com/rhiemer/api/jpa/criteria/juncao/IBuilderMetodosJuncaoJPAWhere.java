package br.com.rhiemer.api.jpa.criteria.juncao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface IBuilderMetodosJuncaoJPAWhere extends IBuilderMetodosJuncaoJPAAnd {
	
	default CriteriaQuery builderQuery(CriteriaBuilder builder, Root root, CriteriaQuery query)
	{
		Predicate predicate = builderMetodoJuncao(builder,root,query);
		return query.where(predicate);
	}

}
