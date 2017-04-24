package br.com.rhiemer.api.jpa.enums;

import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.HelperMessage;

public enum EnumLike {

	INICIO("%{}"), INICO_FIM("%{}%"), FIM("{}%"), SEM_FORMATCAO(null), AUTO(null);

	private String format;

	private EnumLike(String format) {
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

	public String format(String value) {
		if (Helper.isBlank(this.getFormat()))
			return value;
		else
			return HelperMessage.formatMessage(this.getFormat(), value);
	}

}
