package br.com.rhiemer.api.rest.exception.mapper;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import br.com.rhiemer.api.rest.helper.JsonResponse;
import br.com.rhiemer.api.util.annotations.LogApp;
import br.com.rhiemer.api.util.log.LogAplicacao;

/**
 * Tratador de exceções quando o JSON enviado possui um campo não reconhecido
 * pelo formato estabelecido pela ANCINE
 * 
 * 
 */

@Provider
public class UnrecognizedPropertyExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {

	@Inject
	@LogApp
	private LogAplicacao logger;

	@Override
	public Response toResponse(UnrecognizedPropertyException exception) {
		
		
		String mensagem = "'"
				+ exception.getPropertyName()
				+ "' não é um atributo conhecido.";
		
		logger.error(mensagem, exception);

		return JsonResponse.respondeUTF8(Response.Status.BAD_REQUEST,mensagem);
	}

}
