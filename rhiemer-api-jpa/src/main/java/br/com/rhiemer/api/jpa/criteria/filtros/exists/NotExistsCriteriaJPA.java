package br.com.rhiemer.api.jpa.criteria.filtros.exists;

public class NotExistsCriteriaJPA extends ExistsCriteriaJPA {

	public NotExistsCriteriaJPA() {
		super();
		setNot(true);
	}

}
