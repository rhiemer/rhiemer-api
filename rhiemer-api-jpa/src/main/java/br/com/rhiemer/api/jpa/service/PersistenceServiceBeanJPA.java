package br.com.rhiemer.api.jpa.service;

import java.io.Serializable;

import br.com.rhiemer.api.jpa.dao.DaoJPA;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.util.service.PersistenceServiceBean;

public interface PersistenceServiceBeanJPA<T extends Entity,K extends Serializable> extends PersistenceServiceBean<T,K>
{

	DaoJPA getDao();

	void setDao(DaoJPA dao);

	Class<T> getClasseObjeto();

	Class<K> getClasseChave();

	void setClasseObjeto(Class<T> classeObjeto);

	void setClasseChave(Class<K> classeChave);

}
