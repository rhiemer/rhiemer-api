package br.com.rhiemer.api.rest.resources.list;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rhiemer.api.util.cdi.ConfiguracoesAplicacao;
import br.com.rhiemer.api.util.lambda.optinal.HelperOptional;

@ApplicationScoped
public class LogResourceList {

	private static final Logger logger = LoggerFactory.getLogger(LogResourceList.class);

	@Inject
	private ConfiguracoesAplicacao configuracaAplicacao;

	@PostConstruct
	public void init(@Observes @Initialized(ApplicationScoped.class) Object ctx) {

		if (logger.isDebugEnabled()) {
			try {
				String listResources = HelperListResorce.listResourceRestEasy();
				HelperOptional.ofIsBlank(listResources)
						.ifPresent(t -> logger.debug("EndPoitns da aplicação:\nVersão:{}\n{}\n",
								configuracaAplicacao.getVersaoAplicacao(), t));
			} catch (Exception e) {
				logger.error(String.format("Erro ao carregar endpoints da aplicação:%s", e.getMessage()), e);
			}
		}

	}

}
