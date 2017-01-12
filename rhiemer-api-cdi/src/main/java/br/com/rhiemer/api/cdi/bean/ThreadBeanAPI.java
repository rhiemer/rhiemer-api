package br.com.rhiemer.api.cdi.bean;

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
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.AnnotationLiteral;

import br.com.rhiemer.api.util.annotations.ThreadScoped;
import br.com.rhiemer.api.util.cdi.CDIUtil;
import br.com.rhiemer.api.util.cdi.qualifier.ProxyBuilderAplicacaoQualifier;
import br.com.rhiemer.api.util.proxy.ProxyMetodoBuilder;

public class ThreadBeanAPI<T> implements Bean<T> {

	private final Set<Type> types;
	private final Set<Annotation> qualifiers;
	private final Class<T> clazz;

	public ThreadBeanAPI(final Class<T> clazz) {
		this.clazz = clazz;
		this.types = new HashSet<>(asList(clazz, Object.class));
		this.qualifiers = new HashSet<>(asList(new AnnotationLiteral<Any>() {
		}, new AnnotationLiteral<Default>() {
		}));

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
		return ThreadScoped.class;
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
		return emptySet();
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

		try {
			return (T) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void destroy(final T instance, final CreationalContext<T> context) {
		// no-op
	}

}
