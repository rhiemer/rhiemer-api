package br.com.rhiemer.api.util.exception;

/**
 * Exceção de negocio
 * 
 * @author rodrigo.hiemer
 */
public class NegocioException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6382860439267224629L;

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegocioException(String message) {
		super(message);
	}

}
