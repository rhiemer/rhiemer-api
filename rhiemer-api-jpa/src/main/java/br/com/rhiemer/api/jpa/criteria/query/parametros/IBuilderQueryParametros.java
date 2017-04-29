package br.com.rhiemer.api.jpa.criteria.query.parametros;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.query.distinct.QueryDistinct;

public interface IBuilderQueryParametros<T> extends ICriteriaJPA {

	List<ParametrizarCriteriaJPAParametro> getQueryParametros();

	default Boolean getDistinct() {
		return false;
	}

	default T distinct() {
		return distinct(true);
	}

	default T distinct(Boolean distinct) {
		getQueryParametros()
				.add(new ParametrizarCriteriaJPAParametro().setObjeto(new QueryDistinct().setDistinct(getDistinct())));
		return (T) this;
	}

	default List<Predicate> builderQueryParametros(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		List<Predicate> predicates = new ArrayList<>();
		if (getQueryParametros() != null && getQueryParametros().size() > 0)
			getQueryParametros().stream().forEach(t -> t.build(builder, root, query, predicates));
		return predicates;
	}

}
