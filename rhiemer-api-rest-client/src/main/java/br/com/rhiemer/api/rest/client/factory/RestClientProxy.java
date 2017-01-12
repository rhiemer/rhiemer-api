package br.com.rhiemer.api.rest.client.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import br.com.rhiemer.api.rest.client.log.LoggingRequestFilter;
import br.com.rhiemer.api.rest.client.log.LoggingResponseFilter;
import br.com.rhiemer.api.util.annotations.RestClientFilters;
import br.com.rhiemer.api.util.annotations.RestClientPoxyMetodoInterceptor;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.proxy.ProxyCallMetodo;
import br.com.rhiemer.api.util.proxy.ProxyCallMetodoHandlerJavassit;
import br.com.rhiemer.api.util.proxy.ProxyCallMetodoJavassist;

public class RestClientProxy {

	static {
		RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
	}

	private ResteasyClient restClient;
	private ResteasyWebTarget target;
	private String url;
	private List<Class<?>> filtersClass;
	private List<Object> filtersObject;
	private ProxyCallMetodo proxyCall;

	public RestClientProxy() {
		super();
	}

	public RestClientProxy(String url) {
		super();
		this.url = url;
	}

	protected void adicionarLog() {
		target.register(LoggingRequestFilter.class);
		target.register(LoggingResponseFilter.class);
	}

	protected void adicionarFilterClass(Class<?> clazz) {
		if (filtersClass != null) {
			for (Class<?> filter : filtersClass) {
				target.register(filter);
			}
		}

		RestClientFilters restClientFilters = clazz.getAnnotation(RestClientFilters.class);
		if (restClientFilters != null) {
			for (Class<?> filter : restClientFilters.value()) {
				target.register(filter);
			}
		}

	}

	protected void adicionarFilterObject() {
		if (filtersObject != null) {
			for (Object filter : filtersObject) {
				target.register(filter);
			}
		}
	}

	protected void build(Class<?> clazz) {
		if (restClient == null)
			restClient = new ResteasyClientBuilder().build();

	}

	protected void createTarget(Class<?> clazz) {
		build(clazz);
		target = restClient.target(getUrl());
		adicionarLog();
		adicionarFilterClass(clazz);
		adicionarFilterObject();
	}

	public <T> T createService(Class<T> clazz) {
		createTarget(clazz);
		Object targetProxy = target.proxy(clazz);
		Object proxy = verifyCreateProxy((T) targetProxy, clazz);
		if (proxy != null) {
			targetProxy = (T) proxy;
		}
		return (T) targetProxy;
	}

	public <T> T verifyCreateProxy(T objeto, Class<T> classeProxy) {
		ProxyCallMetodo _proxyCall = null;
		if (this.getProxyCall() != null) {
			_proxyCall = this.getProxyCall();
		}

		else {

			RestClientPoxyMetodoInterceptor restClientPoxyInterceptor = Helper.buscarAnnotation(classeProxy,RestClientPoxyMetodoInterceptor.class);
			if (restClientPoxyInterceptor != null) {
				try {
					_proxyCall = ProxyCallMetodoJavassist.class.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException(e);
				}
				_proxyCall.setTarget(objeto);
				_proxyCall.setClassesBean(classeProxy);
				ProxyCallMetodoHandlerJavassit metodoHandler;
				try {
					metodoHandler = restClientPoxyInterceptor.value().getConstructor(Object.class).newInstance(objeto);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					throw new RuntimeException(e instanceof InvocationTargetException ? e.getCause() : e);
				}
				((ProxyCallMetodoJavassist) _proxyCall).setMethodHandler(metodoHandler);
			}

		}
		if (_proxyCall == null) {
			return null;
		} else {
			Object proxy = _proxyCall.builder();
			return (T) proxy;

		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Class<?>> getFiltersClass() {
		return filtersClass;
	}

	public void setFiltersClass(List<Class<?>> filtersClass) {
		this.filtersClass = filtersClass;
	}

	public List<Object> getFiltersObject() {
		return filtersObject;
	}

	public void setFiltersObject(List<Object> filtersObject) {
		this.filtersObject = filtersObject;
	}

	public static RestClientProxyBuilder builder() {
		return new RestClientProxyBuilder();
	}

	public ProxyCallMetodo getProxyCall() {
		return proxyCall;
	}

	public void setProxyCall(ProxyCallMetodo proxyCall) {
		this.proxyCall = proxyCall;
	}

}
