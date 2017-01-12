package br.com.rhiemer.api.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.rhiemer.api.jpa.builder.BuildJPA;
import br.com.rhiemer.api.util.dao.Dao;

public interface DaoJPA extends Dao {

	EntityManager getEntityManager();
	
	void setEntityManager(EntityManager em);

	void flush();

	<T> List<T> excutarQueryList(BuildJPA query);

	<T> T excutarQueryUniqueResult(BuildJPA query);

	int excutarUpdateQuery(BuildJPA query);

}