package br.com.rhiemer.api.jpa.criteria.juncao;

public class MetodosJuncaoJPAOr<T> extends MetodosJuncaoJPA<T>
		implements IBuilderMetodosJuncaoJPAOr, IBuilderFiltrosJPA<T> {

	public MetodosJuncaoJPAOr() {
		super();
	}

	public MetodosJuncaoJPAOr(IBuilderFiltrosJPA<T> anterior) {
		super(anterior);
	}

	public MetodosJuncaoJPAOr(boolean not) {
		super(not);
	}
	
	public MetodosJuncaoJPAOr(boolean not,IBuilderFiltrosJPA<T> anterior) {
		super(not,anterior);
	}


}
