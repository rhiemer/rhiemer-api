package br.com.rhiemer.api.dbunit.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.com.rhiemer.api.dbunit.transactional.DBUnitTransactional;
import static br.com.rhiemer.api.dbunit.transactional.DBUnitTransactional.DELETE_DATA_SET;
import static br.com.rhiemer.api.dbunit.transactional.DBUnitTransactional.DELETE_INSERT_DATA_SET;

public class DBUnitRest {

	public static final String PATH_DB_UNIT = "dbunit/";
	public static final String PATH_DB_UNIT_INSERT = PATH_DB_UNIT.concat(DELETE_INSERT_DATA_SET).concat("/");
	public static final String PATH_DB_UNIT_DELETE = PATH_DB_UNIT.concat(DELETE_DATA_SET).concat("/");

	@Inject
	private DBUnitTransactional dbUnitTransactional;

	@GET
	@Path("dbunit/{operacao}/{dataset}")
	public Response createDataset(@PathParam("operacao") String operacao, @PathParam("dataset") String dataset) {
		dbUnitTransactional.operacaoDataset(operacao, dataset);
		return Response.ok().build();
	}

	@GET
	@Path("dbunit/{operacao}/{dataset}/{classe}")
	public Response createDataset(@PathParam("operacao") String operacao, @PathParam("dataset") String dataset,
			@PathParam("classe") String classe) {
		dbUnitTransactional.operacaoDataset(operacao,dataset, classe);
		return Response.ok().build();
	}

}
