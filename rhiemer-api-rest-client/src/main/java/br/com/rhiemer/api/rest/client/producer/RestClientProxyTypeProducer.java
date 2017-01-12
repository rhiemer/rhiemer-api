package br.com.rhiemer.api.rest.client.producer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import br.com.rhiemer.api.rest.client.annotation.BeanDiscoveryRestClient;
import br.com.rhiemer.api.rest.client.annotation.BeanDiscoveryRestClient.RestClientAppFilters;
import br.com.rhiemer.api.rest.client.factory.RestClientProxyType;

public class RestClientProxyTypeProducer {

	@Dependent
	@Produces
	@BeanDiscoveryRestClient
	public RestClientProxyType getRestClientProxyTypeRestClientApp(InjectionPoint ip) throws InstantiationException, IllegalAccessException {

		ParameterizedType type = (ParameterizedType) ip.getType();
		Type[] typeArgs = type.getActualTypeArguments();
		Class<?> serviceClass = (Class<?>) typeArgs[0];
		
		RestClientProxyType restClientProxyType = (RestClientProxyType) serviceClass.newInstance();
		RestClientAppFilters restClientAppFilter = ip.getAnnotated().getAnnotation(RestClientAppFilters.class);
		BeanDiscoveryRestClient restClientApp = ip.getAnnotated().getAnnotation(BeanDiscoveryRestClient.class);
		if (restClientApp != null)
		{	
		  restClientProxyType.setUrl(restClientApp.url());
		  restClientProxyType.setPropriedade(restClientApp.propriedade());
		} 
		if (restClientAppFilter != null)
		{
			restClientProxyType.setFiltersClass(Arrays.asList(restClientAppFilter.filters()));
		}	
		
		
		return restClientProxyType;

	}


}
