package br.com.rhiemer.api.util.format.converter;

import javax.ws.rs.core.MediaType;

public class ConverterFacotry {

	public static IConverter converter(String tipo) {

		if (tipo.startsWith(MediaType.APPLICATION_JSON)) {
			return new ConverterJson();
		} else if (tipo.startsWith(MediaType.APPLICATION_XML)) {
			return new ConverterXML();
		} else
			return new ConverterNulo();

	}

}
