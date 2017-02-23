package br.com.rhiemer.api.jpa.criteria.create;

import br.com.rhiemer.api.jpa.criteria.interfaces.ICreateCriteriaJPA;
import br.com.rhiemer.api.util.criteria.CreateCriteria;

public class CreateCriteriaJPA extends CreateCriteria implements ICreateCriteriaJPA {

	private RetornoCreateCriteriaJPA retornoCreateCriteriaJPA;

	public CreateCriteriaJPA(RetornoCreateCriteriaJPA retornoCreateCriteriaJPA) {
		super();
		setRetornoCreateCriteriaJPA(retornoCreateCriteriaJPA);
	}

	@Override
	protected void create() {
		getRetornoCreateCriteriaJPA().createAtributos();
	}

	public RetornoCreateCriteriaJPA getRetornoCreateCriteriaJPA() {
		return retornoCreateCriteriaJPA;
	}

	protected void setRetornoCreateCriteriaJPA(RetornoCreateCriteriaJPA retornoCreateCriteriaJPA) {
		this.retornoCreateCriteriaJPA = retornoCreateCriteriaJPA;
	}

}
