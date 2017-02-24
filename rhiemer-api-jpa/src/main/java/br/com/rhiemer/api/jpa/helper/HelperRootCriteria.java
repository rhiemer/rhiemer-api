package br.com.rhiemer.api.jpa.helper;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.SingularAttribute;

import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.lambda.optinal.HelperOptional;

public class HelperRootCriteria {

	private HelperRootCriteria() {

	}

	public static Path getSingularAttribute(Path from, SingularAttribute... attributes) {
		List<SingularAttribute> listAttributes = Helper.convertArgs(attributes);
		final Path[] path = new Path[1];

		listAttributes.forEach(new Consumer<SingularAttribute>() {

			@Override
			public void accept(SingularAttribute t) {
				if (path[0] == null)
					path[0] = from.get(t);
				else
					path[0] = path[0].get(t);
			}
		});
		return path[0];

	}

}
