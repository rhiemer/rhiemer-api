package br.com.rhiemer.api.jpa.criteria.filtros;

import static javax.persistence.criteria.JoinType.INNER;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.atributos.AbstractAtributoCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.subquery.SubQueryRetornoDTO;
import br.com.rhiemer.api.jpa.helper.HelperAtributeJPA;
import br.com.rhiemer.api.jpa.helper.HelperRootCriteria;
import br.com.rhiemer.api.util.helper.Helper;

public abstract class FiltroCriteriaJPA extends AbstractAtributoCriteriaJPA implements IFiltroCriteriaJPA {

	private final String PATTERN_ALIAS_SUBQUERY = "(\\{(.+?)\\})+";

	private CriteriaBuilder builder;
	private Boolean not = false;
	private Boolean caseSensitve = true;
	private Boolean includeNull = false;
	private Boolean isExpression = false;
	private Map<String, SubQueryRetornoDTO> mapSubQueryJpaRoot;

	public FiltroCriteriaJPA() {
		this.setJoinType(INNER);
	}

	public Boolean getIsExpression() {
		return isExpression;
	}

	public CriteriaBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(CriteriaBuilder builder) {
		this.builder = builder;
	}

	public Boolean getNot() {
		return not;
	}

	public Boolean getCaseSensitve() {
		return caseSensitve;
	}

	public Boolean getIncludeNull() {
		return includeNull;
	}

	public FiltroCriteriaJPA setNot(Boolean not) {
		this.not = not;
		return this;
	}

	public FiltroCriteriaJPA setIncludeNull(Boolean includeNull) {
		this.includeNull = includeNull;
		return this;
	}

	public FiltroCriteriaJPA setCaseSensitve(Boolean caseSensitve) {
		this.caseSensitve = caseSensitve;
		return this;
	}

	public FiltroCriteriaJPA setIsExpression(Boolean isExpression) {
		this.isExpression = isExpression;
		return this;
	}

	public FiltroCriteriaJPA setMapSubQueryJpaRoot(Map<String, SubQueryRetornoDTO> mapSubQueryJpaRoot) {
		this.mapSubQueryJpaRoot = mapSubQueryJpaRoot;
		return this;
	}

	public Expression build(Object... filtros) {
		Path path = builderAtributoCriteria();
		return buildFiltro(path, filtros);
	}

	public Map<String, SubQueryRetornoDTO> getMapSubQueryJpaRoot() {
		return this.mapSubQueryJpaRoot;
	}

	protected Boolean getFiltroIsArray() {
		return false;
	}

	protected String[] getRootSubQueryField(String field) {
		Pattern pattern = Pattern.compile(PATTERN_ALIAS_SUBQUERY);
		Matcher matcher = pattern.matcher(field);
		if (matcher.find()) {
			String _group1 = matcher.group(1);
			String strMatcher = matcher.group(2);
			if (field.indexOf(_group1) == 0) {
				String[] result = new String[2];
				result[0] = strMatcher;
				result[1] = field.substring(_group1.length() + 1);
				return result;
			}
		}
		return null;

	}

	protected SubQueryRetornoDTO findSubQueryRetornoDTO(String queryAlias) {
		SubQueryRetornoDTO subQueryRetornoDTO = Optional.ofNullable(getMapSubQueryJpaRoot()).map(t -> t.get(queryAlias))
				.orElse(null);
		if (subQueryRetornoDTO != null && subQueryRetornoDTO.getRoot() != null)
			return subQueryRetornoDTO;
		else
			return null;
	}

	protected String atributoStrBuildNull(String atributo) {
		String[] fields = getRootSubQueryField(atributo);
		if (fields != null) {
			SubQueryRetornoDTO subQueryRetornoDTO = findSubQueryRetornoDTO(fields[0]);
			return subQueryRetornoDTO != null ? fields[1] : null;
		} else
			return null;
	}

	protected String atributoStrBuild(String atributo) {
		return Optional.ofNullable(atributoStrBuildNull(atributo)).map(t -> t).orElse(atributo);
	}

	protected String atributoBuild() {
		Attribute[] _attributes = getAttributes();
		if (_attributes != null && _attributes.length > 0)
			return HelperAtributeJPA.attributeToString(_attributes);
		else
			return atributoStrBuild(getAtributo());
	}

