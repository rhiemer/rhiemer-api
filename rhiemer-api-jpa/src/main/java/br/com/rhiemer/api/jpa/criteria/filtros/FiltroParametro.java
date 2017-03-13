package br.com.rhiemer.api.jpa.criteria.filtros;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Attribute;

import br.com.rhiemer.api.util.helper.Helper;

public class FiltroParametro {

	private Class<? extends FiltroCriteriaJPA> filtro;
	private Object value;
	private String atributo;
	private Attribute[] attributes;

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public Attribute[] getAttributes() {
		return attributes;
	}

	public void setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
	}

	public FiltroParametro() {
		super();
	}

	public FiltroParametro(Class<? extends FiltroCriteriaJPA> filtro, Object value) {
		super();
		this.setFiltro(filtro);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Class<? extends FiltroCriteriaJPA> getFiltro() {
		return filtro;
	}

	public void setFiltro(Class<? extends FiltroCriteriaJPA> filtro) {
		this.filtro = filtro;
	}

	public FiltroCriteriaJPA clone(FiltroCriteriaJPA objTarget) {
		FiltroCriteriaJPA filtroNew = Helper.newInstance(filtro);
		Helper.copyObject(objTarget, filtroNew, false);
		if (this.getAtributo() != null)
			filtroNew.setAtributo(this.getAtributo());
		if (this.getAttributes() != null)
			filtroNew.setAttributes(this.getAttributes());
		return filtroNew;
	}
	
	public Expression build(FiltroCriteriaJPA objTarget) {
		FiltroCriteriaJPA filtroNew = clone(objTarget);		
		return filtroNew.build(value);
	}

}
