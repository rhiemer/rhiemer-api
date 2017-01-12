package br.com.rhiemer.api.jpa.dao;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.rhiemer.api.jpa.mapper.EntityManagerMapClass;

public class DaoAplicacao extends DaoJPAImpl {

	@Inject
	private EntityManagerMapClass entityManagerMapClass;

	@PostConstruct
	protected void postConstrutor() {

		setEntityManager(entityManagerMapClass.getEntityManagerAplicacao());

	}

}
