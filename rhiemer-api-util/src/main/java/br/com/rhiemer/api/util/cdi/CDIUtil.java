package br.com.rhiemer.api.util.cdi;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionPoint;

import br.com.rhiemer.api.util.cdi.qualifier.ProxyBuilderAplicacaoQualifier;
import br.com.rhiemer.api.util.proxy.ProxyMetodoBuilder;

public final class CDIUtil {

	private CDIUtil() {
	}

	public static BeanManager getBeanManager() {
		try {
			BeanManager beanManager = CDI.current().getBeanManager();
			if (beanManager == null)
			 throw new RuntimeException();
			return beanManager;
		} catch (Exception e) {
			throw new RuntimeException("Não pôde encontrar BeanManager no JNDI.");
		}
	}

	public static Object createBeanByName(String name, BeanManager bm) {

		Set<Bean<?>> setBeans = bm.getBeans(name);
		if (setBeans == null || setBeans.isEmpty())
			return null;

		final Bean<?> bean = bm.resolve(setBeans);
		if (bean != null) {
			CreationalContext ctx = bm.createCreationalContext(bean);
			Object o = bean.create(ctx);
			return o;
		} else
			return null;

	}

	public static Object createBeanByName(String name) {

		return createBeanByName(name, getBeanManager());
	}

	public static <T> T createBeanByClasse(Class<T> classe, BeanManager bm) {

		final Set<Bean<?>> beans = bm.getBeans(classe);
		if (beans == null || beans.isEmpty())
			return null;

		final Bean<?> bean = bm.resolve(beans);
		
		if (bean != null) {
			CreationalContext ctx = bm.createCreationalContext(bean);
			Object o = bean.create(ctx);
			return (T) o;
		} else
			return null;

	}
	
	public static <T> T createBeanByClasse(Class<T> classe, BeanManager bm,Annotation... annotations) {

		final Set<Bean<?>> beans = bm.getBeans(classe,annotations);
		if (beans == null || beans.isEmpty())
			return null;

		final Bean<?> bean = bm.resolve(beans);
		if (bean != null) {
			CreationalContext ctx = bm.createCreationalContext(bean);
			Object o = bean.create(ctx);
			return (T) o;
		} else
			return null;

	}
	
	public static <T> T createBeanByClasse(Class<T> classe,Annotation... annotations) {

		return createBeanByClasse(classe, getBeanManager(),annotations);
	}

	public static <T> T createBeanByClasse(Class<T> classe) {

		return createBeanByClasse(classe, getBeanManager());
	}

	public static <T> T getBean(Class<T> clazz, BeanManager bm) {
		final Set<Bean<?>> beans = bm.getBeans(clazz);
		if (beans == null || beans.isEmpty())
			return null;
		final Bean<?> bean = bm.resolve(beans);

		if (bean != null) {
			CreationalContext ctx = bm.createCreationalContext(bean);
			return (T) bm.getReference(bean, bean.getBeanClass(), ctx);
		} else
			return null;
	}
	
	
	public static <T> T getBean(Class<T> clazz,BeanManager bm,Annotation... annotations) {
		final Set<Bean<?>> beans = bm.getBeans(clazz,annotations);
		if (beans == null || beans.isEmpty())
			return null;
		final Bean<?> bean = bm.resolve(beans);
		
		

		if (bean != null) {
			CreationalContext ctx = bm.createCreationalContext(bean);
			return (T) bm.getReference(bean, bean.getBeanClass(), ctx);
		} else
			return null;
	}
	
	public static <T> T getBean(Class<T> clazz,Annotation... annotations) {

		return getBean(clazz, getBeanManager(),annotations);
	}

	public static <T> T getBean(Class<T> clazz) {

		return getBean(clazz, getBeanManager());
	}

	public static <T> T getBean(String beanName, BeanManager bm) {
		final Set<Bean<?>> beans = bm.getBeans(beanName);
		if (beans == null || beans.isEmpty())
			return null;
		final Bean<?> bean = bm.resolve(beans);

		if (bean != null) {
			CreationalContext ctx = bm.createCreationalContext(bean);
			return (T) bm.getReference(bean, bean.getBeanClass(), ctx);
		} else
			return null;
	}

	public static <T> T getBean(String beanName) {
		return getBean(beanName, getBeanManager());
	}
	
	public static <T> T getBeanOrCreate(Class<T> classe, BeanManager bm,Annotation... annotations) {
		Object bean = getBean(classe,bm,annotations);
		if (bean == null)
		 return createBeanByClasse(classe, bm, annotations);
		else
		  return (T)bean;	
	}
	
	public static <T> T getBeanOrCreate(Class<T> classe) {
		Object bean = getBean(classe);
		if (bean == null)
		 return createBeanByClasse(classe);
		else
		  return (T)bean;	
	}
	
	public static <T> T getBeanOrCreate(Class<T> classe,Annotation... annotations) {
		Object bean = getBean(classe,annotations);
		if (bean == null)
		 return createBeanByClasse(classe,annotations);
		else
		  return (T)bean;	
	}
	
	public static Object getBeanOrCreate(String name) {
		Object bean = getBean(name);
		if (bean == null)
		 return createBeanByName(name);
		else
		  return bean;	
	}
	
	public static Object getBeanOrCreate(String name, BeanManager bm) {
		Object bean = getBean(name,bm);
		if (bean == null)
		 return createBeanByName(name,bm);
		else
		  return bean;	
	}
	
	
	
	

}
