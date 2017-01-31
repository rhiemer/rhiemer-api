package br.com.rhiemer.api.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.rhiemer.api.jpa.converter.SimNaoConverter;
import br.com.rhiemer.api.jpa.listener.ListenerEntity;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

@MappedSuperclass
@EntityListeners({ ListenerEntity.class })
public abstract class GenericEntity extends PojoKeyAbstract implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2294751858651011935L;

	@Version
	@XmlTransient
	@JsonIgnore
	@Column(name = "VERSAO", nullable = true)
	@Audited
	private Long version = 1L;

	@XmlTransient
	@JsonIgnore
	@Column(name = "Inclusao", nullable = true, updatable = false)
	@Audited
	private Date inclusao;

	@XmlTransient
	@JsonIgnore
	@Column(name = "ultimaAlteracao", nullable = true)
	@Audited
	private Date ultimaAlteracao;

	@JsonIgnore
	@XmlTransient
	@Audited
	@Column(name = "exclusao")
	private Date exclusao;

	@Column(nullable = false)
	@Convert(converter = SimNaoConverter.class)
	@JsonIgnore
	@XmlTransient
	@Audited
	private Boolean ativo = true;

	@XmlTransient
	@JsonIgnore
	public Date getInclusao() {
		return inclusao;
	}

	@XmlTransient
	@JsonIgnore
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	@XmlTransient
	@JsonIgnore
	public Long getVersion() {
		return version;
	}

	@JsonIgnore
	@XmlTransient
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@JsonIgnore
	@XmlTransient
	public Date getExclusao() {
		return exclusao;
	}

	@PrePersist
	protected void prePersist() {
		Date data = new Date();
		this.ultimaAlteracao = data;
		this.inclusao = data;
		if (ativo == null)
			ativo = true;
	}

	@PreUpdate
	protected void preUpdate() {
		this.ultimaAlteracao = new Date();
		if (ativo == null)
			ativo = true;
	}

	@PreRemove
	protected void preRemove() {
		exclusao = new Date();
	}

	@Override
	protected void posCopia(Object copia) {
		super.posCopia(copia);
		((GenericEntity) copia).ultimaAlteracao = null;
		((GenericEntity) copia).version = null;
	}

	@Override
	public Object clone() {
		GenericEntity clone = (GenericEntity) super.clone();
		clone.ultimaAlteracao = null;
		clone.version = null;
		return clone;
	}

}
