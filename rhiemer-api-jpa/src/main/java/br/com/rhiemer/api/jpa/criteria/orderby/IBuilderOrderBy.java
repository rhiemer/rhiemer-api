package br.com.rhiemer.api.jpa.criteria.orderby;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;

public interface IBuilderOrderBy<T> extends ICriteriaJPA {

	List<ParametrizarCriteriaJPAParametro> getOrderBys();

	default T addOrderBy(List<ParametrizarCriteriaJPAParametro> filtros) {
		getOrderBys().addAll(filtros);
		return (T)this;
	}
	
	default T orderBy(String atributo) {
		getOrderBys().add(new ParametrizarCriteriaJPAParametro().setClasse(OrderByCriteriaJPAAsc.class).setAtributo(atributo));
		return (T)this;
	}
	
	default T orderBy(Attribute... atributes) {
		getOrderBys().add(new ParametrizarCriteriaJPAParametro().setClasse(OrderByCriteriaJPAAsc.class).setAttributes(atributes));
		return (T)this;
	}
	
	default T orderByDesc(String atributo) {
		getOrderBys().add(new ParametrizarCriteriaJPAParametro().setClasse(OrderByCriteriaJPADesc.class).setAtributo(atributo));
		return (T)this;
	}
	
	default T orderByDesc(Attribute... atributes) {
		getOrderBys().add(new ParametrizarCriteriaJPAParametro().setClasse(OrderByCriteriaJPADesc.class).setAttributes(atributes));
		return (T)this;
	}

	default List<Predicate> builderOrderBy(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		List<Predicate> predicates = new ArrayList<>();
		if (getOrderBys() != null && getOrderBys().size() > 0)
			getOrderBys().stream().forEach(t -> t.build(builder, root, query, predicates));
		return predicates;
	}

}
