package br.com.rhiemer.api.rest.helper;

import static br.com.rhiemer.api.util.helper.DateTimeUtils.MACHINE_DATE_FORMAT;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Ao gerar o objeto a partir de um JSON no webservice converter a string em
 * tipo date
 * 
 * @author rodrigo.hiemer
 *
 */
public class JsonDateParser extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);
		SimpleDateFormat sdf = new SimpleDateFormat(MACHINE_DATE_FORMAT);

		try {
			return sdf.parse(node.asText());
		} catch (ParseException e) {
			throw new RuntimeException(
					"Erro ao converter a data. As datas devem ser válidas e estar no formato yyyy-MM-dd");
		}
	}

}
