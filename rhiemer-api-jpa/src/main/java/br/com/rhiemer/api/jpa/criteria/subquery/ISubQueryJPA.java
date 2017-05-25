package br.com.rhiemer.api.jpa.criteria.subquery;

import java.util.List;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;
import br.com.rhiemer.api.jpa.helper.HelperSubQueryCriteria;

public interface ISubQueryJPA<T> extends ICriteriaJPA {

	Class<?> getClasseSubQuery();

	String getAliasSubQuery();

	SubQueryRetornoDTO builderSubQueryMetodos(CriteriaBuilder builder, Root root, AbstractQuery query);

	default void adicionarFiltros(List<Predicate> predicates, CriteriaBuilder builder, Root root, AbstractQuery query) {
		query.where(predicates.toArray(new Predicate[] {}));
	}

	default SubQueryRetornoDTO builderSubQuery(AbstractQuery query) {
		SubQueryRetornoDTO subQueryRetornoDTO = HelperSubQueryCriteria.subQuery(query, getClasseSubQuery());
		return subQueryRetornoDTO;
	}

}
