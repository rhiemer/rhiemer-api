package br.com.rhiemer.api.jpa.builder;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public interface BuildJPA {

	Query buildQuery(EntityManager em);

	Class<?> getResultClass();

	void setResultClass(Class<?> resultClass);

}
