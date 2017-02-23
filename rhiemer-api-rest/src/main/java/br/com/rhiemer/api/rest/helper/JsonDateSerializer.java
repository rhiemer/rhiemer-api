package br.com.rhiemer.api.rest.helper;


import static br.com.rhiemer.api.util.helper.DateTimeUtils.MACHINE_DATE_FORMAT;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * Serialização do tipo {@code Date} para um formato JSON
 * 
 * @author rodrigo.hiemer
 * 
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		String formattedDate = new SimpleDateFormat(MACHINE_DATE_FORMAT).format(date);
		gen.writeString(formattedDate);
	}

}
