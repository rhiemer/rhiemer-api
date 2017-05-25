package br.com.rhiemer.api.jpa.criteria.juncao;

import java.util.List;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface IBuilderMetodosJuncaoJPAOr extends IBuilderMetodosJuncaoJPA {

	default Predicate builderJuncao(List<Predicate> predicates, CriteriaBuilder builder, Root root,
			AbstractQuery query) {
		return builder.or(predicates.toArray(new Predicate[] {}));
	}

}
