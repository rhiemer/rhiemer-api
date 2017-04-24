package br.com.rhiemer.api.jpa.helper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.enums.EnumLike;

public final class HelperLikeCriteria {

	private static final String CHAR_CRITERIA = "%";

	private HelperLikeCriteria() {

	}

	public static Expression concatExpressionCriteria(CriteriaBuilder builder, Expression exp, EnumLike tipo) {

		switch (tipo) {
		case INICIO:
			return builder.concat(CHAR_CRITERIA, exp);
		case FIM:
			return builder.concat(exp, CHAR_CRITERIA);
		case INICO_FIM:
		case AUTO:
			return builder.concat(builder.concat(CHAR_CRITERIA, exp), CHAR_CRITERIA);
		default:
			return exp;
		}

	}

}
