package br.com.rhiemer.api.jpa.builder;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderFiltrosJPA;

public class BuilderCriteriaJPA extends BuilderCriteria implements IBuilderFiltrosJPA {

	private List<ParametrizarCriteriaJPAParametro> filtros = new ArrayList<>();
	
	public BuilderCriteriaJPA(Builder builder) {
		super(builder);
	}

	@Override
	public List<ParametrizarCriteriaJPAParametro> getFiltros() {
		return this.filtros;
	}
	
	@Override
	protected ParametrizarCriteria getParametrizarCriteriaInternal()
	{
		final BuilderCriteriaJPA _builderCriteriaJPA=this;
		return new ParametrizarCriteria() {
			@Override
			public CriteriaQuery parametrizar(CriteriaBuilder builder, CriteriaQuery query, Root from,
					List<Predicate> predicates) {
				final List<Predicate> _predicates = _builderCriteriaJPA.builderMetodos(builder,from,query);
				predicates.addAll(_predicates);
				return query;
			}
		};
	}

}
