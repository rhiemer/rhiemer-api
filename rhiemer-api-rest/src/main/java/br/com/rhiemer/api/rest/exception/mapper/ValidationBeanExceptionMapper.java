package br.com.rhiemer.api.rest.exception.mapper;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.rhiemer.api.rest.helper.ErroRetornoDTO;
import br.com.rhiemer.api.rest.helper.JsonResponse;
import br.com.rhiemer.api.util.annotations.app.LogApp;
import br.com.rhiemer.api.util.exception.AbstractValidacaoException;
import br.com.rhiemer.api.util.log.LogAplicacao;

/**
 * Tratamento para exceções de validações de beans
 * 
 * @author rodrigo.hiemer
 *
 */
@Provider
public class ValidationBeanExceptionMapper implements
		ExceptionMapper<AbstractValidacaoException> {

	@Inject
	@LogApp
	private LogAplicacao logger;

	@Override
	public Response toResponse(AbstractValidacaoException exception) {

		logger.error("ValidacaoException: " + exception.getMessage(), exception);

		ErroRetornoDTO erroRetornoDTO = new ErroRetornoDTO(
				"ValidacaoException",
				"Problemas de validação na entidade enviada.",
				exception.getMessage());

		return JsonResponse.respondeUTF8(Response.Status.BAD_REQUEST,
				erroRetornoDTO);

	}

}
