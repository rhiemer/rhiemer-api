package br.com.rhiemer.api.jpa.criteria.filtros.in;

public class NotInCriteriaJPA extends InCriteriaJPA {

	public NotInCriteriaJPA() {
		this.setNot(true);
	}

}
