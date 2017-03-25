package br.com.rhiemer.api.jpa.criteria.juncao;

public class MetodosJuncaoJPAOr<T> extends MetodosJuncaoJPA<T> implements IBuilderMetodosJuncaoJPAOr,IBuilderFiltrosJPA<T> {

	public MetodosJuncaoJPAOr() {
		super();
	}

	public MetodosJuncaoJPAOr(IBuilderFiltrosJPA<T> anterior) {
		super(anterior);
	}


}
