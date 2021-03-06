package br.com.rhiemer.api.rest.exception.mapper;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.rhiemer.api.rest.helper.JsonResponse;
import br.com.rhiemer.api.util.annotations.app.LogApp;
import br.com.rhiemer.api.util.log.LogAplicacao;

/**
 * Provider de exeção de uma entidade não encontrada numa pesquisa
 * 
 * @author rodrigo.hiemer
 *
 */
@Provider
public class NotFoundServerExceptionMapper implements
		ExceptionMapper<NotFoundException> {

	@Inject	
	@LogApp
	private LogAplicacao logger;

	@Override
	public Response toResponse(NotFoundException exception) {

		logger.error("NotFoundExceptionServer: " + exception.getMessage(), exception);

		return JsonResponse.respondeHTTPStatusNotFound(exception.getMessage());
	}

}
