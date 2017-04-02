package br.com.rhiemer.api.jpa.helper;

import static br.com.rhiemer.api.jpa.constantes.ConstantesAtributosJPA.ANNOTATIONS_LAZY;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.engine.spi.SessionImplementor;

import br.com.rhiemer.api.util.helper.Helper;
import net.sf.beanlib.hibernate3.Hibernate3DtoCopier;

public class HelperLazy {

	private HelperLazy() {

	}

	public static <T> void retirarLazyCollection(Collection<T> c) {
		c.stream().forEach(x -> retirarLazy((x)));
	}

	public static void retirarLazy(Object t) {

		List<Field> fields = Helper.allFields(t.getClass());
		for (Field field : fields) {
			Object valueField = Helper.getValueField(t, field);
			if (valueField == null)
				continue;
			if (HelperHibernateProxy.verifyLazy(valueField)) {
				Helper.setValueField(t, field, null);
				continue;
			}

			Object annotation = Arrays.stream(ANNOTATIONS_LAZY)
					.map(x -> field.getAnnotation((Class<? extends Annotation>) x)).filter(y -> y != null).findFirst()
					.orElse(null);
			if (annotation != null) {
				if (valueField instanceof Collection)
					retirarLazyCollection((Collection) valueField);
				else
					retirarLazy(valueField);
			}
		}
	}

	public static <T> T retirarLazyCopy(T t) {
		return (T) retirarLazyCopy(t, t.getClass());
	}

	public static <T> Object retirarLazyCopy(Object t, Class<T> classe) {
		Object _newValue = Helper.newInstance(classe);
		retirarLazyCopy(t, classe);
		return _newValue;
	}

	public static void retirarLazyCopy(Object t, Object newValue) {

		List<Field> fields = Helper.allFields(t.getClass());
		for (Field field : fields) {
			Object valueField = Helper.getValueField(t, field);
			if (valueField == null || HelperHibernateProxy.verifyLazy(valueField))
				continue;
			Object annotation = Arrays.stream(ANNOTATIONS_LAZY)
					.map(x -> field.getAnnotation((Class<? extends Annotation>) x)).filter(y -> y != null).findFirst()
					.orElse(null);
			Object _newValue = valueField;
			if (annotation != null) {
				_newValue = Helper.newInstance(field.getType());
				if (valueField instanceof Collection) {
					Class<?> classePrincipal = Helper.getClassPrincipal(field.getType());
					retirarLazyCollectionCopy((Collection) valueField, (Collection) _newValue, classePrincipal);
				} else {
					retirarLazyCopy(valueField);
				}
			}
			Helper.setValueField(newValue, field, valueField);
		}
	}

	public static <T extends Collection> T retirarLazyCollectionCopy(Collection<?> c, Class<T> classe) {
		Collection<T> _newValue = (Collection) Helper.newInstance(classe);
		retirarLazyCollectionCopy(c, _newValue);
		return (T) _newValue;
	}

	public static void retirarLazyCollectionCopy(Collection<?> c) {
		Collection<?> _newValue = Helper.newInstance(c.getClass());
		retirarLazyCollectionCopy(c, _newValue);
	}

	public static <T, K> void retirarLazyCollectionCopy(Collection<T> c, Collection<K> copyList) {
		c.stream().forEach(x -> copyList.add((K) retirarLazyCopy(x)));
	}

	public static <T, K> void retirarLazyCollectionCopy(Collection<T> c, Collection<K> copyList, Class<K> classe) {
		if (classe == null)
			retirarLazyCollectionCopy(c, copyList);
		else
			c.stream().forEach(x -> copyList.add((K) retirarLazyCopy(x, classe)));
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
