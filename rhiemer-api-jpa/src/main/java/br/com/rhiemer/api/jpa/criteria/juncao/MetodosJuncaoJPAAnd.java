package br.com.rhiemer.api.jpa.criteria.juncao;

import java.util.Map;

import br.com.rhiemer.api.jpa.criteria.subquery.SubQueryJPA;

public class MetodosJuncaoJPAAnd<T> extends MetodosJuncaoJPA<T> implements IBuilderMetodosJuncaoJPAAnd,IBuilderFiltrosJPA<T> {

	public MetodosJuncaoJPAAnd() {
		super();
	}

	public MetodosJuncaoJPAAnd(IBuilderFiltrosJPA<T> anterior) {
		super(anterior);
	}
	
	public MetodosJuncaoJPAAnd(boolean not) {
		super(not);
	}
	
	public MetodosJuncaoJPAAnd(boolean not,IBuilderFiltrosJPA<T> anterior) {
		super(not,anterior);
	}

	


}
