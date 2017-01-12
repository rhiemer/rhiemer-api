package br.com.rhiemer.api.util.format.json;

import javax.ws.rs.core.MediaType;

public class FormatFacotry {

	public static IFormat builder(String tipo) {

		if (tipo.startsWith(MediaType.APPLICATION_JSON)) {
			return new FormatJson();
		} else if (tipo.startsWith(MediaType.APPLICATION_XML)) {
			return new FormatXML();
		} else
			return new FormatNulo();

	}

}
