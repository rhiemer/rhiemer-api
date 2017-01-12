package br.com.rhiemer.api.rest.client.bean;

import java.util.Arrays;

import br.com.rhiemer.api.rest.client.annotation.BeanDiscoveryRestClient;
import br.com.rhiemer.api.rest.client.factory.RestClientProxyType;
import br.com.rhiemer.api.util.cdi.bean.BeanAPI;

public class BeanRestClient<T> extends BeanAPI<T> {

	
    private final BeanDiscoveryRestClient beanDiscoveryRestClient;
    
     
    
	public BeanRestClient(Class<T> classe,BeanDiscoveryRestClient beanDiscoveryRestClient) {
		super(classe,beanDiscoveryRestClient.proxyType());
		this.beanDiscoveryRestClient = beanDiscoveryRestClient;
	}
	
	@Override
	protected Object createClass() {
		
		RestClientProxyType restClientProxyType = new RestClientProxyType(getClass());
		restClientProxyType.setUrl(beanDiscoveryRestClient.url());
		restClientProxyType.setPropriedade(beanDiscoveryRestClient.propriedade());
		
		Class[] filters = null;
		BeanDiscoveryRestClient.RestClientAppFilters filtersAnnotation = this.getClass()
				.getAnnotation(BeanDiscoveryRestClient.RestClientAppFilters.class);
		if (filtersAnnotation != null)
		{	
			restClientProxyType.setFiltersClass(Arrays.asList(filtersAnnotation.filters()));
		}	

		Object servico = restClientProxyType.getService();
	    return servico;	
	}


	public BeanDiscoveryRestClient getBeanDiscoveryRestClient() {
		return beanDiscoveryRestClient;
	}

	
}
