package br.com.rhiemer.api.util.constantes;

import javax.ws.rs.core.MediaType;

import br.com.rhiemer.api.util.annotations.entity.Chave;

public interface ConstantesAPI {

	static final String BASE_PACKAGE = "br.com.rhiemer";
	static final String BASE_PACKAGE_API = BASE_PACKAGE + ".api";
	static final String MEDIA_REST_JSON = MediaType.APPLICATION_JSON + ";charset=utf-8";
	static final String MEDIA_REST_XML = MediaType.APPLICATION_XML + ";charset=utf-8";
	static final String DOT_FIELD = "[.]";
	static final String ENCONDING_PADRAO = "UTF-8";
	static final Class<?>[] ANNOTATIOSID = new Class[] { javax.persistence.Id.class, javax.persistence.EmbeddedId.class,
			Chave.class };

	static final String BOOLEAN_SIM = "S";
	static final String BOOLEAN_NAO = "N";
	static final String PASTAS_PROPRIEDADE_APLICACOES = "propriedades";
	static final String[] DATES_FORMAT = { "dd/MM/yyyy", "dd/MM/yyyyHH:mm:ss", "dd/MM/yyyyHH:mm", "dd/MM/yyyyHH",
			"dd/MM/yyyyHH", "dd/MM/yyyyHH:mm:ss.S" };

}
