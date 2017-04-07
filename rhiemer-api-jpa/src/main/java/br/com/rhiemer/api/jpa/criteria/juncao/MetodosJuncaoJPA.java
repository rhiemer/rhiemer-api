package br.com.rhiemer.api.jpa.criteria.juncao;

import java.util.ArrayList;
import java.util.List;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;
import br.com.rhiemer.api.util.helper.Helper;

public abstract class MetodosJuncaoJPA<T> {

	private List<ParametrizarCriteriaJPAParametro> filtros = new ArrayList<>();
	private Boolean not=false;
	final private IBuilderFiltrosJPA<T> anterior;

	public MetodosJuncaoJPA() {
		super();
		this.anterior = null;
	}

	public MetodosJuncaoJPA(IBuilderFiltrosJPA<T> anterior) {
		super();
		this.anterior = anterior;
		this.not=false;
	}
	
	public MetodosJuncaoJPA(boolean not) {
		super();
		this.anterior = null;
		this.not=not;
	}
	
	public MetodosJuncaoJPA(boolean not,IBuilderFiltrosJPA<T> anterior) {
		super();
		this.anterior = anterior;
		this.not=not;
	}

	public List<ParametrizarCriteriaJPAParametro> getFiltros() {
		return filtros;
	}

	public Boolean getNot() {
		return not;
	}

	public void setNot(Boolean not) {
		this.not = not;
	}

	public IBuilderFiltrosJPA<T> getAnterior() {
		return anterior;
	}

	public T getRoot() {
		Object _root = this;
		Object _anterior = this;
		do {
			_root = _anterior;
			_anterior = Helper.getValueMethodOrField(_root, "anterior");
		} while (_anterior != null);
		return (T) _root;
	}
	
	

}
