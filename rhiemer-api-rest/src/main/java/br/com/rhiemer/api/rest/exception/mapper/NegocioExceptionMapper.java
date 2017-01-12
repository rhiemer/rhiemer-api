package br.com.rhiemer.api.rest.exception.mapper;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.rhiemer.api.rest.helper.ErroRetornoDTO;
import br.com.rhiemer.api.rest.helper.JsonResponse;
import br.com.rhiemer.api.util.annotations.LogApp;
import br.com.rhiemer.api.util.exception.NegocioException;
import br.com.rhiemer.api.util.log.LogAplicacao;

/**
 * Tratamento para exceções de validações de beans
 * 
 * @author rodrigo.hiemer
 *
 */
@Provider
public class NegocioExceptionMapper implements
		ExceptionMapper<NegocioException> {

	@Inject
	@LogApp
	private LogAplicacao logger;

	@Override
	public Response toResponse(NegocioException exception) {

		logger.error("NegocioException: " + exception.getMessage(), exception);

		ErroRetornoDTO erroRetornoDTO = new ErroRetornoDTO(
				"NegocioException",
				"Exeção de negócio da aplicação.",
				exception.getMessage());

		return JsonResponse.respondeUTF8(Response.Status.BAD_REQUEST,
				erroRetornoDTO);

	}

}
