package br.com.rhiemer.api.util.cdi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rhiemer.api.util.producer.Producer;

@ApplicationScoped
public class ConfiguracoesAplicacao {

	private static final Logger logger = LoggerFactory.getLogger(ConfiguracoesAplicacao.class);
	public static final String VERSAO_APLICACAO = "VERSAO.ALICACAO";
	public static final String NOME_APLICACAO = "APP.NAME";

	@Inject
	private BeanManager beanManager;

	private Properties configuracoes = new Properties();
	protected List<String> listaArquivosConfiguracoes = new ArrayList<>();

	public String getConfiguracao(String key) {
		if (listaArquivosConfiguracoes == null || listaArquivosConfiguracoes.size() == 0)
			carregarArquivos();
		return configuracoes.getProperty(key);
	}

	public String getVersaoAplicacao() {
		return getConfiguracao(VERSAO_APLICACAO);
	}
	
	public String getNomeAplicacao() {
		return getConfiguracao(NOME_APLICACAO);
	}

	protected void configuracoesProperties(String arquivo) {
		InputStream is = Producer.class.getClassLoader().getResourceAsStream(arquivo);
		if (is == null) {

			if (logger.isWarnEnabled()) {
				logger.warn("Arquivo {} não encontrado no resource da aplicação.", arquivo);
			}
			return;
		}

		Properties configuracoesCarregar = new Properties();
		try {
			configuracoesCarregar.load(is);
			configuracoes.putAll(configuracoesCarregar);
		} catch (Exception e) {
			logger.error("Erro ao carregar configurações da aplicação", e);
		}

	}

	protected void arquivosConfiguracoes() {

		listaArquivosConfiguracoes.add("environment.properties");

	}

	protected String textoConfiguracoesAplicacao() {

		StringBuilder sb = new StringBuilder("\nConfigurações da Aplicação\n");
		for (Map.Entry<Object, Object> propriedade : configuracoes.entrySet()) {
			sb.append(" ").append(propriedade.getKey()).append("=").append(propriedade.getValue()).append("\n");
		}
		return sb.toString();
	}

	public void logarConfiguracoesAplicacao() {
		if (listaArquivosConfiguracoes == null || listaArquivosConfiguracoes.size() == 0)
			carregarArquivos();
		logger.info(textoConfiguracoesAplicacao());

	}

	protected void carregarArquivos() {
		arquivosConfiguracoes();
		for (String arquivoConfiguracao : listaArquivosConfiguracoes) {
			configuracoesProperties(arquivoConfiguracao);
		}
	}

	@PostConstruct
	public void init(@Observes @Initialized(ApplicationScoped.class) Object ctx) {
		logarConfiguracoesAplicacao();
		logger.info("BeanManager: {}.", beanManager.toString());
	}

}
