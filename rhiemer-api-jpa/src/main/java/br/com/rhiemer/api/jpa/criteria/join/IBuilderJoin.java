package br.com.rhiemer.api.jpa.criteria.join;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;

public interface IBuilderJoin extends ICriteriaJPA {

	List<ParametrizarCriteriaJPAParametro> getOrderBys();

	default IBuilderJoin addFiltros(List<ParametrizarCriteriaJPAParametro> filtros) {
		getOrderBys().addAll(filtros);
		return this;
	}

	default List<Predicate> builderOrderBy(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		List<Predicate> predicates = new ArrayList<>();
		if (getOrderBys() != null && getOrderBys().size() > 0)
			getOrderBys().stream().forEach(t -> t.build(builder, root, query, predicates));
		return predicates;
	}

}
