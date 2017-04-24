package br.com.rhiemer.api.jpa.helper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public final class HelperPredicateCriteria {

	private HelperPredicateCriteria() {

	}

	public static Predicate equalsCaseInsensitive(CriteriaBuilder builder,
			Expression exp, Object valor) {

		if (exp.getJavaType().isAssignableFrom(String.class))
			return builder.equal(builder.upper(exp), valor.toString()
					.toUpperCase());
		else
			return builder.equal(exp, valor);

	}

	public static Predicate iLikeCaseInsensitive(CriteriaBuilder builder,
			Expression<String> exp, String valor) {
		return builder
				.like(builder.upper(exp), "%" + valor.toUpperCase() + "%");
	}

	public static Predicate iLikeCaseSensitive(CriteriaBuilder builder,
			Expression<String> exp, String valor) {
		return builder.like(exp, "%" + valor + "%");
	}
	
	
	
	
	

}
