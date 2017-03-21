package br.com.rhiemer.api.jpa.builder;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.execucao.IJPAExecucao;
import br.com.rhiemer.api.util.helper.Helper;

public class BuilderCriteriaJPA extends BuilderCriteria implements IBuilderMetodosFiltrosExecucaoJPA {

	private List<ParametrizarCriteriaJPAParametro> filtros = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> orderBys = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> joins = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> fetchs = new ArrayList<>();
	private List<IJPAExecucao> parametrosExecucao = new ArrayList<>();

	

	public BuilderCriteriaJPA(Builder builder) {
		super(builder);
	}
	
	@Override
	public List<IJPAExecucao> getParametrosExecucao() {
		return this.parametrosExecucao;
	}
	
	public BuilderCriteriaJPA setParametrosExecucao(IJPAExecucao... parametrosExecucao) {
		this.parametrosExecucao = Helper.convertArgs(parametrosExecucao);
		return this;
	}

	@Override
	public List<ParametrizarCriteriaJPAParametro> getFiltros() {
		return this.filtros;
	}
	
	@Override
	public List<ParametrizarCriteriaJPAParametro> getOrderBys() {
		return this.orderBys;
	}

	@Override
	public List<ParametrizarCriteriaJPAParametro> getJoins() {
		return this.joins;
	}

	@Override
	public List<ParametrizarCriteriaJPAParametro> getFetchs() {
		return this.fetchs;
	}

	@Override
	protected ParametrizarCriteria getParametrizarCriteriaInternal() {
		final BuilderCriteriaJPA _builderCriteriaJPA = this;
		return new ParametrizarCriteria() {
			@Override
			public CriteriaQuery parametrizar(CriteriaBuilder builder, CriteriaQuery query, Root from,
					List<Predicate> predicates) {
				final List<Predicate> _predicates = _builderCriteriaJPA.builderAll(builder, from, query);
				predicates.addAll(_predicates);
				return query;
			}
		};
	}

	

	

}
