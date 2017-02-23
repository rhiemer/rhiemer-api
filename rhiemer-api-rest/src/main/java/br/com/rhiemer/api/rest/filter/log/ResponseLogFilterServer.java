package br.com.rhiemer.api.rest.filter.log;

import static br.com.rhiemer.api.util.helper.DateTimeUtils.HUMAN_DATE_TIME_FORMAT_MLS;
import static br.com.rhiemer.api.util.helper.DateTimeUtils.HUMAN_TIME_FORMAT_MLS;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.util.annotations.app.LogApp;
import br.com.rhiemer.api.util.helper.DateTimeUtils;
import br.com.rhiemer.api.util.log.LogAplicacao;
import br.com.rhiemer.api.util.rest.LogarResultadoFilter;

@Provider
public class ResponseLogFilterServer implements ContainerResponseFilter, WriterInterceptor {

	@Inject
	@LogApp
	private LogAplicacao logAplicacao;

	private StringBuilder sb = new StringBuilder();

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		headerCodigoLoggingRequestServer(requestContext, responseContext);
		if (logAplicacao.isTraceEnabled()) {
			logar(requestContext, responseContext);
		} else {

			logAplicacao.debug(
					String.format("Response Serviço: %s codigoLoggingRequestServer=%s codigoLoggingRequestClient=%s",
							requestContext.getUriInfo().getRequestUri().toURL().toExternalForm(),
							codigoLoggingRequestServer(requestContext, responseContext),
							codigoLoggingRequestCliente(requestContext, responseContext)));
		}

	}

	protected void headerCodigoLoggingRequestServer(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) {
		String codigo = codigoLoggingRequestServer(requestContext, responseContext);
		if (StringUtils.isNotBlank(codigo))
			responseContext.getHeaders().put("codigoLoggingRequestServer", Arrays.asList(new String[] { codigo }));
		codigo = codigoLoggingRequestCliente(requestContext, responseContext);
		if (StringUtils.isNotBlank(codigo))
			responseContext.getHeaders().put("codigoLoggingRequestClient", Arrays.asList(new String[] { codigo }));
	}

	protected String codigoLoggingRequestServer(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) {
		return requestContext.getHeaderString("codigoLoggingRequestServer");
	}

	protected String codigoLoggingRequestCliente(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) {
		return requestContext.getHeaderString("codigoLoggingRequestClient");
	}

	protected void logar(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		Date dateRequestFim = new Date();
		String dateRequestFimStr = new SimpleDateFormat(HUMAN_DATE_TIME_FORMAT_MLS).format(dateRequestFim);
		responseContext.getHeaders().put("dataLoggingServerResponse",
				Arrays.asList(new String[] { dateRequestFimStr }));
		Optional.ofNullable(requestContext.getHeaderString("dataLoggingClientRequest")).ifPresent(
				t -> responseContext.getHeaders().put("dataLoggingClientRequest", Arrays.asList(new String[] { t })));
		Optional.ofNullable(requestContext.getHeaderString("dataLoggingServerRequest")).ifPresent(
				t -> responseContext.getHeaders().put("dataLoggingServerRequest", Arrays.asList(new String[] { t })));

		sb = new StringBuilder();

		sb.append("\n------------------------------------\n");
		sb.append("ResponseLogFilterServer....\n");
		sb.append("------------------------------------\n");
		sb.append("status: " + responseContext.getStatus() + "\n");
		sb.append("date: " + responseContext.getDate() + "\n");

		sb.append("last-modified: " + responseContext.getLastModified() + "\n");
		sb.append("location: " + responseContext.getLocation() + "\n");
		sb.append("Entity: " + responseContext.getEntity() + "\n");
		if (responseContext.getMediaType() != null) {
			sb.append("media-type: " + responseContext.getMediaType().getType() + "\n");
		}
		sb.append("headers:" + "\n");

		// dados do header está dando uma exceção de não achar o método
		// getHeaders quando o response é da classe ApacheHttpClient4Engine
		try {
			for (Entry<String, List<Object>> header : responseContext.getHeaders().entrySet()) {

				sb.append("  " + header.getKey() + ":  ");
				for (int i = 0; i < header.getValue().size(); i++) {

					sb.append(header.getValue().get(i) + (i < header.getValue().size() - 1 ? ", " : ""));
				}
				sb.append("\n");
			}

		} catch (Throwable e) {
		}

		Optional.ofNullable(LogarResultadoFilter.logPerfomanceRest(requestContext, responseContext))
				.ifPresent(t -> sb.append(t));

	}

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
		if (!logAplicacao.isTraceEnabled()) {
			return;

		}

		String contentType = null;
		for (Entry<String, List<Object>> header : context.getHeaders().entrySet()) {
			for (int i = 0; i < header.getValue().size(); i++) {
				if (header.getKey().equals("Content-Type"))
					contentType = header.getValue().get(i).toString();
			}
		}
		sb.append("Resultado:\n");
		String resultado = LogarResultadoFilter.stringResultado(context, contentType);
		sb.append(resultado + "\n");
		logAplicacao.trace(sb.toString());

	}
}
