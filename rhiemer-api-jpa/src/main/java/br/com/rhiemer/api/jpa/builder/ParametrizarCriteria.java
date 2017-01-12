package br.com.rhiemer.api.jpa.builder;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface ParametrizarCriteria {

	CriteriaQuery parametrizar(CriteriaBuilder builder, CriteriaQuery query, Root from,List<Predicate> predicates);

}
