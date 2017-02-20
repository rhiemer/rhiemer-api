package br.com.rhiemer.api.rest.filter.log;

import static br.com.rhiemer.api.util.constantes.ConstantesAPI.ENCONDING_PADRAO;
import static br.com.rhiemer.api.util.helper.DatetimeUtils.HUMAN_DATE_TIME_FORMAT_MLS;
import static br.com.rhiemer.api.util.helper.DatetimeUtils.HUMAN_TIME_FORMAT_MLS;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.util.annotations.app.LogApp;
import br.com.rhiemer.api.util.format.json.FormatFacotry;
import br.com.rhiemer.api.util.helper.DatetimeUtils;
import br.com.rhiemer.api.util.log.LogAplicacao;
import br.com.rhiemer.api.util.rest.LogarResultadoFilter;

@Provider
@PreMatching
public class RequestLogFilterServer implements ContainerRequestFilter {

	@Inject
	@LogApp
	private LogAplicacao logAplicacao;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		String codigo = codigoLoggingRequest();
		if (codigo != null)
			headerCodigoLoggingRequestServer(requestContext, codigo);

		if (logAplicacao.isTraceEnabled()) {
			logar(requestContext);
		} else {
			logAplicacao.debug(
					String.format("Request Serviço: %s codigoLoggingRequestServer=%s codigoLoggingRequestClient=%s",
							requestContext.getUriInfo().getRequestUri().toURL().toExternalForm(), codigo,
							codigoLoggingRequestCliente(requestContext)));
		}

	}

	protected String codigoLoggingRequest() {
		return UUID.randomUUID().toString();
	}

	protected void headerCodigoLoggingRequestServer(ContainerRequestContext requestContext, String codigo) {
		requestContext.getHeaders().put("codigoLoggingRequestServer", Arrays.asList(new String[] { codigo }));
	}

	protected String codigoLoggingRequestCliente(ContainerRequestContext requestContext) {
		return requestContext.getHeaderString("codigoLoggingRequestClient");
	}

	protected void logar(ContainerRequestContext requestContext) throws IOException {
		StringBuilder sb = new StringBuilder();
		String contentType = null;
		Date dateRequestIni = new Date();
		String dateRequestIniStr = new SimpleDateFormat(HUMAN_DATE_TIME_FORMAT_MLS).format(dateRequestIni);

		requestContext.getHeaders().put("dataLoggingServerRequest", Arrays.asList(new String[] { dateRequestIniStr }));

		sb.append("\n------------------------------------\n");
		sb.append("RequestLogFilterServer....\n");
		sb.append("------------------------------------\n");
		sb.append("url: " + requestContext.getUriInfo() == null ? ""
				: requestContext.getUriInfo().getRequestUri().toURL().toExternalForm() + "\n");
		sb.append("method: " + requestContext.getMethod() + "\n");
		sb.append("headers:\n");

		for (Entry<String, List<String>> header : requestContext.getHeaders().entrySet()) {
			sb.append("  " + header.getKey() + ":  ");
			for (int i = 0; i < header.getValue().size(); i++) {
				if (header.getKey().equals("Content-Type"))
					contentType = header.getValue().get(i).toString();
				sb.append(header.getValue().get(i) + (i < header.getValue().size() - 1 ? ", " : ""));
			}
			sb.append("\n");
		}

		if (requestContext.getMediaType() != null)
			sb.append("media-type: " + requestContext.getMediaType().getType() + "\n");

		Optional.ofNullable(LogarResultadoFilter.logPerfomanceRest(requestContext))
				.ifPresent(t -> sb.append(t));

		InputStream stream = null;
		if (!requestContext.getEntityStream().markSupported()) {
			stream = new BufferedInputStream(requestContext.getEntityStream());
			requestContext.setEntityStream(stream);
		} else {
			stream = requestContext.getEntityStream();
		}

		stream.mark(Integer.MAX_VALUE);

		String strObj = IOUtils.toString(stream, ENCONDING_PADRAO);
		String strObjFormatado = "";
		if (!StringUtils.isBlank(strObj)) {
			strObjFormatado = FormatFacotry.builder((contentType == null ? MediaType.APPLICATION_JSON : contentType))
					.formatar(strObj);
		}

		sb.append(strObjFormatado + "\n");
		stream.reset();

		logAplicacao.trace(sb.toString());

	}

}
