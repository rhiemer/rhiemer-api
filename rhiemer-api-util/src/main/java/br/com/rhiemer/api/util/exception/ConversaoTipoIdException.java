package br.com.rhiemer.api.util.exception;

/**
 * Exceção que não encontrou uma entidade com os parametros de uma pesquisa
 * 
 * @author rodrigo.hiemer
 */
public class ConversaoTipoIdException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6382860439267224629L;

	public ConversaoTipoIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConversaoTipoIdException(String message) {
		super(message);
	}

}
