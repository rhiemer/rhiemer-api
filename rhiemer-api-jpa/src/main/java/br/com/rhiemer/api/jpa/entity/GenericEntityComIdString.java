package br.com.rhiemer.api.jpa.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericEntityComIdString extends GenericEntityComId<String> implements EntityComIdString
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6141238085821720956L;

	@Id
	private String id;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

}
