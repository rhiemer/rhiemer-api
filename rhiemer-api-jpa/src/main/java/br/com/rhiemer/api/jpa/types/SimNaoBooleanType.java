package br.com.rhiemer.api.jpa.types;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.StringType;
import org.hibernate.type.YesNoType;

import br.com.rhiemer.api.jpa.enums.EnumSimNao;

public class SimNaoBooleanType extends YesNoType {

	private static final long serialVersionUID = 1541824592470500450L;
	
	public SimNaoBooleanType() {
		super();
	}

	@Override
	public String getName() {
		return "boolean_sim_nao";
	}

	@Override
	public String objectToSQLString(Boolean value, Dialect dialect)
			throws Exception {
		return StringType.INSTANCE.objectToSQLString(EnumSimNao
				.getByValorBooleano(value).getAbreviacao(), dialect);
	}

}
