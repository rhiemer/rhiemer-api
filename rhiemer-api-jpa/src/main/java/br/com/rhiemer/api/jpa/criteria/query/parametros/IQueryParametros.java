package br.com.rhiemer.api.jpa.criteria.query.parametros;

import javax.persistence.criteria.CriteriaQuery;

import br.com.rhiemer.api.jpa.criteria.interfaces.ICriteriaJPA;

public interface IQueryParametros extends ICriteriaJPA {

	public CriteriaQuery getQuery();
	public Boolean getAtributo();
	public void build();

}
