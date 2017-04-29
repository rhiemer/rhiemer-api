package br.com.rhiemer.api.jpa.helper;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;

import br.com.rhiemer.api.util.helper.Helper;

public final class HelperQueryCriteria {

	private HelperQueryCriteria() {
	}

	public static boolean isQueyDistinct(From root) {
		return root.getJoins().stream().filter(x -> Helper.isNotBlank(((Join) x).getAlias()))
				.map(x -> ((Join) x).getAlias())
				.filter(x -> HelperAtributeJPA.isAnnotationMappedList(root.getJavaType(), x.toString())).findFirst()
				.isPresent();

	}

	public static void setQueyDistinctJoin(CriteriaQuery query, From root) {
		boolean distinct = isQueyDistinct(root);
		if (distinct && !query.isDistinct())
			query.distinct(true);

	}

}
