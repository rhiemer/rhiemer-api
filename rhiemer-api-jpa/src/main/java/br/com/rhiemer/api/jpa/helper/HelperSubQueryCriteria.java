package br.com.rhiemer.api.jpa.helper;

import javax.persistence.Entity;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import br.com.rhiemer.api.jpa.criteria.subquery.SubQueryRetornoDTO;
import br.com.rhiemer.api.util.helper.HelperFindEntityClass;

public final class HelperSubQueryCriteria {

	private HelperSubQueryCriteria() {
	}

	public static SubQueryRetornoDTO subQuery(AbstractQuery queryRoot, Class<?> classe) {
		if (classe == null)
			return null;
		Subquery subquery = queryRoot.subquery(classe);
		Root subRootEntity = subquery.from(classe);
		subquery.select(subRootEntity);
		return new SubQueryRetornoDTO(subquery, subRootEntity);
	}

	public static SubQueryRetornoDTO subQuery(AbstractQuery queryRoot, String className) {
		return subQuery(queryRoot, HelperFindEntityClass.findEntityByString(className, Entity.class));
	}

}
