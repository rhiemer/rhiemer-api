package br.com.rhiemer.api.jpa.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GenericEntityComIdIncremental extends GenericEntityComId<Integer>
		implements EntityComIdIncremental {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6141238085821720956L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	public GenericEntityComIdIncremental() {
		super();
	}

	public GenericEntityComIdIncremental(Integer chave) {
		super(chave);
	}

	public GenericEntityComIdIncremental(int chave) {
		super(chave);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
