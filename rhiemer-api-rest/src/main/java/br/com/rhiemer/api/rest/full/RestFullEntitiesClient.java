package br.com.rhiemer.api.rest.full;

import javax.enterprise.context.ApplicationScoped;

import br.com.rhiemer.api.util.annotations.rest.RESTful;
import br.com.rhiemer.api.util.cdi.PackageEntitiesCdi;

@ApplicationScoped
public class RestFullEntitiesClient extends PackageEntitiesCdi<RESTful> {

	@Override
	protected String notFoundMessage() {
		return "Entidade %s não existe.";
	}

	

}
