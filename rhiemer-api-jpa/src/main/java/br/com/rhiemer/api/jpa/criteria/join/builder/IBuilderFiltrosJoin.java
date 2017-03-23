package br.com.rhiemer.api.jpa.criteria.join.builder;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.join.JoinCriteriaJPA;

public interface IBuilderFiltrosJoin<T> extends ICriteriaJPA {

	List<ParametrizarCriteriaJPAParametro> getJoins();

	default T addJoins(List<ParametrizarCriteriaJPAParametro> filtros) {
		getJoins().addAll(filtros);
		return (T)this;
	}
	
	default T join(String atributo) {
		getJoins().add(new ParametrizarCriteriaJPAParametro().setClasse(JoinCriteriaJPA.class).setAtributo(atributo));
		return (T)this;
	}
	
	default T join(Attribute... atributes) {
		getJoins().add(new ParametrizarCriteriaJPAParametro().setClasse(JoinCriteriaJPA.class).setAttributes(atributes));
		return (T)this;
	}	
	
	default List<Predicate> builderJoins(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		List<Predicate> predicates = new ArrayList<>();
		if (getJoins() != null && getJoins().size() > 0)
			getJoins().stream().forEach(t -> t.build(builder, root, query, predicates));
		return predicates;
	}

}
