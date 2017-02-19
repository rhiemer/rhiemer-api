package br.com.rhiemer.api.util.exception;

import br.com.rhiemer.api.util.helper.HelperException;
import br.com.rhiemer.api.util.helper.HelperMessage;

public class APPIllegalArgumentException extends IllegalArgumentException implements IAPPSystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2433433073844568046L;

	public APPIllegalArgumentException(String message, Object... params) {
		super(HelperMessage.formatMessage(message, params));
	}

	public APPIllegalArgumentException(String message, Throwable cause, Object... params) {
		super(HelperMessage.formatMessage(message, params), HelperException.tratarThrowApp(cause));
	}

	public APPIllegalArgumentException(Throwable cause) {
		super(HelperException.tratarStrThrowApp(cause), HelperException.tratarThrowApp(cause));
	}

}
