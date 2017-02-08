package br.com.rhiemer.api.rest.client.factory;

import javax.inject.Inject;

import br.com.rhiemer.api.util.annotations.app.ConfigurationFilesAplicacaoApi;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.propriedades.ConfigurationFilesAplicacao;

public class RestClientProxyType<T> extends RestClientProxy {

	@Inject
	private ConfigurationFilesAplicacao configurationFile;

	private Class<?> classe;
	private T service;
	private String propriedade;

	public RestClientProxyType() {
		super();
		this.classe = Helper.getClassPrincipal(this.getClass());

	}

	public RestClientProxyType(Class<?> classe) {
		super();
		this.classe = classe;

	}

	public RestClientProxyType(String url) {
		super(url);
		this.classe = Helper.getClassPrincipal(this.getClass());
	}

	public T getService() {

		if (this.service == null) {
			String _url = getUrl();
			if (_url == null && propriedade != null) {
				_url = configurationFile.valueProperty(propriedade);
			}
			if (_url != null)
				setUrl(_url);
			this.service = (T) createService(this.classe);
		}

		return service;

	}

	public String getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}

}
