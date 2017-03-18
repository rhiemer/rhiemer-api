package br.com.rhiemer.api.jpa.builder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.join.builder.IBuilderFiltrosFetch;
import br.com.rhiemer.api.jpa.criteria.join.builder.IBuilderFiltrosJoin;
import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderFiltrosJPA;
import br.com.rhiemer.api.jpa.criteria.orderby.IBuilderOrderBy;

public interface IBuilderMetodosFiltrosJPA
		extends IBuilderFiltrosJPA, IBuilderOrderBy, IBuilderFiltrosJoin, IBuilderFiltrosFetch {

	default List<Predicate> builderAll(CriteriaBuilder builder, Root root, CriteriaQuery query)
	{
		this.builderJoins(builder, root, query);
		List<Predicate> predicates = this.builderMetodos(builder, root, query);
		this.builderOrderBy(builder, root, query);
		this.builderFetchs(builder, root, query);
		return predicates;
	}

}
