package br.com.rhiemer.api.jpa.criteria.juncao;

import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.jpa.criteria.builder.ParametroCriteriaJPADto;
import br.com.rhiemer.api.jpa.criteria.filtros.equals.EqualsCriteriaJPA;

public interface IBuilderFiltrosJPA extends IBuilderMetodosJPA {
	
	default MetodosJuncaoJPAOr or()
	{
		MetodosJuncaoJPAOr objeto = new MetodosJuncaoJPAOr(this);
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(objeto));
		return objeto;
	}
	
	default MetodosJuncaoJPAOr orNot()
	{
		MetodosJuncaoJPAOr objeto =  new MetodosJuncaoJPAOr(this);
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setNot(true).setObjeto(objeto));
		return objeto;
	}
	
	default MetodosJuncaoJPAAnd and()
	{
		MetodosJuncaoJPAAnd objeto = new MetodosJuncaoJPAAnd(this);
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setObjeto(objeto));
		return objeto;
	}
	
	default MetodosJuncaoJPAAnd andNot()
	{
		MetodosJuncaoJPAAnd objeto =  new MetodosJuncaoJPAAnd(this);
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setNot(true).setObjeto(objeto));
		return objeto;
	}
	
	default IBuilderFiltrosJPA equal(String atributo,Object...values)
	{
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class).setAtributo(atributo).setValues(values));
		return this;
	}
	
	default IBuilderFiltrosJPA equal(String atributo,ParametroCriteriaJPADto parametos,Object...values)
	{
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(EqualsCriteriaJPA.class).setAtributo(atributo).setValues(values));
		return this;
	}
	
	default IBuilderFiltrosJPA equal(Object value,Attribute... atributes)
	{
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class).setAttributes(atributes).setValues(new Object[]{value}));
		return this;
	}
	
	default IBuilderFiltrosJPA equal(Object value,ParametroCriteriaJPADto parametos,Attribute... atributes)
	{
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(EqualsCriteriaJPA.class).setAttributes(atributes).setValues(new Object[]{value}));
		return this;
	}
	
	default IBuilderFiltrosJPA equal(Object[] values,Attribute... atributes)
	{
		getFiltros().add(new ParametrizarCriteriaJPAParametro().setClasse(EqualsCriteriaJPA.class).setAttributes(atributes).setValues(values));
		return this;
	}
	
	default IBuilderFiltrosJPA equal(Object[] values,ParametroCriteriaJPADto parametos,Attribute... atributes)
	{
		getFiltros().add(new ParametrizarCriteriaJPAParametro(parametos).setClasse(EqualsCriteriaJPA.class).setAttributes(atributes).setValues(values));
		return this;
	}
	
	
	
	

}
