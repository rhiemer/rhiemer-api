package br.com.rhiemer.api.jpa.builder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderMetodosJuncaoJPAWhere;

public interface IBuilderMetodosFiltrosWhere<T> extends IBuilderMetodosFiltrosExecucaoJPA<T>, IBuilderMetodosJuncaoJPAWhere {

	default CriteriaQuery builderQuery(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		List<Predicate> predicates = this.builderAll(builder, root, query);
		if (predicates != null && predicates.size() > 0)
			return query.where(predicates.toArray(new Predicate[] {}));
		else
			return query;
	}

}
