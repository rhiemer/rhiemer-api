package br.com.rhiemer.api.jpa.criteria.juncao;

public class MetodosJuncaoJPAAnd<T> extends MetodosJuncaoJPA<T> implements IBuilderMetodosJuncaoJPAAnd,IBuilderFiltrosJPA<T> {

	public MetodosJuncaoJPAAnd() {
		super();
	}

	public MetodosJuncaoJPAAnd(IBuilderFiltrosJPA<T> anterior) {
		super(anterior);
	}


}
