package br.com.rhiemer.api.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.rhiemer.api.jpa.converter.SimNaoConverter;

@MappedSuperclass
@SQLDelete(sql = "UPDATE #{#entityName} SET ativo = 'N' WHERE id = ? and version = ?")
@Where(clause = "ativo = 'S' ")
public abstract class GenericEntityComIdIncrementalDeleteLogico extends
		GenericEntityComIdIncremental implements EntityDeleteLogico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6141238085821720956L;

	@Column(nullable = false)
	@Convert(converter=SimNaoConverter.class)
	@JsonIgnore
	@XmlTransient
	@Audited
	private Boolean ativo=true;
	
	@JsonIgnore
	@XmlTransient
	@Audited
	@Column(name = "exclusao")
	private Date exclusao;

	@JsonIgnore
	@XmlTransient
	public Boolean getAtivo() {
		return ativo;
	}	
	
	@JsonIgnore
	@XmlTransient
	public Date getExclusao() {
		return exclusao;
	}
	
	@Override
	protected void prePersist() {
		super.prePersist();
		if (ativo == null)
		 ativo = true;
	}
	
	@Override
	protected void preUpdate() {
		super.preUpdate();
		if (ativo == null)
		 ativo = true;	
	}

	@Override
	protected void preRemove() {
		super.preRemove();
		exclusao = new Date();
	}

	

}