	public Expression buildFiltro(Expression path, Object... filtros) {
		List<Object> args = Helper.convertArgs(filtros);
		List<Expression> exps = new ArrayList<>();

		if (!getFiltroIsArray()) {
			args.stream().filter(t -> Helper.isNotBlank(t)).forEach(t -> exps.add(buildValue(path, t)));
		} else {
			Object[] values = args.stream().filter(t -> Helper.isNotBlank(t)).toArray();
			if (values != null && values.length > 0)
				exps.add(buildPathArrayOrExpression(path, values));
		}

		Expression expJunction = null;
		if (exps == null || exps.size() == 0)
			return null;
		else if (exps.size() > 1) {
			Predicate[] array = (Predicate[]) Array.newInstance(exps.get(0).getClass(), 1);
			expJunction = getBuilder().or(exps.toArray(array));
		} else {
			expJunction = exps.get(0);
		}

		Expression result = null;
		if (!getNot()) {
			result = expJunction;
		} else {
			result = getBuilder().not(expJunction);
		}

		return result;

	}

	protected boolean caseInsensitive(Expression path) {
		return !getCaseSensitve() && path.getJavaType().isAssignableFrom(String.class);
	}

	protected Object tranformCaseInsensitive(Expression path, Object filtro) {
		if (!caseInsensitive(path))
			return Helper.convertObjectReflextionVerifiyNull(filtro, path.getJavaType());
		else
			return filtro.toString().toUpperCase().trim();
	}

	protected Expression buildExpression(Expression path) {
		Expression _path = path;
		if (caseInsensitive(path)) {
			_path = getBuilder().upper(path);
			_path = getBuilder().trim(_path);
		}

		if (getIncludeNull()) {
			Expression expInsNull = builder.isNull(path);
			Expression expJunction = builder.or(expInsNull, _path);
			_path = expJunction;
		}

		return _path;
	}

	protected Expression buildIsExpression(Object filtro, From from) {
		Expression exp = HelperRootCriteria.getExpressionObj(from, getFecth(), getJoinType(), filtro);
		Expression _exp = buildExpression(exp);
		return _exp;
	}

	protected Object buildValueObjComplex(Object filtro) {

		String attr = atributoBuild();
		return HelperAtributeJPA.createEntity(rootBuild(filtro).getJavaType(), attr, filtro);

	}

	protected From rootBuild(Object filtro) {
		if (filtro == null || !(filtro instanceof String))
			return getRoot();
		else {
			String[] fields = getRootSubQueryField(filtro.toString());
			if (fields != null) {
				SubQueryRetornoDTO subQueryRetornoDTO = findSubQueryRetornoDTO(fields[0]);
				if (subQueryRetornoDTO != null)
					return subQueryRetornoDTO.getRoot();
				else
					return getRoot();
			} else
				return getRoot();
		}
	}

	protected Expression buildValue(Expression path, Object filtro) {
		Expression _path = buildExpression(path);
		Object _filtro = null;
		Object _filtroParam = filtro;

		Boolean _isExpression = getIsExpression();
		if (!_isExpression) {
			String _filtroExp = atributoStrBuildNull(filtro.toString());
			if (_filtroExp != null) {
				_isExpression = true;
				_filtroParam = _filtroExp;
			}
		}

		if (_isExpression) {
			From from = rootBuild(filtro);
			_filtro = buildIsExpression(_filtroParam, from);
			return buildSingular(_path, (Expression) _filtro);

		} else {
			_filtro = tranformCaseInsensitive(path, _filtroParam);
			return buildSingular(_path, buildValueObjComplex(_filtro));
		}

	}

	protected Expression buildPathArrayOrExpression(Expression path, Object[] filtro) {
		Expression _path = buildExpression(path);
		Object[] _filtro = null;
		if (getIsExpression()) {
			_filtro = buildPathExpression(path, filtro);
		} else {
			_filtro = buildPathArray(path, filtro);
		}
		return buildSingular(_path, _filtro);
	}

	protected Expression[] buildPathExpression(Expression path, Object[] filtro) {
		Expression[] _filtro = (Expression[]) Arrays.stream(filtro).map(t -> buildIsExpression(t, rootBuild(t)))
				.toArray(size -> new Expression[size]);
		return _filtro;
	}

	protected Object[] buildPathArray(Expression path, Object[] filtro) {
		Object[] _filtro = Arrays.stream(filtro).map(t -> tranformCaseInsensitive(path, t)).toArray();
		return _filtro;
	}

	public Expression buildFiltro(FiltroParametro... filtros) {

		List<FiltroParametro> args = Helper.convertArgs(filtros);
		List<Expression> exps = new ArrayList<>();
		for (FiltroParametro filtro : args) {
			Expression expJunction = filtro.build(this);
			if (expJunction != null)
				exps.add(expJunction);

		}

		Expression result = null;
		if (exps.size() > 0) {
			Predicate[] array = (Predicate[]) Array.newInstance(exps.get(0).getClass(), 1);
			result = builder.and(exps.toArray(array));
		}

		return result;

	}

	public abstract Expression buildSingular(Expression path, Object filtro);

}
