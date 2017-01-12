package br.com.rhiemer.api.util.log;

import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class LogAplicacao {

	private Logger logger;

	public LogAplicacao(Logger logger) {
		super();
		this.logger = logger;
	}

	public void info(String msg) {
		if (logger.isInfoEnabled()) {
			logger.info(msg);
		}
	}

	public void info(String msg, Object... arguments) {
		if (logger.isInfoEnabled()) {
			logger.info(msg, arguments);
		}
	}
	
	public boolean isInfoEnabled()
	{
		return logger.isInfoEnabled();
	}

	public void debug(String msg) {
		if (logger.isDebugEnabled()) {
			logger.debug(msg);
		}
	}

	public void debug(String msg, Object... arguments) {
		if (logger.isDebugEnabled()) {
			logger.debug(msg, arguments);
		}
	}
	
	public boolean isDebugEnabled()
	{
		return logger.isDebugEnabled();
	}
	

	public void trace(String msg) {
		if (logger.isTraceEnabled()) {
			logger.trace(msg);
		}
	}

	public void trace(String msg, Object... arguments) {
		if (logger.isTraceEnabled()) {
			logger.trace(msg, arguments);
		}
	}
	
	public boolean isTraceEnabled()
	{
		return logger.isTraceEnabled();
	}

	public void warn(String msg) {
		if (logger.isWarnEnabled()) {
			logger.warn(msg);
		}
	}

	public void warn(String msg, Object... arguments) {
		if (logger.isWarnEnabled()) {
			logger.warn(msg, arguments);
		}
	}
	
	
	public boolean isWarnEnabled()
	{
		return logger.isWarnEnabled();
	}

	public void error(String msg) {
		if (logger.isErrorEnabled()) {
			logger.error(msg);
		}
	}

	public void error(String msg, Throwable e) {
		if (logger.isErrorEnabled()) {
			logger.error(msg, e);
		}
	}

	public void error(String msg, Throwable e, Object... arguments) {
		if (logger.isErrorEnabled()) {
			FormattingTuple ft = MessageFormatter.arrayFormat(msg, arguments);
			logger.error(ft.getMessage(), e);
		}
	}
	
	public boolean isErrorEnabled()
	{
		return logger.isErrorEnabled();
	}

}
