package br.com.rhiemer.api.rest.client.log;

import static br.com.rhiemer.api.util.helper.DatetimeUtils.HUMAN_DATE_TIME_FORMAT_MLS;
import static br.com.rhiemer.api.util.helper.DatetimeUtils.HUMAN_TIME_FORMAT_MLS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rhiemer.api.util.helper.DatetimeUtils;
import br.com.rhiemer.api.util.rest.LogarResultadoFilter;

/**
 * Classe para logar informações de request de envios de RESTWebServices
 * 
 * @author rodrigo.hiemer
 *
 */
public class LoggingRequestFilter implements ClientRequestFilter, WriterInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LoggingRequestFilter.class);

	protected String codigoLoggingRequest(ClientRequestContext requestContext) {
		return UUID.randomUUID().toString();
	}

	protected void headerCodigoLoggingRequestServer(ClientRequestContext requestContext, String codigo) {
		requestContext.getHeaders().put("codigoLoggingRequestClient", Arrays.asList(new String[] { codigo }));

	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {

		String codigo = codigoLoggingRequest(requestContext);
		if (codigo != null)
			headerCodigoLoggingRequestServer(requestContext, codigo);
		if (PrintLogger.isTrace(logger))
			logar(requestContext);
		else if (PrintLogger.verificarLog(logger))
			PrintLogger.print(logger, String.format("Request Serviço Client: %s  codigoLoggingRequestClient=%s",
					requestContext.getUri().toURL().toExternalForm(), codigo));

	}

	protected void logar(ClientRequestContext requestContext) throws IOException {

		requestContext.getHeaders().put("dataLoggingClientRequest",
				Arrays.asList(new String[] { new SimpleDateFormat(HUMAN_DATE_TIME_FORMAT_MLS).format(new Date()) }));

		StringBuilder sb = new StringBuilder();
		sb.append("\n------------------------------------\n");
		sb.append("LoggingRequestFilterClient....\n");
		sb.append("------------------------------------\n");
		sb.append("url: " + requestContext.getUri() == null ? ""
				: requestContext.getUri().toURL().toExternalForm() + "\n");
		sb.append("entity: " + requestContext.getEntity() + "\n");
		sb.append("method: " + requestContext.getMethod() + "\n");
		sb.append("headers:\n");

		for (Entry<String, List<Object>> header : requestContext.getHeaders().entrySet()) {
			sb.append("  " + header.getKey() + ":  ");
			for (int i = 0; i < header.getValue().size(); i++) {

				sb.append(header.getValue().get(i) + (i < header.getValue().size() - 1 ? ", " : ""));
			}
			sb.append("\n");
		}

		if (requestContext.getMediaType() != null)
			sb.append("media-type: " + requestContext.getMediaType().getType());

		PrintLogger.print(logger, sb.toString());

	}

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
		if (!PrintLogger.isTrace(logger))
			return;

		StringBuilder sb = new StringBuilder();

		String codigo = "";
		String contentType = null;
		for (Entry<String, List<Object>> header : context.getHeaders().entrySet()) {
			for (int i = 0; i < header.getValue().size(); i++) {
				if (header.getKey().equals("Content-Type"))
					contentType = header.getValue().get(i).toString();
				if (header.getKey().equals("codigoLoggingRequestClient"))
					codigo = header.getValue().get(i).toString();
			}
		}
		sb.append(String.format("Resultado do codigoLoggingRequestClient:%s\n", codigo));
		String resultado = LogarResultadoFilter.stringResultado(context, contentType);
		sb.append(resultado);
		PrintLogger.print(logger, sb.toString() + "\n");

	}

}
