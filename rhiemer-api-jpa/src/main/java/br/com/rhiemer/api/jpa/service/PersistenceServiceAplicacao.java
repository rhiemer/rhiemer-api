package br.com.rhiemer.api.jpa.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.rhiemer.api.jpa.dao.DaoJPA;
import br.com.rhiemer.api.jpa.dao.factory.DaoFactory;
import br.com.rhiemer.api.jpa.entity.Entity;

public class PersistenceServiceAplicacao<T extends Entity,K extends Serializable> extends PersistenceServiceType<T, K> {

	@Inject
	private DaoFactory daoFacroty;

	@PostConstruct
	protected void postConstructor() {
		setDao((DaoJPA)daoFacroty.buscarDao(getClasseObjeto()));

	}

}
