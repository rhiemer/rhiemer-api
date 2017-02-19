package br.com.rhiemer.api.util.exception;

import br.com.rhiemer.api.util.helper.HelperException;
import br.com.rhiemer.api.util.helper.HelperMessage;

/**
 * Exceção pai de todas as exceções de sistema.
 * 
 * @author <a href="mailto:asouza@redhat.com">Ângelo Galvão</a>
 */
public class APPSystemException extends RuntimeException implements IAPPSystemException {

	private static final long serialVersionUID = 7720172632206418386L;

	public APPSystemException(String message, Object... params) {
		super(HelperMessage.formatMessage(message, params));
	}

	public APPSystemException(String message, Throwable cause, Object... params) {
		super(HelperMessage.formatMessage(message, params), HelperException.tratarThrowApp(cause));
	}

	public APPSystemException(Throwable cause) {
		super(HelperException.tratarStrThrowApp(cause),
				HelperException.tratarThrowApp(cause));
	}

}
