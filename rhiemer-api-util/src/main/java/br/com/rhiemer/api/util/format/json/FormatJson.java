package br.com.rhiemer.api.util.format.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FormatJson implements IFormat {

	@Override
	public String formatar(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Object jsonObj = null;
		String strObjFormatado = null;
		try {
			jsonObj = mapper.readValue(json, Object.class);
			strObjFormatado = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
		} catch (Exception e) {
			return json;
		}

		return strObjFormatado;
	}

}
