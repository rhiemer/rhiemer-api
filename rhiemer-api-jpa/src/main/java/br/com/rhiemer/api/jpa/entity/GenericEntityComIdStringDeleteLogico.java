package br.com.rhiemer.api.jpa.entity;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@MappedSuperclass
@SQLDelete(sql = "UPDATE #{#entityName} SET ativo = 'N' WHERE id = ? and version = ?")
@Where(clause = "ativo = 'S' ")
public abstract class GenericEntityComIdStringDeleteLogico extends GenericEntityComIdString
		implements EntityDeleteLogico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6651122349902716822L;

}
