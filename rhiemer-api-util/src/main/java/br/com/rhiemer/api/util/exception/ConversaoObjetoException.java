package br.com.rhiemer.api.util.exception;

/**
 * Exceção que não encontrou uma entidade com os parametros de uma pesquisa
 * 
 * @author rodrigo.hiemer
 */
public class ConversaoObjetoException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2056014859030971535L;

	/**
	 * 
	 */

	public ConversaoObjetoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConversaoObjetoException(String message) {
		super(message);
	}

}
