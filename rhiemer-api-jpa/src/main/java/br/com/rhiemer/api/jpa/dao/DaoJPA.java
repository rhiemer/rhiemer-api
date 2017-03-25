package br.com.rhiemer.api.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.rhiemer.api.jpa.builder.BuildJPA;
import br.com.rhiemer.api.util.dao.Dao;
import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;

public interface DaoJPA extends Dao {

	EntityManager getEntityManager();
	
	void setEntityManager(EntityManager em);

	void flush();

	<T> List<T> excutarQueryList(BuildJPA query, IExecucao... parametrosExecucao);

	<T> T excutarQueryUniqueResult(BuildJPA query, IExecucao... parametrosExecucao);

	int excutarUpdateQuery(BuildJPA query);

}