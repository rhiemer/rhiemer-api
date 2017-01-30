package br.com.rhiemer.api.jpa.entity;

public interface EntityComId<Chave> extends Entity {
	
	Chave getId();

	void setId(Chave id);

}
