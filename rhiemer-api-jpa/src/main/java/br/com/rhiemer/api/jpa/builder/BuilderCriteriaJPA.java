package br.com.rhiemer.api.jpa.builder;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;
import br.com.rhiemer.api.util.dto.Pager;
import br.com.rhiemer.api.util.helper.Helper;

public class BuilderCriteriaJPA extends BuilderCriteria implements IBuilderMetodosFiltrosExecucaoJPA<BuilderCriteriaJPA> {

	private List<ParametrizarCriteriaJPAParametro> filtros = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> orderBys = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> joins = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> fetchs = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> queryParametros = new ArrayList<>();
	

	public BuilderCriteriaJPA()
	{
		super();
	}

	public BuilderCriteriaJPA(BuilderCreate builder) {
		super();
		this.setCreateClass(builder.createClass);
		this.setResultClass(builder.resultClass);
		this.setPager(builder.pager);
		this.setParametrizarCriteria(builder.parametrizarCriteria);
		this.setResultMaping(builder.resultMaping);
		this.setTransformMap(builder.transformMap);
		this.setParametrosExecucao(builder.parametrosExecucao);
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
	public List<ParametrizarCriteriaJPAParametro> getQueryParametros() {
		return queryParametros;
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
	
	public static BuilderCreate builderCreate() {
		return new BuilderCreate();
	}
	
	

	public static class BuilderCreate {

		private Class<?> createClass;
		private boolean transformMap;
		private Class<?> resultClass;
		private Pager pager;
		private ParametrizarCriteria parametrizarCriteria;
		private String resultMaping;
		private IExecucao[] parametrosExecucao;

		public BuilderCreate createClass(Class<?> createClass) {
			this.createClass = createClass;
			return this;
		}

		public BuilderCreate resultClass(Class<?> resultClass) {
			this.resultClass = resultClass;
			return this;
		}

		public BuilderCreate pager(Pager pager) {
			this.pager = pager;
			return this;
		}

		public BuilderCreate parametrizarCriteria(ParametrizarCriteria parametrizarCriteria) {
			this.parametrizarCriteria = parametrizarCriteria;
			return this;
		}

		public BuilderCreate resultMaping(String resultMaping) {
			this.resultMaping = resultMaping;
			return this;
		}

		public BuilderCreate transformMap(boolean transformMap) {
			this.transformMap = transformMap;
			return this;
		}
		
		public BuilderCreate parametrosExecucao(IExecucao... parametrosExecucao) {
			this.parametrosExecucao = parametrosExecucao;
			return this;
		}

		public BuilderCriteriaJPA build() {
			return new BuilderCriteriaJPA(this);
		}

	}

	

	

}
