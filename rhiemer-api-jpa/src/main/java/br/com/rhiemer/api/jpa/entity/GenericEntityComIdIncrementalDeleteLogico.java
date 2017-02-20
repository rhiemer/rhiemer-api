package br.com.rhiemer.api.jpa.entity;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@MappedSuperclass
@SQLDelete(sql = "UPDATE #{#entityName} SET ativo = 'N' WHERE id = ? and version = ?")
@Where(clause = "ativo = 'S' ")
public abstract class GenericEntityComIdIncrementalDeleteLogico extends
		GenericEntityComIdIncremental implements EntityDeleteLogico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6141238085821720956L;

	public GenericEntityComIdIncrementalDeleteLogico() {
		super();
	}

	public GenericEntityComIdIncrementalDeleteLogico(int chave) {
		super(chave);
	}

	public GenericEntityComIdIncrementalDeleteLogico(Integer chave) {
		super(chave);
	}
	
	
	
	

	

}
