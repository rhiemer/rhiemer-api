package br.com.rhiemer.api.jpa.builder;

import java.util.List;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.join.builder.IBuilderFiltrosFetch;
import br.com.rhiemer.api.jpa.criteria.join.builder.IBuilderFiltrosJoin;
import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderFiltrosJPA;
import br.com.rhiemer.api.jpa.criteria.orderby.IBuilderOrderBy;
import br.com.rhiemer.api.jpa.criteria.query.parametros.IBuilderQueryParametros;
import br.com.rhiemer.api.jpa.criteria.subquery.IBuilderSubQuery;

public interface IBuilderMetodosFiltrosJPA<T> extends IBuilderFiltrosJPA<T>, IBuilderOrderBy<T>,
		IBuilderQueryParametros<T>, IBuilderFiltrosJoin<T>, IBuilderFiltrosFetch<T>, IBuilderSubQuery<T> {

	default List<Predicate> builderAll(CriteriaBuilder builder, Root root, AbstractQuery query) {
		this.builderQueryParametros(builder, root, query);
		this.builderSubQuerys(builder, root, query);
		this.builderJoins(builder, root, query);
		List<Predicate> predicates = this.builderMetodos(builder, root, query);
		if (query instanceof CriteriaQuery)
			this.builderOrderBy(builder, root, (CriteriaQuery) query);
		this.builderFetchs(builder, root, query);
		return predicates;
	}

}
