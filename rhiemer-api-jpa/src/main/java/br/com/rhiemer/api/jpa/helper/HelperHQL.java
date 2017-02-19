package br.com.rhiemer.api.jpa.helper;

import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.HelperMessage;
import br.com.rhiemer.api.util.lambda.optinal.HelperOptional;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public final class HelperHQL {

	private static final String DELETE_FROM = "Delete from {} {}";
	private static final String WHERE = "WHERE";
	private static final String AND = " and {} ";

	private HelperHQL() {

	}

	public static String parameterKeyStr(String atributo) {
		return HelperMessage.formatMessage("{} =:{}", atributo, atributo);
	}

	public static <T extends PojoKeyAbstract> String sqlId(T entidade) {

		StringBuilder sb = new StringBuilder();
		entidade.getPrimaryKeyList().forEach(t -> sb.append(Helper.isBlank(sb.toString()) ? parameterKeyStr(t)
				: HelperMessage.formatMessage(AND, parameterKeyStr(t))));
		return sb.toString();

	}

	public static <T extends PojoKeyAbstract> String sqlWhereId(T entidade) {
		return HelperOptional.ofIsBlank(sqlId(entidade)).map(t -> WHERE.concat(" ").concat(t)).get();
	}

	public static <T extends PojoKeyAbstract> String sqlDelete(T entidade) {
		return HelperOptional.ofIsBlank(sqlWhereId(entidade))
				.map(t -> HelperMessage.formatMessage(DELETE_FROM, entidade.getClass().getName(), t)).get();
	}

}
