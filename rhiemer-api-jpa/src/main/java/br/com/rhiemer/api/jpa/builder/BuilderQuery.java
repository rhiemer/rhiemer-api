package br.com.rhiemer.api.jpa.builder;

import static br.com.rhiemer.api.jpa.enums.EnumTipoQuery.arquivo;
import static br.com.rhiemer.api.jpa.enums.EnumTipoQuery.nativeQuery;
import static br.com.rhiemer.api.jpa.enums.EnumTipoQuery.query;
import static br.com.rhiemer.api.jpa.enums.EnumTipoQuery.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.jpa.builder.BuilderCriteria.Builder;
import br.com.rhiemer.api.jpa.enums.EnumTipoQuery;
import br.com.rhiemer.api.jpa.helper.HelperAtributeJPA;
import br.com.rhiemer.api.jpa.helper.JPAUtils;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;
import br.com.rhiemer.api.util.dto.Arquivo;
import br.com.rhiemer.api.util.dto.Pager;
import br.com.rhiemer.api.util.helper.FileUtils;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.HelperPojoKey;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public class BuilderQuery implements BuildJPA {

	private EnumTipoQuery tipoQuery = query;
	private String sql;
	private Class<?> resultClass;
	private Pager pager;
	private Map<Object, Object> parameters;
	private ParametrizarQuery parametrizarQuery;
	private String resultMaping;
	private Boolean transformMap = false;
	private List<IExecucao> parametrosExecucao = new ArrayList<>();

	public BuilderQuery(Builder builder) {
		super();
		this.tipoQuery = builder.tipoQuery;
		this.sql = builder.sql;
		this.resultClass = builder.resultClass;
		this.pager = builder.pager;
		this.parameters = builder.parameters;
		this.parametrizarQuery = builder.parametrizarQuery;
		this.resultMaping = builder.resultMaping;
		this.transformMap = builder.transformMap;
		this.setParametrosExecucao(builder.parametrosExecucao);
	}

	public EnumTipoQuery getTipoQuery() {
		return tipoQuery;
	}

	public void setTipoQuery(EnumTipoQuery tipoQuery) {
		this.tipoQuery = tipoQuery;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Class<?> getResultClass() {
		return resultClass;
	}

	public void setResultClass(Class<?> resultClass) {
		this.resultClass = resultClass;
	}

	public Map<Object, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<Object, Object> parameters) {
		this.parameters = parameters;
	}

	public ParametrizarQuery getParametrizarQuery() {
		return parametrizarQuery;
	}

	public void setParametrizarQuery(ParametrizarQuery parametrizarQuery) {
		this.parametrizarQuery = parametrizarQuery;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getResultMaping() {
		return resultMaping;
	}

	protected void setResultMaping(String resultMaping) {
		this.resultMaping = resultMaping;
	}

	public List<IExecucao> getParametrosExecucao() {
		return this.parametrosExecucao;
	}

	public BuilderQuery setParametrosExecucao(IExecucao... parametrosExecucao) {
		this.parametrosExecucao = Helper.convertArgs(parametrosExecucao);
		return this;
	}

	protected Arquivo buscarArquivo() {
		Arquivo result = null;
		if (tipoQuery.equals(resource)) {
			result = FileUtils.getArquivoResource(this.sql);
		} else if (tipoQuery.equals(arquivo)) {
			result = FileUtils.getArquivoCaminhoFisico(this.sql);
		}
		return result;
	}

	protected Query createQuery(EntityManager em) {
		Query query = null;
		String _sql = sql;
		EnumTipoQuery tipoQuery = this.tipoQuery;
		Class<?> _resultClass = this.resultClass;

		if (!JPAUtils.verifcaNamedQuery(em.getEntityManagerFactory(), this.sql)) {
			Arquivo arquivo = buscarArquivo();
			if (arquivo != null) {
				if ("sql".equalsIgnoreCase(FilenameUtils.getExtension(arquivo.getNome()))) {
					tipoQuery = nativeQuery;
				} else {
					tipoQuery = EnumTipoQuery.query;
				}
				_sql = new String(arquivo.getConteudo());
			}
		}

		if (transformMap) {
			_resultClass = Map.Entry.class;
		}

		switch (tipoQuery) {
		case namedQuery:
			if (this.resultClass != null)
				query = em.createNamedQuery(_sql, _resultClass);
			else
				query = em.createNamedQuery(_sql);
			break;
		case nativeQuery:
			if (!StringUtils.isBlank(this.resultMaping))
				query = em.createNativeQuery(_sql, resultMaping);
			else if (resultClass != null)
				query = em.createNativeQuery(_sql, _resultClass);
			else
				query = em.createNativeQuery(_sql);
			break;
		case procedure:
			if (!StringUtils.isBlank(this.resultMaping))
				query = em.createStoredProcedureQuery(_sql, resultMaping);
			else if (resultClass != null)
				query = em.createStoredProcedureQuery(_sql, _resultClass);
			else
				query = em.createStoredProcedureQuery(_sql);
			break;
		default:
			if (resultClass != null)
				query = em.createQuery(_sql, _resultClass);
			else
				query = em.createQuery(_sql);
			break;
		}

		return query;
	}

	public BuilderQuery addParameterPrimaryKey(Object... params) {
		if (this.parameters == null)
			this.parameters = new HashMap<>();
		this.parameters.putAll(HelperPojoKey.mapPrimaryKey(getResultClass(), params));
		return this;
	}

	protected void paginar(Query query) {
		if (pager != null) {
			pager.paginarQuery(query);
		}
	}

	public Query buildQuery(EntityManager em) {
		Query query = createQuery(em);
		paginar(query);
		if (this.parametrizarQuery != null)
			this.parametrizarQuery.parametrizar(query);
		JPAUtils.fillParameters(query, this.parameters);
		return query;
	}

	public static interface ParametrizarQuery {
		void parametrizar(Query query);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		public boolean transformMap;
		private EnumTipoQuery tipoQuery = EnumTipoQuery.query;
		private String sql;
		private Class<?> resultClass;
		private Pager pager;
		private Map<Object, Object> parameters;
		private ParametrizarQuery parametrizarQuery;
		private String resultMaping;
		private IExecucao[] parametrosExecucao;

		public Builder tipoQuery(EnumTipoQuery tipoQuery) {
			this.tipoQuery = tipoQuery;
			return this;
		}

		public Builder sql(String sql) {
			this.sql = sql;
			return this;
		}

		public Builder resultClass(Class<?> resultClass) {
			this.resultClass = resultClass;
			return this;
		}

		public Builder pager(Pager pager) {
			this.pager = pager;
			return this;
		}

		public Builder parametrizarQuery(ParametrizarQuery parametrizarQuery) {
			this.parametrizarQuery = parametrizarQuery;
			return this;
		}

		public Builder resultMaping(String resultMaping) {
			this.resultMaping = resultMaping;
			return this;
		}

		public Builder parameters(Map<Object, Object> parameters) {
			this.parameters = parameters;
			return this;
		}

		public Builder addParameter(Object key, Object value) {
			if (this.parameters == null)
				this.parameters = new HashMap<>();
			this.parameters.put(key, value);
			return this;
		}

		public Builder transformMap(boolean transformMap) {
			this.transformMap = transformMap;
			return this;
		}

		public Builder parametrosExecucao(IExecucao... parametrosExecucao) {
			this.parametrosExecucao = parametrosExecucao;
			return this;
		}

		public BuilderQuery build() {
			return new BuilderQuery(this);
		}

	}

}
