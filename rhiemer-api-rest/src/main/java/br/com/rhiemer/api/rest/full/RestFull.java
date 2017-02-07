package br.com.rhiemer.api.rest.full;

import static br.com.rhiemer.api.util.helper.ConstantesAPI.MEDIA_REST_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.rhiemer.api.util.annotations.rest.RestClientMetodoClasse;
import br.com.rhiemer.api.util.annotations.rest.RestClientPoxyMetodoInterceptor;

@RestClientPoxyMetodoInterceptor(RestFullMetodoInterceptor.class)
public interface RestFull {

	@GET
	@Produces(MEDIA_REST_JSON)
	@Consumes(MEDIA_REST_JSON)
	@Path("/{entity}")
	Object find(@PathParam("entity") @RestClientMetodoClasse String entity);

	@GET
	@Produces(MEDIA_REST_JSON)
	@Consumes(MEDIA_REST_JSON)
	@Path("/{entity}/{id}")
	Object find(@PathParam("entity") @RestClientMetodoClasse String entity, @PathParam("id") String id);

	@POST
	@Produces(MEDIA_REST_JSON)
	@Consumes(MEDIA_REST_JSON)
	@Path("/{entity}")
	Object add(@PathParam("entity") @RestClientMetodoClasse String entity, Object json);

	@PUT
	@Produces(MEDIA_REST_JSON)
	@Consumes(MEDIA_REST_JSON)
	@Path("/{entity}")
	Object update(@PathParam("entity") @RestClientMetodoClasse String entity, Object json);

	@DELETE
	@Produces(MEDIA_REST_JSON)
	@Consumes(MEDIA_REST_JSON)
	@Path("/{entity}/{id}")
	void delete(@PathParam("entity") @RestClientMetodoClasse String entity, @PathParam("id") String id);

}