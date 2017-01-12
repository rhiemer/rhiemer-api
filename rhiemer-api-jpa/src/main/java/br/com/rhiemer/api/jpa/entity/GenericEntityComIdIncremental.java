package br.com.rhiemer.api.jpa.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericEntityComIdIncremental extends GenericEntity implements EntityComId
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6141238085821720956L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	protected void posCopia(Object copia) {
		super.posCopia(copia);
		((GenericEntityComIdIncremental) copia).id = null;
	}

	@Override
	public Object clone() {
		GenericEntityComIdIncremental clone = (GenericEntityComIdIncremental) super.clone();
		((GenericEntityComIdIncremental) clone).id = null;
		return clone;
	}

}
