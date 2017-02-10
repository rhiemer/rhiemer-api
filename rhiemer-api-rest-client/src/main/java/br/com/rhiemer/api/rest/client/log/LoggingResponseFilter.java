package br.com.rhiemer.api.rest.client.log;

import static br.com.rhiemer.api.util.helper.DatetimeUtils.HUMAN_DATE_TIME_FORMAT_MLS;
import static br.com.rhiemer.api.util.helper.DatetimeUtils.HUMAN_TIME_FORMAT_MLS;
import static br.com.rhiemer.api.util.constantes.ConstantesAPI.ENCONDING_PADRAO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rhiemer.api.util.format.json.FormatFacotry;
import br.com.rhiemer.api.util.helper.DatetimeUtils;

/**
 * Classe para logar informações de response de envios de RESTWebServices
 * 
 * @author rodrigo.hiemer
 *
 */
public class LoggingResponseFilter implements ClientResponseFilter {

	private static Logger logger = LoggerFactory.getLogger(LoggingResponseFilter.class);

	protected String codigoLoggingRequestServer(ClientRequestContext requestContext,
			ClientResponseContext responseContext) {
		return requestContext.getHeaderString("codigoLoggingRequestServer");
	}

	protected String codigoLoggingRequestCliente(ClientRequestContext requestContext,
			ClientResponseContext responseContext) {
		return requestContext.getHeaderString("codigoLoggingRequestClient");
	}

	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {

		if (PrintLogger.isTrace(logger))
			logar(requestContext, responseContext);
		else if (PrintLogger.verificarLog(logger))
			PrintLogger.print(logger,
					String.format(
							"Request Serviço Client: %s  codigoLoggingRequestClient=%s codigoLoggingRequestServer=%s",
							codigoLoggingRequestCliente(requestContext, responseContext),
							codigoLoggingRequestServer(requestContext, responseContext)));

	}

	public void logar(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {

		StringBuilder sb = new StringBuilder();
		String codigo = codigoLoggingRequestCliente(requestContext, responseContext);
		String contentType = null;
		Date dateRequestFim = new Date();
		String dateRequestFimStr = new SimpleDateFormat(HUMAN_DATE_TIME_FORMAT_MLS).format(dateRequestFim);

		sb.append("\n------------------------------------\n");
		sb.append("LoggingResponseFilterClient....\n");
		sb.append("------------------------------------\n");
		sb.append("codigoLoggingRequestClient: " + codigo + "\n");
		sb.append("dataLoggingClientRequest: " + responseContext.getHeaderString("dataLoggingClientRequest") + "\n");

		sb.append("status: " + responseContext.getStatus() + "\n");
		sb.append("date: " + responseContext.getDate() + "\n");

		sb.append("last-modified: " + responseContext.getLastModified() + "\n");
		sb.append("location: " + responseContext.getLocation() + "\n");
		sb.append("headers:" + "\n");

		// dados do header está dando uma exceção de não achar o método
		// getHeaders quando o response é da classe ApacheHttpClient4Engine
		try {
			for (Entry<String, List<String>> header : responseContext.getHeaders().entrySet()) {

				sb.append("  " + header.getKey() + ":  ");
				for (int i = 0; i < header.getValue().size(); i++) {
					if (header.getKey().equals("Content-Type"))
						contentType = header.getValue().get(i).toString();
					sb.append(header.getValue().get(i) + (i < header.getValue().size() - 1 ? ", " : ""));
				}
				sb.append("\n");
			}

		} catch (Throwable e) {
		}
		sb.append("dataLoggingClientResponse: " + dateRequestFimStr + "\n");

		if (!StringUtils.isBlank(responseContext.getHeaderString("dataLoggingServerRequest"))
				&& !StringUtils.isBlank(responseContext.getHeaderString("dataLoggingServerResponse")))
			sb.append("perfomance_server: "
					+ DatetimeUtils.diffDatesStrFormat(responseContext.getHeaderString("dataLoggingServerResponse"),
							responseContext.getHeaderString("dataLoggingServerRequest"), HUMAN_DATE_TIME_FORMAT_MLS)
					+ "\n");
		if (!StringUtils.isBlank(requestContext.getHeaderString("dataLoggingClientRequest")))
			sb.append("perfomance_client: "
					+ DatetimeUtils.diffDatesStrFormat(dateRequestFimStr,
							requestContext.getHeaderString("dataLoggingClientRequest"), HUMAN_DATE_TIME_FORMAT_MLS)
					+ "\n");

		if (responseContext.getMediaType() != null)
			sb.append("media-type: " + responseContext.getMediaType().getType() + "\n");

		if (responseContext.getEntityStream() != null) {
			// cria um novo EntityStream para logar o resultado do serviço
			sb.append("Response:\n");
			InputStream stream = null;
			if (!responseContext.getEntityStream().markSupported()) {
				stream = new BufferedInputStream(responseContext.getEntityStream());
				responseContext.setEntityStream(stream);
			} else {
				stream = responseContext.getEntityStream();
			}

			stream.mark(Integer.MAX_VALUE);

			String strObj = IOUtils.toString(stream, ENCONDING_PADRAO);
			String strObjFormatado = "";

			if (!StringUtils.isBlank(strObj)) {
				strObjFormatado = FormatFacotry
						.builder((contentType == null ? MediaType.APPLICATION_JSON : contentType)).formatar(strObj);
			}

			sb.append(strObjFormatado + "\n");
			stream.reset();

			PrintLogger.print(logger, sb.toString() + "\n");

		}

	}

}
