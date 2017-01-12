package br.com.rhiemer.api.util.pojo;

import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.util.annotations.ToString;
import br.com.rhiemer.api.util.helper.Helper;

public class PojoAbstract implements Pojo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5140596488259988227L;

	@Override
	public boolean equals(Object o) {

		if (o == null || !o.getClass().isInstance(this))
			return false;

		return compareTo((PojoAbstract) o) == 0;

	}

	protected String prefixoToString() {
		return null;
	}

	@Override
	public String toString() {

		String nomClasse = super.toString();
		String toString = prefixoToString();

		String[] fields = Helper.allStrFromFields(this.getClass(),
				new Class[] { ToString.class }, true);
		if (fields != null) {
			for (String field : fields) {

				Object valor = Helper.getValueMethodOrField(this, field);
				String strValor = null;
				if (valor != null)
					strValor = valor.toString();
				else
					strValor = "null";

				String titulo = (String) Helper.valueAnnotationOfField(this,
						field, ToString.class, "value");
				if (titulo == null || "".equals(titulo))
					titulo = field;

				toString += (toString.equals("") ? "" : ",")
						+ String.format("%s=%s", titulo, strValor);

			}

		}

		if (!StringUtils.isBlank(toString)) {
			return String.format("%s[%s]", nomClasse, toString);
		} else
			return nomClasse;

	}

	protected String[] noMethodosCopia() {
		return null;
	}

	protected void posCopia(Object copia) {

	}

	public Object clone() {
		Object copia = Helper.clone(this);
		posCopia(copia);
		return copia;

	}

	public Object copia() {
		Object copia;
		try {
			copia = this.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		Helper.copyObject(this, copia, false, noMethodosCopia());
		posCopia(copia);
		return copia;
	}

	@Override
	public int compareTo(Pojo o) {
		return PojoAbstract.super.equals(o) ? 0 : -1;
	}

}
