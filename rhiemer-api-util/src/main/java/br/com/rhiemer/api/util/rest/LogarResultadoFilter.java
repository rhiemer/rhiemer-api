package br.com.rhiemer.api.util.rest;

import static br.com.rhiemer.api.util.constantes.ConstantesAPI.ENCONDING_PADRAO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.util.format.json.FormatFacotry;

public final class LogarResultadoFilter {
	
	private LogarResultadoFilter()
	{
		
	}
	
	public static String stringResultado(WriterInterceptorContext context,String contentType) throws IOException, WebApplicationException {
		
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

}
