package br.com.rhiemer.api.jpa.criteria.filtros;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.atributos.AbstractAtributoCriteriaJPA;
import br.com.rhiemer.api.jpa.helper.HelperAtributeJPA;
import br.com.rhiemer.api.jpa.helper.HelperRootCriteria;
import br.com.rhiemer.api.util.helper.Helper;

public abstract class FiltroCriteriaJPA extends AbstractAtributoCriteriaJPA implements IFiltroCriteriaJPA {

	private CriteriaBuilder builder;
	private Boolean not = false;
	private Boolean caseSensitve = true;
	private Boolean includeNull = false;
	private Boolean isExpression = false;

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

	public Expression build(Object... filtros) {
		Path path = builderAtributoCriteria();
		return buildFiltro(path, filtros);
	}

	protected Boolean getFiltroIsArray() {
		return false;
	}

	public String atributoBuild() {
		Attribute[] _attributes = getAttributes();
		if (_attributes != null && _attributes.length > 0)
			return HelperAtributeJPA.attributeToString(_attributes);
		else
			return getAtributo();
	}

	public Expression buildFiltro(Expression path, Object... filtros) {
		List<Object> args = Helper.convertArgs(filtros);
		List<Expression> exps = new ArrayList<>();

		if (!getFiltroIsArray()) {
			args.stream().filter(t -> Helper.isNotBlank(t)).forEach(t -> exps.add(buildValue(path, t)));
		} else {
			Object[] values = args.stream().filter(t -> Helper.isNotBlank(t)).toArray();
			if (values != null && values.length > 0)
				exps.add(buildValue(path, values));
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

	protected Expression buildIsExpression(Object filtro) {
		Expression exp = HelperRootCriteria.getExpressionObj(getRoot(), getFecth(), getJoinType(), filtro);
		Expression _exp = buildExpression(exp);
		return _exp;
	}

	protected Object buildValueObjComplex(Object filtro) {

		String attr = atributoBuild();
		return HelperAtributeJPA.createEntity(getRoot().getJavaType(), attr, filtro);

	}

	protected Expression buildValue(Expression path, Object filtro) {
		Expression _path = buildExpression(path);
		Object _filtro = null;
		if (getIsExpression())
			_filtro = buildIsExpression(filtro);
		else
			_filtro = tranformCaseInsensitive(path, filtro);

		return buildSingular(_path, buildValueObjComplex(_filtro));
	}

	protected Expression buildPathArray(Expression path, Object[] filtro) {
		Expression _path = buildExpression(path);
		Object[] _filtro = Arrays.stream(filtro).map(t -> tranformCaseInsensitive(path, t)).toArray();
		return buildArray(_path, _filtro);
	}

	protected Expression buildArray(Expression path, Object[] filtro) {
		return null;
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
