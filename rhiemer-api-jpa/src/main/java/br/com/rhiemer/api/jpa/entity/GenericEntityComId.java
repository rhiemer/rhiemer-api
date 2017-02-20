package br.com.rhiemer.api.jpa.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericEntityComId<Chave> extends GenericEntity implements EntityComId<Chave> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6141238085821720956L;

	public GenericEntityComId() {
		super();
	}

	public GenericEntityComId(Chave chave) {
		super();
		setId(chave);
	}

	@Override
	protected void posCopia(Object copia) {
		super.posCopia(copia);
		((GenericEntityComId) copia).setId(null);
	}

	@Override
	public Object clone() {
		GenericEntityComId clone = (GenericEntityComId) super.clone();
		((GenericEntityComId) clone).setId(null);
		return clone;
	}

}
