package br.com.rhiemer.api.jpa.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GenericEntity.class)
public abstract class GenericEntity_ {

	public static volatile SingularAttribute<GenericEntity, Date> ultimaAlteracao;
	public static volatile SingularAttribute<GenericEntity, Boolean> ativo;
	public static volatile SingularAttribute<GenericEntity, Date> exclusao;
	public static volatile SingularAttribute<GenericEntity, Date> inclusao;
	public static volatile SingularAttribute<GenericEntity, Long> version;

}

