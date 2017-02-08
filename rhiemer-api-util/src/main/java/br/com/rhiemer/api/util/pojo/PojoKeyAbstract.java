package br.com.rhiemer.api.util.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.rhiemer.api.util.helper.Helper;

public abstract class PojoKeyAbstract extends PojoAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2478964439043142234L;

	@Transient
	@JsonIgnore
	@XmlTransient
	private transient List<String> primaryKey;
	private final transient Map<String, Class<?>> primaryKeyTypes = new HashMap<>();

	@JsonIgnore
	@XmlTransient
	public List<String> getPrimaryKeyList() {
		if (primaryKey == null) {

			primaryKey = new ArrayList<>();

			primaryKeyTypes.putAll(Helper.getPrimaryKeyList(this.getClass()));
			for (Map.Entry<String, Class<?>> entry : primaryKeyTypes.entrySet()) {
				primaryKey.add(entry.getKey());
			}

		}
		return primaryKey;
	}

	public Map<String, Class<?>> primaryKeyTypes() {
		return this.primaryKeyTypes;
	}

	@Transient
	@JsonIgnore
	@XmlTransient
	public Object getPrimaryKey() {

		if (getPrimaryKeyList() == null || getPrimaryKeyList().size() == 0)
			return null;

		List<Object> lista = new ArrayList<>();

		for (String property : getPrimaryKeyList()) {
			Object result = Helper.getValueMethodOrField(this, property);
			lista.add(result);
		}

		if (lista.size() == 1)
			return lista.get(0);
		else
			return lista.toArray(new Object[] {});
	}

	@JsonIgnore
	@XmlTransient
	public void setPrimaryKey(PojoKeyAbstract p) {
		for (String property : getPrimaryKeyList()) {
			Object value = Helper.getValueMethodOrField(p, property);
			Helper.setValueMethodOrField(this, property, value);
		}
	}

	@JsonIgnore
	@XmlTransient
	public void setPrimaryKeyArray(Object... keys) {
		for (int i = 0; i < keys.length; i++) {
			Helper.setValueMethodOrField(this, getPrimaryKeyList().get(i), keys[i]);
		}
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		for (String property : getPrimaryKeyList()) {
			Object value = Helper.getValueMethodOrField(this, property);
			result = prime * result + (value == null ? 0 : value.hashCode());
		}

		return result;
	}

	public String pkToString() {
		String pkToString = "";
		for (String displayValue : getPrimaryKeyList()) {

			Object value = Helper.getValueMethodOrField(this, displayValue);
			if (!"".equals(pkToString))
				pkToString += ",";

			pkToString += String.format("%s=%s", displayValue, value);

		}

		return pkToString;
	}

	@Override
	public String prefixoToString() {

		return pkToString();
	}

	@Override
	public int compareTo(Pojo pojo) {
		if (pojo == null)
			return -1;

		for (String property : getPrimaryKeyList()) {

			Object resultMethodFrom = Helper.getValueMethodOrField(this, property);
			Object resultMethodTo = Helper.getValueMethodOrField(pojo, property);

			int result = 0;
			if (resultMethodFrom != null || resultMethodTo != null)
				if (resultMethodFrom == null && resultMethodTo != null)
					return -1;
				else
					result = Helper.compareToObj(resultMethodFrom, resultMethodTo);

			if (result != 0)
				return result;

		}

		return 0;
	}

	public Object[] arrayPrimaryKey() {
		if (!getPrimaryKey().getClass().isArray()) {
			return new Object[] { getPrimaryKey() };
		} else {
			return (Object[]) getPrimaryKey();
		}
	}

	public int compareToKey(Object... keys) {
		if (getPrimaryKeyList() == null || getPrimaryKeyList().size() == 0)
			throw new IllegalArgumentException(
					String.format("Sem primaryKey definida para a classe %s", this.getClass().toString()));

		if (keys.length != getPrimaryKeyList().size())
			throw new IllegalArgumentException(
					String.format("Tamanho da chave [%s] dferente da quantidade de parametros [%s]", keys.length,
							getPrimaryKeyList().size()));

		for (int i = 0; i < arrayPrimaryKey().length; i++) {
			int compara = Helper.compareToObj(arrayPrimaryKey()[i], keys[i]);
			if (compara != 0)
				return compara;
		}

		return 0;

	}

	public boolean equalsToKey(Object... keys) {
		return compareToKey(keys) == 0;
	}

}
