package br.com.rhiemer.api.jpa.criteria.juncao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.builder.ParametroCriteriaJPADto;
import br.com.rhiemer.api.jpa.criteria.filtros.between.BetweenAtributoCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.between.BetweenValueCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.equals.EqualsCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.equals.NotEqualsCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.in.InCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.in.NotInCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.isnull.IsNotNullCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.isnull.IsNullCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.like.ILikeCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.like.LikeCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.maiormenor.MaiorQueCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.maiormenor.MaiorQueOuIgualCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.maiormenor.MenorQueCriteriaJPA;
import br.com.rhiemer.api.jpa.criteria.filtros.maiormenor.MenorQueOuIgualCriteriaJPA;
import br.com.rhiemer.api.util.helper.Helper;

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

	default IBuilderFiltrosJPA<T> notIn(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(NotInCriteriaJPA.class).setAtributo(atributo)
				.setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> notIn(String atributo, ParametroCriteriaJPADto parametos, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(NotInCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> notInAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(NotInCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> notInAtribute(Object value, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(NotInCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> notInAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(NotInCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> notInAtribute(Object[] values, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(NotInCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> notIn(Map<String, Object> map) {
		map.forEach((x, y) -> notIn(x, y));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQue(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MaiorQueCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQue(String atributo, ParametroCriteriaJPADto parametos, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MaiorQueCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MaiorQueCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueAtribute(Object value, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MaiorQueCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MaiorQueCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueAtribute(Object[] values, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MaiorQueCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQue(Map<String, Object> map) {
		map.forEach((x, y) -> maiorQue(x, y));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueOuIgual(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MaiorQueOuIgualCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueOuIgual(String atributo, ParametroCriteriaJPADto parametos,
			Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MaiorQueOuIgualCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueOuIgualAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MaiorQueOuIgualCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueOuIgualAtribute(Object value, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MaiorQueOuIgualCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueOuIgualAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MaiorQueOuIgualCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueOuIgualAtribute(Object[] values, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MaiorQueOuIgualCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> maiorQueOuIgual(Map<String, Object> map) {
		map.forEach((x, y) -> maiorQueOuIgual(x, y));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQue(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MenorQueCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQue(String atributo, ParametroCriteriaJPADto parametos, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MenorQueCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MenorQueCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueAtribute(Object value, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MenorQueCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MenorQueCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueAtribute(Object[] values, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MenorQueCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQue(Map<String, Object> map) {
		map.forEach((x, y) -> menorQue(x, y));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueOuIgual(String atributo, Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MenorQueOuIgualCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueOuIgual(String atributo, ParametroCriteriaJPADto parametos,
			Object... values) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MenorQueOuIgualCriteriaJPA.class)
				.setAtributo(atributo).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueOuIgualAtribute(Object value, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MenorQueOuIgualCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueOuIgualAtribute(Object value, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MenorQueOuIgualCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { value }));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueOuIgualAtribute(Object[] values, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(MenorQueOuIgualCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueOuIgualAtribute(Object[] values, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(MenorQueOuIgualCriteriaJPA.class)
				.setAttributes(atributes).setValues(values));
		return this;
	}

	default IBuilderFiltrosJPA<T> menorQueOuIgual(Map<String, Object> map) {
		map.forEach((x, y) -> menorQueOuIgual(x, y));
		return this;
	}

	default IBuilderFiltrosJPA<T> isNull(String... atributo) {
		List<String> _atributos = Helper.convertArgs(atributo);
		_atributos.forEach(x -> getFiltros().add(new ParametrizarCriteriaJPAParametro()
				.setClasse(IsNullCriteriaJPA.class).setAtributo(x).setValues(new Object[] { 0 })));
		return this;
	}

	default IBuilderFiltrosJPA<T> isNull(ParametroCriteriaJPADto parametos, String... atributo) {
		List<String> _atributos = Helper.convertArgs(atributo);
		_atributos.forEach(x -> getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos)
				.setClasse(IsNullCriteriaJPA.class).setAtributo(x).setValues(new Object[] { 0 })));
		return this;
	}

	default IBuilderFiltrosJPA<T> isNullAtribute(Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(IsNullCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { 0 }));
		return this;
	}

	default IBuilderFiltrosJPA<T> isNullAtribute(ParametroCriteriaJPADto parametos, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(IsNullCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { 0 }));
		return this;
	}

	default IBuilderFiltrosJPA<T> isNotNull(String... atributo) {
		List<String> _atributos = Helper.convertArgs(atributo);
		_atributos.forEach(x -> getFiltros().add(new ParametrizarCriteriaJPAParametro()
				.setClasse(IsNotNullCriteriaJPA.class).setAtributo(x).setValues(new Object[] { 0 })));
		return this;
	}

	default IBuilderFiltrosJPA<T> isNotNull(ParametroCriteriaJPADto parametos, String... atributo) {
		List<String> _atributos = Helper.convertArgs(atributo);
		_atributos.forEach(x -> getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos)
				.setClasse(IsNotNullCriteriaJPA.class).setAtributo(x).setValues(new Object[] { 0 })));
		return this;
	}

	default IBuilderFiltrosJPA<T> isNotNullAtribute(Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(IsNotNullCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { 0 }));
		return this;
	}

	default IBuilderFiltrosJPA<T> isNotNullAtribute(ParametroCriteriaJPADto parametos, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(IsNotNullCriteriaJPA.class)
				.setAttributes(atributes).setValues(new Object[] { 0 }));
		return this;
	}

	default IBuilderFiltrosJPA<T> between(String atributo, Object filtro1, Object filtro2) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(BetweenValueCriteriaJPA.class)
				.setAtributo(atributo).setFiltro1(filtro1).setFiltro2(filtro2));
		return this;
	}

	default IBuilderFiltrosJPA<T> between(String atributo, Object filtro1, Object filtro2,
			ParametroCriteriaJPADto parametos) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(BetweenValueCriteriaJPA.class)
				.setAtributo(atributo).setFiltro1(filtro1).setFiltro2(filtro2));
		return this;
	}

	default IBuilderFiltrosJPA<T> betweenAtribute(Object filtro1, Object filtro2, Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(BetweenValueCriteriaJPA.class)
				.setAttributes(atributes).setFiltro1(filtro1).setFiltro2(filtro2));
		return this;
	}

	default IBuilderFiltrosJPA<T> betweenAtribute(Object filtro1, Object filtro2, ParametroCriteriaJPADto parametos,
			Attribute... atributes) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(BetweenValueCriteriaJPA.class)
				.setAttributes(atributes).setFiltro1(filtro1).setFiltro2(filtro2));
		return this;
	}

	default IBuilderFiltrosJPA<T> betweenCampo(String path1, String path2, Object filtro) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(BetweenAtributoCriteriaJPA.class)
				.setPath1(path1).setPath2(path2).setValues(new Object[] { filtro }));
		return this;
	}

	default IBuilderFiltrosJPA<T> betweenCampo(String path1, String path2, Object filtro,
			ParametroCriteriaJPADto parametos) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(BetweenAtributoCriteriaJPA.class)
				.setPath1(path1).setPath2(path2).setValues(new Object[] { filtro }));
		return this;
	}

	default IBuilderFiltrosJPA<T> betweenCampoAtribute(Attribute[] pathAttribute1, Attribute[] pathAttribute2,
			Object filtro) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(BetweenAtributoCriteriaJPA.class)
				.setPathAttribute1(pathAttribute1).setPathAttribute2(pathAttribute2)
				.setValues(new Object[] { filtro }));
		return this;
	}

	default IBuilderFiltrosJPA<T> betweenCampoAtribute(Attribute pathAttribute1, Attribute pathAttribute2,
			Object filtro) {
		return betweenCampoAtribute(new Attribute[] { pathAttribute1 }, new Attribute[] { pathAttribute2 }, filtro);
	}

	default IBuilderFiltrosJPA<T> betweenCampoAtribute(Attribute[] pathAttribute1, Attribute[] pathAttribute2,
			Object filtro, ParametroCriteriaJPADto parametos) {
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(BetweenAtributoCriteriaJPA.class)
				.setPathAttribute1(pathAttribute1).setPathAttribute2(pathAttribute2)
				.setValues(new Object[] { filtro }));
		return this;
	}

	default IBuilderFiltrosJPA<T> betweenCampoAtribute(Attribute pathAttribute1, Attribute pathAttribute2,
			Object filtro, ParametroCriteriaJPADto parametos) {
		return betweenCampoAtribute(new Attribute[] { pathAttribute1 }, new Attribute[] { pathAttribute2 }, filtro,
				parametos);
	}

}
