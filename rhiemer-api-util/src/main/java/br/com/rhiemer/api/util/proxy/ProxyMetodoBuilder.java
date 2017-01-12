package br.com.rhiemer.api.util.proxy;

import br.com.rhiemer.api.util.cdi.CDIUtil;
import br.com.rhiemer.api.util.helper.ProxyUtils;

public class ProxyMetodoBuilder {

	private Class<? extends ProxyCallMetodo> classFactory;

	public ProxyMetodoBuilder(Class<? extends ProxyCallMetodo> classFactory) {
		super();
		this.classFactory = classFactory;
	}

	protected <T> ProxyCallMetodo createFactory(T target, Class<?>... classesBean) {
		try {
			return this.classFactory.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public <T, K> K builder(T target, Class<?>... classesBean) {
		ProxyCallMetodo factory = createFactory(target, classesBean);
		factory.setClassesBean(classesBean);
		factory.setTarget(target);
		return (K) factory.builder();
	}

	public <T, K> K builderBean(T target, Class<?> classe) {
		if (classe != null && target.getClass().isAssignableFrom(classe)) {
			return (K) target;
		} else {
			Class _clazzBean = classe;
			if (classe == null) {
				_clazzBean = getClassProxiedCdi(target.getClass());
				if (_clazzBean == null) {
					return (K) target;
				}
			}
			
			K proxyInst = (K) builder(target, _clazzBean);
			return proxyInst;
		}
	}

	public <T, K> K builderBeanProxy(Class<T> clazz) {
		return builderBean(clazz);
	}

	public <T, K> K builderBean(T proxy) {

		return builderBean(proxy, null);

	}

	public <T, K> K builderBeanCDI(Class<T> clazz, Class<K> clazzBean) {
		T obj = CDIUtil.getBean(clazz);
		if (obj == null)
			return null;
		K proxy = builderBean(obj, clazzBean);
		return proxy;
	}
	
	

	public <T, K> K builderBeanCDI(String name) {
		T obj = (T)CDIUtil.createBeanByName(name);
		if (obj == null)
			return null;
		K proxy = builderBean(obj,obj.getClass());
		return proxy;
	}
	

	public <T> T verificaSeCriaProxy(T proxy) {
		Class _clazzBean = getClassProxiedCdi(proxy.getClass());
		if (_clazzBean == null) {
			return (T) proxy;
		} else {
			return (T) builder(proxy, _clazzBean);
		}

	}

	public Class<?> getClassProxiedCdi(Class<?> classe) {

		return ProxyUtils.getClassProxiedCdi(classe);
	}

}
