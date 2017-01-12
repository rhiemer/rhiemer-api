package br.com.rhiemer.api.util.exception;

/**
 * Exceção pai de todas as exceções de sistema.
 * 
 * @author <a href="mailto:asouza@redhat.com">Ângelo Galvão</a>
 */
public class APIConfigurationException extends APISystemException {

	private static final long serialVersionUID = -4131198649164320418L;

	public APIConfigurationException(String message) {
		super("ERRO: Erro de configuação: " + message);
	}
}
