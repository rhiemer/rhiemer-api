package br.com.rhiemer.api.test.integration.dbunit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.com.rhiemer.api.jpa.dbunit.DBUnitOperations;

public class DBUnitRest {

	public static final String PATH_DB_UNIT = "dbunit/";
	public static final String PATH_DB_UNIT_INSERT = PATH_DB_UNIT.concat("create/");
	public static final String PATH_DB_UNIT_DELETE = PATH_DB_UNIT.concat("delete/");

	@GET
	@Path("dbunit/create/{dataset}")
	public Response createDataset(@PathParam("dataset") String dataset) {
		DBUnitOperations dbUnitOperations = DBUnitOperations.createBuilder().builder();
		dbUnitOperations.cleanInsertDataset(dataset);
		return Response.ok().build();
	}

	@GET
	@Path("dbunit/create/{dataset}/{classe}")
	public Response createDataset(@PathParam("dataset") String dataset, @PathParam("classe") String classe) {
		DBUnitOperations dbUnitOperations = DBUnitOperations.createBuilder().setClassName(classe).builder();
		dbUnitOperations.cleanInsertDataset(dataset);
		return Response.ok().build();
	}

	@GET
	@Path("dbunit/delete/{dataset}")
	public Response deleteDataset(@PathParam("dataset") String dataset) {
		DBUnitOperations dbUnitOperations = DBUnitOperations.createBuilder().builder();
		dbUnitOperations.deleteAllDataset(dataset);
		return Response.ok().build();
	}

	@GET
	@Path("dbunit/delete/{dataset}/{classe}")
	public Response deleteDataset(@PathParam("dataset") String dataset, @PathParam("classe") String classe) {
		DBUnitOperations dbUnitOperations = DBUnitOperations.createBuilder().setClassName(classe).builder();
		dbUnitOperations.deleteAllDataset(dataset);
		return Response.ok().build();
	}

}
