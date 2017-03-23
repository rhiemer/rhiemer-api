package br.com.rhiemer.api.jpa.helper;

import java.util.Collection;

import net.sf.beanlib.hibernate3.Hibernate3DtoCopier;

public class HelperLazy {
	
	private HelperLazy()
	{
		
	}
	
	public static <T> T copyDTO(T t)
	{
		Hibernate3DtoCopier copiador = new Hibernate3DtoCopier();
		T copia = copiador.hibernate2dto(t);
		return copia;
	}
	
	public static <T> Collection<T> copyCollection(Collection<T> c)
	{
		Hibernate3DtoCopier copiador = new Hibernate3DtoCopier();
		Collection<?> copia = copiador.hibernate2dto(c);
		return (Collection<T>)copia;
	}
	
	public static <T> T copyDTOFully(T t)
	{
		Hibernate3DtoCopier copiador = new Hibernate3DtoCopier();
		T copia = copiador.hibernate2dtoFully(t);
		return copia;
	}
	
	public static <T> Collection<T> copyCollectionFully(Collection<T> c)
	{
		Hibernate3DtoCopier copiador = new Hibernate3DtoCopier();
		Collection<?> copia = copiador.hibernate2dtoFully(c);
		return (Collection<T>)copia;
	}

}
