package br.com.rhiemer.api.jpa.helper;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import br.com.rhiemer.api.util.helper.Helper;

public final class HelperHibernateProxy {

	private HelperHibernateProxy() {

	}

	public static boolean isHibernateProxy(final Object object) {
		if (object == null)
			return false;
		return HibernateProxy.class.isAssignableFrom(object.getClass());
	}

	public static boolean isPersistentCollection(final Object object) {
		if (object == null)
			return false;
		return PersistentCollection.class.isAssignableFrom(object.getClass());
	}

	public static boolean isHibernateProxOrCollectiony(final Object object) {
		return isHibernateProxy(object) || isPersistentCollection(object);
	}

	public static boolean verifyLazy(final Object object) {
		if (object != null) {
			if (isHibernateProxy(object)) {
				final HibernateProxy hibernateProxy = (HibernateProxy) object;
				final LazyInitializer lazyInitializer = hibernateProxy.getHibernateLazyInitializer();
				return (lazyInitializer.isUninitialized());
			} else if (isPersistentCollection(object)) {
				final PersistentCollection persistenceCollection = (PersistentCollection) object;
				return (!persistenceCollection.wasInitialized());
			}
		}
		return false;
	}

	public static SessionImplementor getSession(final Object object) {
		if (isHibernateProxy(object)) {
			final HibernateProxy hibernateProxy = (HibernateProxy) object;
			final LazyInitializer lazyInitializer = hibernateProxy.getHibernateLazyInitializer();
			return lazyInitializer.getSession();
		} else if (isPersistentCollection(object)) {
			return (SessionImplementor) Helper.getValueMethodOrField(object, "session");
		}

		return null;

	}

	public static SessionImplementor getSessionProperty(final Object object) {
		if (object == null)
			return null;
		SessionImplementor session = getSession(object);
		if (session != null)
			return session;

		List<Field> fields = Helper.allFields(object.getClass());
		for (Field field : fields) {
			Object valueField = Helper.getValueField(object, field);
			session = getSession(valueField);
			if (session != null)
				return session;
		}

		return null;

	}

}
