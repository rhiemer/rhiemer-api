package br.com.rhiemer.api.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class LogAplicacao {

	private static final String ERRO_DEBUG_PREFIX = "erro.debug";
	private static final String ERRO_DEBUG_IDENT = "[ERROR_DEBUG] ";
	
	private Logger logger;
	private Logger loggerErroDebug;

	public LogAplicacao(Logger logger) {
		super();
		this.logger = logger;
		this.loggerErroDebug = LoggerFactory.getLogger(ERRO_DEBUG_PREFIX + "." + logger.getName());
	}

	public void info(String msg) {
		if (isInfoEnabled()) {
			logger.info(msg);
		}
	}

	public void info(String msg, Object... arguments) {
		if (isInfoEnabled()) {
			logger.info(msg, arguments);
		}
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	public void debug(String msg) {
		if (isDebugEnabled()) {
			logger.debug(msg);
		}
	}

	public void debug(String msg, Object... arguments) {
		if (isDebugEnabled()) {
			logger.debug(msg, arguments);
		}
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void trace(String msg) {
		if (isTraceEnabled()) {
			logger.trace(msg);
		}
	}

	public void trace(String msg, Object... arguments) {
		if (isTraceEnabled()) {
			logger.trace(msg, arguments);
		}
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	public void warn(String msg) {
		if (isWarnEnabled()) {
			logger.warn(msg);
		}
	}

	public void warn(String msg, Object... arguments) {
		if (isWarnEnabled()) {
			logger.warn(msg, arguments);
		}
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	public void error(String msg) {
		if (isErrorEnabled()) {
			logger.error(msg);
		}
	}

	public void error(String msg, Throwable e) {
		if (isErrorEnabled()) {
			logger.error(msg, e);
		}
	}

	public void error(String msg, Throwable e, Object... arguments) {
		if (isErrorEnabled()) {
			FormattingTuple ft = MessageFormatter.arrayFormat(msg, arguments);
			logger.error(ft.getMessage(), e);
		}
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	public boolean isErrorDebugEnabled() {
		return loggerErroDebug.isErrorEnabled();
	}

	public void errorDebug(String msg) {
		if (isErrorDebugEnabled()) {
			loggerErroDebug.error(ERRO_DEBUG_IDENT.concat(msg));
		}
	}

	public void errorDebug(String msg, Throwable e) {
		if (isErrorDebugEnabled()) {
			loggerErroDebug.error(ERRO_DEBUG_IDENT.concat(msg), e);
		}
	}

	public void errorDebug(String msg, Throwable e, Object... arguments) {
		if (isErrorDebugEnabled()) {
			FormattingTuple ft = MessageFormatter.arrayFormat(ERRO_DEBUG_IDENT.concat(msg), arguments);
			loggerErroDebug.error(ft.getMessage(), e);
		}
	}

}
