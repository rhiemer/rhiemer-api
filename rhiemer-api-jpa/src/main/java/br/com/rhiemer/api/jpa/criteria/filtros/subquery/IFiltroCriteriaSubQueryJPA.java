package br.com.rhiemer.api.jpa.criteria.filtros.subquery;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.filtros.IFiltroCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.subquery.SubQueryRetornoDTO;

public interface IFiltroCriteriaSubQueryJPA extends IFiltroCriteriaJPA {

	Predicate builder(CriteriaBuilder builder, AbstractQuery query, Root root,SubQueryRetornoDTO subQueryRetornoDTO);

}
