package br.com.rhiemer.api.rest.contextresolver;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Provider
public class ObjectMapperContextResolverJPA implements ContextResolver<ObjectMapper> {

	 final ObjectMapper mapper = new ObjectMapper();

	    public ObjectMapperContextResolverJPA() {
	        mapper.registerModule(new Hibernate4Module());
	    }

	    @Override
	    public ObjectMapper getContext(Class<?> type) {
	        return mapper;
	    }  

}
