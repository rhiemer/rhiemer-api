package br.com.rhiemer.api.jpa.criteria.filtros.exists;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import br.com.rhiemer.api.jpa.criteria.filtros.subquery.AbstractFiltroCriteriaSubQueryJPA;
import br.com.rhiemer.api.jpa.criteria.subquery.SubQueryRetornoDTO;

public class ExistsCriteriaJPA extends AbstractFiltroCriteriaSubQueryJPA {

	@Override
	public Predicate builder(CriteriaBuilder builder, AbstractQuery query, Root root,
			SubQueryRetornoDTO subQueryRetornoDTO) {
		return builder.exists((Subquery) subQueryRetornoDTO.getSubquery());
	}

}
