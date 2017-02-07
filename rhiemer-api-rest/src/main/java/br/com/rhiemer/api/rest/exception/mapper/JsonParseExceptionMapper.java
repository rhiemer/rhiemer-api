package br.com.rhiemer.api.rest.exception.mapper;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParseException;

import br.com.rhiemer.api.rest.helper.JsonResponse;
import br.com.rhiemer.api.util.annotations.app.LogApp;
import br.com.rhiemer.api.util.log.LogAplicacao;

/**
 * Tratador de exceções quando for submetido um conteúdo não JSON
 * 
 * 
 * 
 */
@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {
	
	@Inject
	@LogApp
	private LogAplicacao logger;

	@Override
	public Response toResponse(JsonParseException exception) {
		
		String mensagem = String.format("Os dados enviados não correspondem ao formato JSON especificado. Linha: '%s', Coluna: '%s'.",
				exception.getLocation().getLineNr(), exception
				.getLocation().getColumnNr());
		
		logger.error(mensagem, exception);
		
		return JsonResponse
				.respondeUTF8(
						Response.Status.BAD_REQUEST,
						mensagem);
	}

}
