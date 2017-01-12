package br.com.rhiemer.api.test.integration.rest.client;

import org.hibernate.validator.constraints.URL;
import org.jboss.arquillian.test.api.ArquillianResource;

import br.com.rhiemer.api.rest.client.factory.RestClientProxyBuilder;

public class RestClientProxyBuilderArquillian extends RestClientProxyBuilder {

	@ArquillianResource
	private URL urlRoot;

	
	public RestClientProxyBuilder setUrl(String url) {
		super.setUrl(urlRoot.toString().concat(url));
		return this;
	}
	
	public static RestClientProxyBuilderArquillian builderArquillian()
	{
		return new RestClientProxyBuilderArquillian();
	}

}
