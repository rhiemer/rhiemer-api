package br.com.rhiemer.api.jpa.helper;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import br.com.rhiemer.api.jpa.enums.EnumLike;
import br.com.rhiemer.api.util.helper.Helper;

public final class HelperLikeCriteria {

	private static final String REGEX_QUOTE_SPACE = "([^\"]\\S*|\".+?\")\\s*";
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

	public static String[] quebraStringLike(String str) {
		if (Helper.isBlank(str))
			return null;
		String[] strSplit = Arrays.stream(Helper.splitRegex(str, REGEX_QUOTE_SPACE))
				.filter(x -> Helper.isNotBlank(x.trim())).map(x -> x.trim()).map(x -> convertStrLike(x))
				.toArray(size -> new String[size]);
		return strSplit;

	}

	public static String convertStrLike(String str) {
		String _str = str.trim().replace("\"", "");
		if (_str.startsWith("=") && _str.length() != 1) {
			return _str.substring(1);
		} else {
			return EnumLike.AUTO.format(_str);
		}

	}

}
