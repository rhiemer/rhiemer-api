package br.com.rhiemer.api.rest.full;

import static br.com.rhiemer.api.util.constantes.ConstantesAPI.MEDIA_REST_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.rhiemer.api.util.annotations.rest.RestClientMetodoClasse;
import br.com.rhiemer.api.util.annotations.rest.RestClientPoxyMetodoInterceptor;

@RestClientPoxyMetodoInterceptor(RestFullMetodoInterceptor.class)
public interface RestFullDelete {

	@DELETE
	@Produces(MEDIA_REST_JSON)
	@Consumes(MEDIA_REST_JSON)
	@Path("/{entity}/{id}")
	void delete(@PathParam("entity") @RestClientMetodoClasse String entity, @PathParam("id") String id);

}