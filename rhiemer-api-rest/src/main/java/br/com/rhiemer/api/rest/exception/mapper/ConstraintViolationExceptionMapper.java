package br.com.rhiemer.api.rest.exception.mapper;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.rhiemer.api.rest.helper.ErroRetornoListaDTO;
import br.com.rhiemer.api.rest.helper.ErroRetornoListaMapDTO;
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
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Inject
	@LogApp
	private LogAplicacao logger;

	@Override
	public Response toResponse(ConstraintViolationException exception) {

		logger.error("ConstraintViolationException: " + exception.getMessage(), exception);

		ErroRetornoListaDTO erroRetornoDTO = new ErroRetornoListaDTO("ConstraintViolationException",
				"Problemas de validação na entidade enviada.", "Atributos inválidos",
				AbstractValidacaoException.listaDto(exception.getConstraintViolations()));

		return JsonResponse.respondeUTF8(Response.Status.BAD_REQUEST, erroRetornoDTO);

	}

}
