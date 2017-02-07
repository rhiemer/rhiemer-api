package br.com.rhiemer.api.util.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import br.com.rhiemer.api.util.annotations.app.ProxyBuilderAplicacao;
import br.com.rhiemer.api.util.proxy.ProxyCallMetodoJavassist;
import br.com.rhiemer.api.util.proxy.ProxyMetodoBuilder;

/**
 * Producer default das APIs rhiemer.
 * 
 * @author rhiemer
 *
 */

@ApplicationScoped
public class ProxyBuilderAplicacaoProducer {

	@Produces
	@ProxyBuilderAplicacao
	public ProxyMetodoBuilder getProxyBuilderAplicacao() {
		ProxyMetodoBuilder proxyMetodoBuilder = new ProxyMetodoBuilder(ProxyCallMetodoJavassist.class);
		return proxyMetodoBuilder;
	}

}
