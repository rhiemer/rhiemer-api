package br.com.rhiemer.api.jpa.criteria.juncao;

import java.util.ArrayList;
import java.util.List;

import br.com.rhiemer.api.jpa.criteria.builder.ParametrizarCriteriaJPAParametro;

public class MetodosJuncaoJPA  {

	private List<ParametrizarCriteriaJPAParametro> filtros = new ArrayList<>();
	private Boolean not;
	final private IBuilderMetodosJPA anterior;
	
	
	public MetodosJuncaoJPA() {
		super();
		this.anterior = null;		
	}
	

	public MetodosJuncaoJPA(IBuilderMetodosJPA anterior) {
		super();
		this.anterior = anterior;
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

	public IBuilderMetodosJPA getAnterior() {
		return anterior;
	}


}
