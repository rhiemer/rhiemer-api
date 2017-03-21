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
import br.com.rhiemer.api.jpa.criteria.join.FetchCriteriaJPA;
import br.com.rhiemer.api.jpa.execucao.IJPAExecucao;

public interface IBuilderFiltrosFetch extends ICriteriaJPA {

	List<ParametrizarCriteriaJPAParametro> getFetchs();

	default IBuilderFiltrosFetch addFetchs(List<ParametrizarCriteriaJPAParametro> filtros) {
		getFetchs().addAll(filtros);
		return this;
	}
	
	default IBuilderFiltrosFetch fetch(String atributo) {
		getFetchs().add(new ParametrizarCriteriaJPAParametro().setClasse(FetchCriteriaJPA.class).setAtributo(atributo));
		return this;
	}
	
	default IBuilderFiltrosFetch fetch(Attribute... atributes) {
		getFetchs().add(new ParametrizarCriteriaJPAParametro().setClasse(FetchCriteriaJPA.class).setAttributes(atributes));
		return this;
	}	
	
	default List<Predicate> builderFetchs(CriteriaBuilder builder, Root root, CriteriaQuery query) {
		List<Predicate> predicates = new ArrayList<>();
		if (getFetchs() != null && getFetchs().size() > 0)
			getFetchs().stream().forEach(t -> t.build(builder, root, query, predicates));
		return predicates;
	}

}
