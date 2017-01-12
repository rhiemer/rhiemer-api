package br.com.rhiemer.api.util.exception;

/**
 * Exceção que não encontrou uma entidade com os parametros de uma pesquisa
 * 
 * @author rodrigo.hiemer
 */
public class NotFoundException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6382860439267224629L;

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
	}

}
