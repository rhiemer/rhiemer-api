package br.com.rhiemer.api.jpa.listener;

import br.com.rhiemer.api.jpa.entity.Entity;

public class ListenerDto {
	
	/**
	 * 
	 */
	private Entity entidade;
	
	public ListenerDto(Entity entidade) {
		super();
		this.entidade = entidade;
	}


	public Entity getEntidade() {
		return entidade;
	}

}
