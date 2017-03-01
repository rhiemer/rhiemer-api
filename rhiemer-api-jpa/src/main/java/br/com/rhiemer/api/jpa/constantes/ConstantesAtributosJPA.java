package br.com.rhiemer.api.jpa.constantes;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public interface ConstantesAtributosJPA {

	static Class[] ANNOTATIONS_JOIN = new Class[] { OneToMany.class, ManyToOne.class, OneToOne.class,
			ManyToMany.class };
	static Class[] ANNOTATIONS_LIST = new Class[] { OneToMany.class, ManyToMany.class };
	static Class[] ANNOTATIONS_REFERENCE = new Class[] { OneToMany.class, ManyToMany.class,OneToOne.class };

}
