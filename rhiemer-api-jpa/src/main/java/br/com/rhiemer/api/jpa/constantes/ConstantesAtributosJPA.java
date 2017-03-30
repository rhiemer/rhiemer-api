package br.com.rhiemer.api.jpa.constantes;

import java.lang.annotation.Annotation;

import javax.persistence.EmbeddedId;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public interface ConstantesAtributosJPA {

	static Class<? extends Annotation>[] ANNOTATIONS_JOIN = new Class[] { OneToMany.class, ManyToOne.class, OneToOne.class,
			ManyToMany.class };
	static Class<? extends Annotation>[] ANNOTATIONS_LIST = new Class[] { OneToMany.class, ManyToMany.class };
	static Class<? extends Annotation>[] ANNOTATIONS_REFERENCE = new Class[] { OneToMany.class, ManyToMany.class,OneToOne.class };
	static Class<? extends Annotation>[] ANNOTATIONS_ATRIBUTO = new Class[] {ManyToOne.class, OneToOne.class};
	static Class<? extends Annotation>[] ANNOTATIONS_CREATE = new Class[] { ManyToOne.class, OneToOne.class,EmbeddedId.class };
	
}
