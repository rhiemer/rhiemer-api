package br.com.rhiemer.api.rest.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rhiemer.api.rest.helper.JsonResponse;

import com.fasterxml.jackson.databind.JsonMappingException;



/**
 * Provider de problemas de mapeamento no JSON
 * @author rodrigo.hiemer
 *
 */
@Provider
public class JsonMappingExceptionMapper implements
		ExceptionMapper<JsonMappingException> {

	private static Logger logger = LoggerFactory
			.getLogger(JsonMappingExceptionMapper.class);

	@Override
	public Response toResponse(JsonMappingException exception) {
		logger.error("JsonMappingException", exception);
		if (exception.getMessage().startsWith(
				"Can not construct instance of")) {

			String msg = exception.getMessage().replace("\n", " ");

			String regExp = "^Can not construct instance of .+ from String value '(.*)': not a valid .+\\(through reference chain:(.*)\\)";

			if (msg.matches(regExp)) {
				msg = msg.replaceAll(regExp,
						"Não é possível instanciar o valor '$1' em $2");
			} else {
				msg = "Os dados enviados não correspondem ao formato JSON especificado.";
			}
			return JsonResponse.respondeUTF8(Response.Status.BAD_REQUEST,
					String.format("%s Linha: '%s', Coluna: '%s'.", msg,
							exception.getLocation().getLineNr(), exception
									.getLocation().getColumnNr()));
		} else if (exception.getCause() != null
				&& exception.getCause().getClass().getName()
						.endsWith("LazyInitializationException")) {
			return JsonResponse.respondeUTF8(
					Response.Status.INTERNAL_SERVER_ERROR,
					"Erro de processamento interno: LazyInitialization");
		} else {
			return JsonResponse.respondeUTF8(Response.Status.BAD_REQUEST,
					"O JSON enviado apresentou problemas ao ser carregado");
		}
	}

}
