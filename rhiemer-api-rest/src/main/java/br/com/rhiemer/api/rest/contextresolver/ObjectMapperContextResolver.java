package br.com.rhiemer.api.rest.contextresolver;

import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import br.com.rhiemer.api.util.helper.DatetimeUtils;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

	final ObjectMapper mapper = new ObjectMapper();

	public ObjectMapperContextResolver() {
		Hibernate4Module hbm = new Hibernate4Module();
		mapper.registerModule(hbm);
		mapper.setDateFormat(new SimpleDateFormat(DatetimeUtils.HUMAN_DATE_FORMAT));
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);		
		mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);

	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

}
