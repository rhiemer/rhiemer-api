package br.com.rhiemer.api.jpa.criteria.juncao;

import java.util.Map;

import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.builder.ParametroCriteriaJPADto;
import br.com.rhiemer.api.jpa.criteria.filtros.equals.EqualsCriteriaJPA;

public interface IBuilderFiltrosJPA<T> extends IBuilderMetodosJPA {

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
		MetodosJuncaoJPAAnd<T> objeto = new MetodosJuncaoJPAAnd(true,this);
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(objeto));
		return objeto;
	}

	default T equal(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class).setAtributo(atributo)
				.setValues(values));
		return (T) this;
	}

	default T equal(String atributo, ParametroCriteriaJPADto parametos, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(EqualsCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return (T) this;
	}

	default T equalAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return (T) this;
	}

	default T equalAtribute(Object value, ParametroCriteriaJPADto parametos, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(EqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return (T) this;
	}

	default T equalAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return (T) this;
	}

	default T equalAtribute(Object[] values, ParametroCriteriaJPADto parametos, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(EqualsCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return (T) this;
	}

	default T equal(Map<String, Object> map) {
		map.forEach((x, y) -> equal(x, y));
		return (T) this;
	}

}
