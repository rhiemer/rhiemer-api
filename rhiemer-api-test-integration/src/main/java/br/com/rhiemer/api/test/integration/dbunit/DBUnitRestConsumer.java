package br.com.rhiemer.api.test.integration.dbunit;

import static br.com.rhiemer.api.test.integration.dbunit.DBUnitRest.PATH_DB_UNIT_DELETE;
import static br.com.rhiemer.api.test.integration.dbunit.DBUnitRest.PATH_DB_UNIT_INSERT;

import br.com.rhiemer.api.rest.client.response.RestWSUtils;
import br.com.rhiemer.api.util.helper.Helper;

public class DBUnitRestConsumer {

	public static void createRemoteDataset(String context, String dataset) {
		String url = context.concat(PATH_DB_UNIT_INSERT).concat(Helper.enconder(dataset));
		RestWSUtils.requestGET(url);
	}

	public static void deleteRemoteDataset(String context, String dataset) {
		String url = context.concat(PATH_DB_UNIT_DELETE).concat(Helper.enconder(dataset));
		RestWSUtils.requestGET(url);
	}

}
