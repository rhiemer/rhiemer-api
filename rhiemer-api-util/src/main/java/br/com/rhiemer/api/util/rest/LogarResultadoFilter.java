package br.com.rhiemer.api.util.rest;

import static br.com.rhiemer.api.util.constantes.ConstantesAPI.ENCONDING_PADRAO;
import static br.com.rhiemer.api.util.helper.DatetimeUtils.HUMAN_DATE_TIME_FORMAT_MLS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.util.format.json.FormatFacotry;
import br.com.rhiemer.api.util.helper.DatetimeUtils;
import br.com.rhiemer.api.util.lambda.optinal.OptionalConsumer;

public final class LogarResultadoFilter {

	private LogarResultadoFilter() {

	}

	public static String stringResultado(WriterInterceptorContext context, String contentType)
			throws IOException, WebApplicationException {

		StringBuilder sb = new StringBuilder();
		OutputStream originalStream = context.getOutputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		context.setOutputStream(baos);
		try {
			context.proceed();
		} finally {
			String strObj = baos.toString(ENCONDING_PADRAO);
			baos.writeTo(originalStream);
			baos.close();
			context.setOutputStream(originalStream);
			String strObjFormatado = "";
			if (!StringUtils.isBlank(strObj)) {
				strObjFormatado = FormatFacotry
						.builder((contentType == null ? MediaType.APPLICATION_JSON : contentType)).formatar(strObj);
			}
			sb.append(strObjFormatado + "\n");

		}
		return sb.toString();

	}

	private static String inokeHeaderString(Object objeto, String header) {
		if (objeto == null)
			return null;
		Object value = null;
		try {
			value = objeto.getClass().getMethod("getHeaderString", String.class).invoke(objeto,
					new Object[] { header });
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new RuntimeException(e instanceof InvocationTargetException ? e.getCause() : e);
		}
		return value == null ? null : value.toString();

	}

	private static String getHeaderString(String header, Object requestContext, Object responseContext) {
		return Optional.ofNullable(inokeHeaderString(requestContext, header))
				.orElse(inokeHeaderString(responseContext, header));

	}

	private static String diffStrHeaderString(String titulo, String header1, String header2, Object requestContext,
			Object responseContext) {

		String value1 = getHeaderString(header1, requestContext, responseContext);
		String value2 = getHeaderString(header2, requestContext, responseContext);

		if (!StringUtils.isBlank(value1) && !StringUtils.isBlank(value2))
			return String.format("%s: %s\n", titulo,
					DatetimeUtils.diffDatesStrFormat(value2, value1, HUMAN_DATE_TIME_FORMAT_MLS));
		else
			return null;

	}

	public static String logPerfomanceRest(Object requestContext) {
		return logPerfomanceRest(requestContext, null);
	}

	public static String logPerfomanceRest(Object requestContext, Object responseContext) {
		StringBuilder sb = new StringBuilder();
		Optional.ofNullable(diffStrHeaderString("perfomance_client_request_server_request", "dataLoggingClientRequest",
				"dataLoggingServerRequest", requestContext, responseContext)).ifPresent(t -> sb.append(t));
		;
		Optional.ofNullable(diffStrHeaderString("perfomance_server_request_server_response", "dataLoggingServerRequest",
				"dataLoggingServerResponse", requestContext, responseContext)).ifPresent(t -> sb.append(t));
		;
		Optional.ofNullable(diffStrHeaderString("perfomance_client_request_server_response", "dataLoggingClientRequest",
				"dataLoggingServerResponse", requestContext, responseContext)).ifPresent(t -> sb.append(t));
		;
		Optional.ofNullable(diffStrHeaderString("perfomance_server_response_client_response",
				"dataLoggingServerResponse", "dataLoggingClientResponse", requestContext, responseContext))
				.ifPresent(t -> sb.append(t));
		;
		Optional.ofNullable(diffStrHeaderString("perfomance_server_request_client_response", "dataLoggingServerRequest",
				"dataLoggingClientResponse", requestContext, responseContext)).ifPresent(t -> sb.append(t));
		;
		Optional.ofNullable(diffStrHeaderString("perfomance_client_request_client_response", "dataLoggingClientRequest",
				"dataLoggingClientResponse", requestContext, responseContext)).ifPresent(t -> sb.append(t));
		;
		return StringUtils.isBlank(sb.toString()) ? null : sb.toString();

	}

}
