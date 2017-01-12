package br.com.rhiemer.api.rest.exception.mapper;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.rhiemer.api.rest.helper.JsonResponse;
import br.com.rhiemer.api.util.annotations.LogApp;
import br.com.rhiemer.api.util.log.LogAplicacao;

/**
 * Tratamento para exceções não tratadas por outro provider
 * 
 * @author rodrigo.hiemer
 *
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<Exception> {

	@Inject
	@LogApp
	private LogAplicacao logger;

	@Inject
	private ExceptionMapperBean exceptionMapperBean;

	@Override
	public Response toResponse(Exception exception) {

		// Devido ao JAX-RS utilizar esse provider para todos as exceções, mesmo
		// com outras já mapeadas foi preciso desenvolver uma solução que
		// buscasse o provider correto
		// TO-DO: Testar sem essa funcionalidade na proxima versão do JAX-RS
		Response responseBean = exceptionMapperBean.toResponse(exception);
		if (responseBean != null)
			return responseBean;

		logger.error("Erro de processamento interno " + exception.getMessage(), exception);

		return JsonResponse.respondeHTTPStatusServerError(exception.getMessage());

	}

}
