package br.com.rhiemer.api.util.cdi.bean;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;

import java.lang.annotation.Annotation;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.util.AnnotationLiteral;

import br.com.rhiemer.api.util.cdi.CDIUtil;
import br.com.rhiemer.api.util.cdi.qualifier.ProxyBuilderAplicacaoQualifier;
import br.com.rhiemer.api.util.proxy.ProxyMetodoBuilder;

public class BeanAPI<T> implements Bean<T> {

	private final Set<Type> types;
	private final Set<Annotation> qualifiers;
	private final Class<T> clazz;
	private final Class<?> proxyType;
	private final BeanManager bm;
	private final AnnotatedType annotatedType;
	private InjectionTarget injectionTarget = null;

	public BeanAPI(final Class<T> clazz, BeanManager bm,AnnotatedType annotatedType) {
		this.clazz = clazz;
		this.types = new HashSet<>(asList(clazz, Object.class));
		this.qualifiers = new HashSet<>(asList(new AnnotationLiteral<Any>() {
		}, new AnnotationLiteral<Default>() {
		}));
		this.proxyType = null;
		this.bm = bm;
		this.annotatedType = annotatedType;
		try {
			injectionTarget = this.bm.createInjectionTarget(this.annotatedType);
		} catch (Exception e) {
		}

	}

	public BeanAPI(final Class<T> clazz, final Class<?> proxyType, BeanManager bm,AnnotatedType annotatedType) {
		this.clazz = clazz;
		this.types = new HashSet<>(asList(clazz, Object.class));
		this.qualifiers = new HashSet<>(asList(new AnnotationLiteral<Any>() {
		}, new AnnotationLiteral<Default>() {
		}));

		this.proxyType = proxyType;
		this.bm = bm;
		this.annotatedType = annotatedType;
		try {
			injectionTarget = this.bm.createInjectionTarget(this.annotatedType);
		} catch (Exception e) {
		}

	}

	@Override
	public Set<Type> getTypes() {
		return types;
	}

	@Override
	public Set<Annotation> getQualifiers() {
		return qualifiers;
	}

	@Override
	public Class<? extends Annotation> getScope() {
		return Dependent.class;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean isNullable() {
		return false;
	}

	@Override
	public Set<InjectionPoint> getInjectionPoints() {
		try {
			return this.injectionTarget.getInjectionPoints();
		} catch (Exception e) {
			return emptySet();
		}
	}

	@Override
	public Class<?> getBeanClass() {
		return clazz;
	}

	@Override
	public Set<Class<? extends Annotation>> getStereotypes() {
		return emptySet();
	}

	@Override
	public boolean isAlternative() {
		return false;
	}

	@Override
	public T create(final CreationalContext<T> context) {
		if (clazz == null)
			return null;
		if (getProxyType() == null || getProxyType().equals(Void.class)) {
			return (T) createClass();

		} else {
			return (T) createProxy();
		}

	}

	protected Object createClass() {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	protected Object createProxy() {
		Object result = null;
		BeanManager bm = CDIUtil.getBeanManager();
		Set<Bean<?>> beansProxyBuilder = bm.getBeans(ProxyMetodoBuilder.class, new ProxyBuilderAplicacaoQualifier());
		final Bean<?> beanProxyBuilder = bm.resolve(beansProxyBuilder);
		CreationalContext ctxProxyBuilder = bm.createCreationalContext(beanProxyBuilder);
		ProxyMetodoBuilder proxyMetodoBuilder = (ProxyMetodoBuilder) bm.getReference(beanProxyBuilder,
				beanProxyBuilder.getBeanClass(), ctxProxyBuilder);

		Object objProxy = CDIUtil.getBean(getProxyType());
		if (objProxy == null)
			objProxy = (T) createClass();

		Object obj = proxyMetodoBuilder.builder(objProxy, clazz);
		return obj;
	}

	@Override
	public void destroy(final T instance, final CreationalContext<T> context) {
		// no-op
	}

	public Class<?> getProxyType() {
		return proxyType;
	}

}
