package br.com.rhiemer.api.test.integration.extension;

import org.jboss.arquillian.core.spi.LoadableExtension;

/**
 * O Arquillian sempre que rodar os testes remotamente num servidor que não seja
 * localhost sempre injeta na URL do contexto 127.0.0.1:8080, por isso de
 * configurar o servidor e a porta do contexto do servidor remoto
 * 
 * Para que a classe seja processada pelo Arquillian incluir o arquivo /META-INF/services/org.jboss.arquillian.core.spi.LoadableExtension
 * Com o conteúdo 'br.gov.ancine.api.test.integration.extension.NATExtension' 
 * 
 * @author rodrigo.hiemer
 *
 */
public class NATExtension implements LoadableExtension {

	@Override
	public void register(ExtensionBuilder builder) {
		builder.observer(ProtocolMetadataRewriter.class);
	}

}
