package br.com.rhiemer.api.jpa.criteria.filtros;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import br.com.rhiemer.api.jpa.criteria.join.AbstractJoinCriteriaJPA;
import br.com.rhiemer.api.util.helper.Helper;

public abstract class FiltroCriteriaJPA extends AbstractJoinCriteriaJPA implements IFiltroCriteriaJPA {

	private CriteriaBuilder builder;
	private Boolean not = false;
	private Boolean caseSensitve = false;
	private Boolean includeNull = false;

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

	public Expression build(Object... filtros) {
		Path path = builderJoin();
		return buildFiltro(path, filtros);
	}

	public Expression buildFiltro(Expression path, Object... filtros) {
		List<Object> args = Helper.convertArgs(filtros);
		List<Expression> exps = new ArrayList<>();

		args.stream().filter(t -> Helper.isNotBlank(t)).forEach(t -> exps.add(buildValue(path, t)));

		Expression expJunction = null;
		if (exps == null || exps.size() == 0)
			return null;
		else if (exps.size() > 1) {
			Predicate[] array = (Predicate[]) Array.newInstance(exps.get(0).getClass(), 1);
			expJunction = builder.or(exps.toArray(array));
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

	protected Expression buildValue(Expression path, Object filtro) {
		Expression _path = path;
		Object _filtro = filtro;
		if (!getCaseSensitve() && path.getJavaType().isAssignableFrom(String.class)) {
			_path = getBuilder().upper(path);
			_filtro = filtro.toString().toUpperCase();
		}

		if (getIncludeNull()) {
			Expression expInsNull = builder.isNull(path);
			Expression expJunction = builder.or(expInsNull, _path);
			_path = expJunction;

		}

		return buildSingular(_path, _filtro);
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
