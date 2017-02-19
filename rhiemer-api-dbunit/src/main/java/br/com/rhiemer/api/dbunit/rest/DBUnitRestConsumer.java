package br.com.rhiemer.api.dbunit.rest;

import br.com.rhiemer.api.rest.client.response.RestWSUtils;
import br.com.rhiemer.api.util.helper.Helper;
import static br.com.rhiemer.api.dbunit.rest.DBUnitRest.PATH_DB_UNIT;

public class DBUnitRestConsumer {

	public static void operacaoDataset(String context, String operacao, String dataset) {
		String url = context.concat(PATH_DB_UNIT).concat(operacao).concat("/").concat(Helper.enconder(dataset));
		RestWSUtils.requestGET(url);
	}

}
