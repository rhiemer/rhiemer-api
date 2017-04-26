package br.com.rhiemer.api.jpa.criteria.juncao;

import java.util.Map;

import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.builder.ParametroCriteriaJPADto;
import br.com.rhiemer.api.jpa.criteria.filtros.equals.EqualsCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.equals.NotEqualsCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.in.InCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.like.ILikeCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.like.LikeCriteriaJPA;

public interface IBuilderFiltrosJPA<T> extends IBuilderMetodosJPA {

	default T root() {
		if (this instanceof MetodosJuncaoJPA)
			return (T) ((MetodosJuncaoJPA) this).getRoot();
		else
			return (T) this;
	}

	default IBuilderFiltrosJPA<T> anterior() {
		if (this instanceof MetodosJuncaoJPA)
			return ((MetodosJuncaoJPA) this).getAnterior();
		else
			return (IBuilderFiltrosJPA) this;
	}

	default MetodosJuncaoJPAOr<T> or() {
		MetodosJuncaoJPAOr<T> objeto = new MetodosJuncaoJPAOr(this);
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(objeto));
		return objeto;
	}

	default MetodosJuncaoJPAOr<T> orNot() {
		MetodosJuncaoJPAOr<T> objeto = new MetodosJuncaoJPAOr(true, this);
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(objeto));
		return objeto;
	}

	default MetodosJuncaoJPAAnd<T> and() {
		MetodosJuncaoJPAAnd<T> objeto = new MetodosJuncaoJPAAnd(this);
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(objeto));
		return objeto;
	}

	default MetodosJuncaoJPAAnd<T> andNot() {
		MetodosJuncaoJPAAnd<T> objeto = new MetodosJuncaoJPAAnd(true, this);
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(objeto));
		return objeto;
	}

	default IBuilderFiltrosJPA<T> equal(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class).setAtributo(atributo)
				.setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> equal(String atributo, ParametroCriteriaJPADto parametos, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(EqualsCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> equalAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> equalAtribute(Object value, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(EqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> equalAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> equalAtribute(Object[] values, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(EqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> equal(Map<String, Object> map) {
		map.forEach((x, y) -> equal(x, y));
		return this;
	}

	default IBuilderFiltrosJPA<T> notEqual(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(NotEqualsCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> notEqual(String atributo, ParametroCriteriaJPADto parametos, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(NotEqualsCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> notEqualAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(NotEqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> notEqualAtribute(Object value, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(NotEqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> notEqualAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(NotEqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> notEqualAtribute(Object[] values, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(NotEqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> notEqual(Map<String, Object> map) {
		map.forEach((x, y) -> notEqual(x, y));
		return this;
	}

	default IBuilderFiltrosJPA<T> like(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(LikeCriteriaJPA.class).setAtributo(atributo)
				.setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> like(String atributo, ParametroCriteriaJPADto parametos, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(LikeCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> likeAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(LikeCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> likeAtribute(Object value, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(LikeCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> likeAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(LikeCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> likeAtribute(Object[] values, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(LikeCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> like(Map<String, Object> map) {
		map.forEach((x, y) -> like(x, y));
		return this;
	}

	default IBuilderFiltrosJPA<T> iLike(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(ILikeCriteriaJPA.class).setAtributo(atributo)
				.setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> iLike(String atributo, ParametroCriteriaJPADto parametos, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(ILikeCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> iLikeAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(ILikeCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> iLikeAtribute(Object value, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(ILikeCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> iLikeAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(ILikeCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> iLikeAtribute(Object[] values, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(ILikeCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> iLike(Map<String, Object> map) {
		map.forEach((x, y) -> iLike(x, y));
		return this;
	}

	default IBuilderFiltrosJPA<T> in(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(InCriteriaJPA.class).setAtributo(atributo)
				.setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> in(String atributo, ParametroCriteriaJPADto parametos, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(InCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> inAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(InCriteriaJPA.class).setAttributes(atributes)
				.setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> inAtribute(Object value, ParametroCriteriaJPADto parametos, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(InCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> inAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(InCriteriaJPA.class).setAttributes(atributes)
				.setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> inAtribute(Object[] values, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(InCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> in(Map<String, Object> map) {
		map.forEach((x, y) -> in(x, y));
		return this;
	}

}
