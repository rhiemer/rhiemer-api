package br.com.rhiemer.api.jpa.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SessionImplementor;

import net.sf.beanlib.hibernate3.Hibernate3DtoCopier;

public class HelperLazy {

	private HelperLazy() {

	}

	public static <T> T unproxy(T t) {
		SessionImplementor session = HelperHibernateProxy.getSessionProperty(t);
		return unproxy(t, session);
	}

	public static <T> T unproxy(T t, EntityManager em) {
		return unproxy(t, em.unwrap(SessionImplementor.class));
	}

	public static <T> T unproxy(T t, SessionImplementor session) {
		PersistenceContext persistenceContext = session.getPersistenceContext();
		T result = (T) persistenceContext.unproxyAndReassociate(t);
		return result;
	}
	
	public static <T> void unproxyCollection(Collection<T> c) {
		List<T> lista = new ArrayList<>();
		lista.addAll(c);
		c.clear();
		lista.stream().forEach(x -> c.add(unproxy(x)));
	}

	public static <T> void unproxyCollection(Collection<T> c, EntityManager em) {
		List<T> lista = new ArrayList<>();
		lista.addAll(c);
		c.clear();
		lista.stream().forEach(x -> c.add(unproxy(x, em)));
	}
	
	public static <T> void unproxyCollection(Collection<T> c, SessionImplementor session) {
		List<T> lista = new ArrayList<>();
		lista.addAll(c);
		c.clear();
		lista.stream().forEach(x -> c.add(unproxy(x, session)));
	}

	public static <T> T copyDTO(T t) {
		Hibernate3DtoCopier copiador = new Hibernate3DtoCopier();
		T copia = copiador.hibernate2dto(t);
		return copia;
	}

	public static <T> Collection<T> copyCollection(Collection<T> c) {
		Hibernate3DtoCopier copiador = new Hibernate3DtoCopier();
		Collection<?> copia = copiador.hibernate2dto(c);
		return (Collection<T>) copia;
	}

	public static <T> T copyDTOFully(T t) {
		Hibernate3DtoCopier copiador = new Hibernate3DtoCopier();
		T copia = copiador.hibernate2dtoFully(t);
		return copia;
	}

	public static <T> Collection<T> copyCollectionFully(Collection<T> c) {
		Hibernate3DtoCopier copiador = new Hibernate3DtoCopier();
		Collection<?> copia = copiador.hibernate2dtoFully(c);
		return (Collection<T>) copia;
	}

}
