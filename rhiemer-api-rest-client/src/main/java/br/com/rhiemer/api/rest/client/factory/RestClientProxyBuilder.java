package br.com.rhiemer.api.rest.client.factory;

import java.util.Arrays;
import java.util.List;

import br.com.rhiemer.api.util.proxy.ProxyCallMetodo;

public class RestClientProxyBuilder {

	private String url;
	private List<Class<?>> filtersClass;
	private List<Object> filtersObject;
	private ProxyCallMetodo proxyCall;

	public RestClientProxyBuilder setUrl(String url) {
		this.url = url;
		return this;
	}

	public RestClientProxyBuilder setFiltersClass(Class<?>... filtersClass) {

		if (filtersClass != null)
			this.filtersClass = Arrays.asList(filtersClass);
		return this;
	}

	public RestClientProxyBuilder setFiltersObject(Object... filtersObject) {
		if (filtersObject != null)
			this.filtersObject = Arrays.asList(filtersObject);
		return this;
	}


	public RestClientProxyBuilder setProxyCall(ProxyCallMetodo proxyCall) {
		this.proxyCall = proxyCall;
		return this;
	}

	public RestClientProxy builder() {
		RestClientProxy restClientProxy = new RestClientProxy();
		restClientProxy.setUrl(this.url);
		restClientProxy.setFiltersObject(this.filtersObject);
		restClientProxy.setFiltersClass(this.filtersClass);
		restClientProxy.setProxyCall(this.proxyCall);
		return restClientProxy;
	}

	

}
