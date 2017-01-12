package br.com.rhiemer.api.test.integration.extension;

import static br.com.rhiemer.api.test.integration.constantes.ConstantesTestIntegration.ARQUILLIAN_PORT;
import static br.com.rhiemer.api.test.integration.constantes.ConstantesTestIntegration.ARQUILLIAN_SERVER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.jboss.arquillian.container.spi.client.protocol.metadata.HTTPContext;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.spi.client.protocol.metadata.Servlet;
import org.jboss.arquillian.container.spi.context.annotation.DeploymentScoped;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;

/**
 * Verificar se há necessidade de configurar o HTTPContext com as as
 * system.properties 'arquillian.management.httpPort' e/ou
 * 'arquillian.management.address' *
 * 
 * @author rodrigo.hiemer
 *
 */
public class ProtocolMetadataRewriter {

	@Inject
	@DeploymentScoped
	private InstanceProducer<ProtocolMetaData> prod;

	/**
	 * caso tenha alguma configuração de porta ou servidor para o Protocolo HTTP
	 * do Arquillian, sobrescrever o protcolo
	 * 
	 * @param metadata
	 */
	public void rewrite(@Observes ProtocolMetaData metadata) {

		List<HTTPContext> listaContextosAClonar = listarContextosAClonar(metadata);

		if (listaContextosAClonar.size() > 0) {
			ProtocolMetaData newData = cloneAndRewrite(metadata, listaContextosAClonar);

			// Since your observing the creation of a ProtocolMetaData you will
			// get called again
			// when you set cloned/recreated metadata on the InstanceProducer.
			// The needToRewriteMetadata check should prevent a wild loop
			prod.set(newData);
		}

	}

	/**
	 * clona o protocolo http do Arquillian com as informações configuradas
	 * 
	 * @param metadata
	 * @return
	 */
	private ProtocolMetaData cloneAndRewrite(ProtocolMetaData metadata, List<HTTPContext> listaContextosAClonar) {

		String serverHost = System.getProperty(ARQUILLIAN_SERVER);
		String portHost = System.getProperty(ARQUILLIAN_PORT);

		ProtocolMetaData newMetadata = new ProtocolMetaData();

		for (Object context : metadata.getContexts()) {
			if (!listaContextosAClonar.contains(context)) {
				newMetadata.addContext(context);
			}
		}

		for (HTTPContext contextAClonar : listaContextosAClonar) {
			String server = serverHost == null ? contextAClonar.getHost() : serverHost;
			int port = portHost == null ? contextAClonar.getPort() : Integer.valueOf(portHost);
			HTTPContext newContext = cloneHTTPContext(contextAClonar, server, port);
			newMetadata.addContext(newContext);
		}

		return newMetadata;
	}

	/**
	 * Clone o protocolo http com as informações configuradas e do protocolo
	 * antigo
	 * 
	 * @param metadata
	 * @param serverHost
	 * @param serverPort
	 * @return
	 */
	private HTTPContext cloneHTTPContext(HTTPContext contextAClonar, String serverHost, int serverPort) {

		HTTPContext newContext = new HTTPContext(serverHost, serverPort);
		for (Servlet servlet : contextAClonar.getServlets()) {
			newContext.add(servlet);
		}
		return newContext;
	}

	/**
	 * Verifica se tem alguma informação configurada e se triver clona o
	 * HTTPContext
	 * 
	 * @param metadata
	 * @return
	 */
	private List<HTTPContext> listarContextosAClonar(ProtocolMetaData metadata) {
		
		
		List<HTTPContext> listaContextosAClonar = new ArrayList<>();

		if (metadata.hasContext(HTTPContext.class)) {

			String serverHost = System.getProperty(ARQUILLIAN_SERVER);
			String portHost = System.getProperty(ARQUILLIAN_PORT);

			final Collection<HTTPContext> listaContext = metadata.getContexts(HTTPContext.class);
			
			Consumer<HTTPContext> consumer =  new Consumer<HTTPContext>() {
				
				public void accept(HTTPContext httpContext) {  
					
					String host = httpContext.getHost();
					int port = httpContext.getPort();
					

					if ((serverHost != null && !serverHost.equals(host))
							|| (portHost != null && !new Integer(port).toString().equals(portHost))) {
						listaContextosAClonar.add(httpContext);
					}
					
					
				}

				
			};
			
			
			listaContext.forEach(consumer);
			

		}
		return listaContextosAClonar;
	}

}
