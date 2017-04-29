package br.com.rhiemer.api.jpa.builder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.execucao.builder.IBuilderExecucaoAtributos;
import br.com.rhiemer.api.jpa.criteria.execucao.builder.IBuilderExecucaoOrderBy;
import br.com.rhiemer.api.jpa.criteria.filtros.uniquekey.FiltroCriteriaUniqueKeyJPA;
import br.com.rhiemer.api.jpa.helper.HelperQueryCriteria;

public interface IBuilderMetodosFiltrosExecucaoJPA<T> extends FiltroCriteriaUniqueKeyJPA<T>,
		IBuilderMetodosFiltrosJPA<T>, IBuilderExecucaoAtributos, IBuilderExecucaoOrderBy {

	default List<Predicate> builderAll(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		builderExecucaoAtributos(builder, root, query);
		List<Predicate> predicates = IBuilderMetodosFiltrosJPA.super.builderAll(builder, root, query);
		builderExecucaoOrderBy(builder, root, query);
		HelperQueryCriteria.setQueyDistinctJoin(query, root);
		return predicates;
	}

}
