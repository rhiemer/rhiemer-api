package br.com.rhiemer.api.jpa.criteria.subquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rhiemer.api.jpa.builder.IBuilderMetodosFiltrosJPA;
import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.juncao.IBuilderFiltrosJPA;
import br.com.rhiemer.api.jpa.criteria.juncao.MetodosJuncaoJPA;
import br.com.rhiemer.api.util.helper.HelperFindEntityClass;

public class SubQueryJPA<T> extends MetodosJuncaoJPA<T> implements ISubQueryJPA<T>, IBuilderMetodosFiltrosJPA<T> {

	private Class<?> classe;
	private String classeString;
	private String alias;

	private List<ParametrizarCriteriaJPAParametro> filtros = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> orderBys = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> joins = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> fetchs = new ArrayList<>();
	private List<ParametrizarCriteriaJPAParametro> queryParametros = new ArrayList<>();
	private Map<String, SubQueryJPA> mapSubQueryAlias = new HashMap<>();
	private Map<String, SubQueryRetornoDTO> mapSubQueryJpaRoot;

	public SubQueryJPA() {
		super();
	}

	public SubQueryJPA(IBuilderFiltrosJPA<T> anterior) {
		super(anterior);
	}

	public SubQueryJPA(IBuilderFiltrosJPA<T> anterior, String classeString) {
		super(anterior);
		this.classeString = classeString;
	}

	public SubQueryJPA(IBuilderFiltrosJPA<T> anterior, Class<?> classe) {
		super(anterior);
		this.classe = classe;
	}

	public SubQueryJPA(String classeString) {
		super();
		this.classeString = classeString;
	}


	public SubQueryJPA(Class<?> classe) {
		super();
		this.classe = classe;
	}
	
	public SubQueryJPA setMapSubQueryJpaRoot(Map<String, SubQueryRetornoDTO> mapSubQueryJpaRoot) {
		this.mapSubQueryJpaRoot = mapSubQueryJpaRoot;
		return this;
	}


	@Override
	public SubQueryRetornoDTO builderSubQueryMetodos(CriteriaBuilder builder, Root root, AbstractQuery query) {
		SubQueryRetornoDTO result = builderSubQuery(query);
		List<Predicate> predicates = builderAll(builder, result.getRoot(), result.getSubquery());
		adicionarFiltros(predicates, builder, result.getRoot(), result.getSubquery());
		return result;
	}

	@Override
	public Class<?> getClasseSubQuery() {
		return Optional.ofNullable(this.classe)
				.orElse(HelperFindEntityClass.findEntityByString(this.classeString, Entity.class));
	}

	@Override
	public String getAliasSubQuery() {
		return Optional.ofNullable(this.alias).map(t -> t)
				.orElse(HelperFindEntityClass.aliasClass(getClasseSubQuery()));
	}

	public Class<?> getClasse() {
		return classe;
	}

	public SubQueryJPA setClasse(Class<?> classe) {
		this.classe = classe;
		return this;
	}

	public String getClasseString() {
		return classeString;
	}

	public SubQueryJPA setClasseString(String classeString) {
		this.classeString = classeString;
		return this;
	}

	public String getAlias() {
		return alias;
	}

	public SubQueryJPA setAlias(String alias) {
		this.alias = alias;
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
	public List<ParametrizarCriteriaJPAParametro> getQueryParametros() {
		return queryParametros;
	}

	@Override
	public Map<String, SubQueryJPA> getMapSubQueryAlias() {
		return this.mapSubQueryAlias;
	}

	@Override
	public Map<String, SubQueryRetornoDTO> getMapSubQueryJpaRoot() {
		return this.mapSubQueryJpaRoot;
	}

}
