package br.com.rhiemer.api.util.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rhiemer.api.util.annotations.app.LogApp;
import br.com.rhiemer.api.util.log.LogAplicacao;

/**
 * Producer default das APIs rhiemer.
 * 
 * @author rhiemer
 *
 */

@ApplicationScoped
public class LogAplicacaoProducer {

	

	@Produces
	@LogApp
	public LogAplicacao getLoggerAplicacao(InjectionPoint injectionPoint) {
		Logger logger = LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
		LogAplicacao logAplicacao = new LogAplicacao(logger);
		return logAplicacao;
	}

}
