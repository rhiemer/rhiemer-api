package br.com.rhiemer.api.jpa.builder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.rhiemer.api.util.dao.parametros.execucao.IExecucao;

public interface BuildJPA {

	Query buildQuery(EntityManager em);

	Class<?> getResultClass();

	void setResultClass(Class<?> resultClass);
	
	List<IExecucao> getParametrosExecucao();
	
	

}
