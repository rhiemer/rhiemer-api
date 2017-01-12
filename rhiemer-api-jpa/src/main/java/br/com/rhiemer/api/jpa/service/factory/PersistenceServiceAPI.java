package br.com.rhiemer.api.jpa.service.factory;

import java.io.Serializable;

import br.com.rhiemer.api.jpa.annotations.InjetarDaoEntidade;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.service.PersistenceServiceCrud;

@InjetarDaoEntidade
public class PersistenceServiceAPI<T extends Entity,K extends Serializable> extends PersistenceServiceCrud<T, K> {

	
	
	
	


}
