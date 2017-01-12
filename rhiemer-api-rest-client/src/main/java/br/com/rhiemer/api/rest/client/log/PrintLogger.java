package br.com.rhiemer.api.rest.client.log;

import org.slf4j.Logger;

/**
 * Classe que define como se escerve o log dos webservices
 * 
 * @author rodrigo.hiemer
 *
 */
public class PrintLogger {

	private static final String HABILITAR_LOG_TEST_REST = "habilitar.log.test.rest";

	private PrintLogger() {
	}

	public static boolean verificarLog(Logger logger) {
		if (logger.isDebugEnabled() || isTrace(logger))
			return true;
		else
			return false;
	}

	public static boolean isTrace(Logger logger) {
		if (logger.isTraceEnabled() || "S".equals(System.getProperty(HABILITAR_LOG_TEST_REST)))
			return true;
		else
			return false;
	}

	public static void print(Logger logger, String str) {
		if ("S".equals(System.getProperty(HABILITAR_LOG_TEST_REST))) {
			if (logger.isInfoEnabled())
				logger.info(str);
			else
				System.out.println(str);
		} else if (logger.isTraceEnabled())
			logger.trace(str);
		else if (logger.isDebugEnabled())
			logger.debug(str);

	}

}
