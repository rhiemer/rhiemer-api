package br.com.rhiemer.api.util.exception;

/**
 * Exceção pai de todas as exceções de sistema.
 * 
 * @author <a href="mailto:asouza@redhat.com">Ângelo Galvão</a>
 */
public class APISystemException extends RuntimeException {

	private static final long serialVersionUID = 7720172632206418386L;

	public APISystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public APISystemException(String message) {
		super(message);
	}

}
