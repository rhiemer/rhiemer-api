package br.com.rhiemer.api.util.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.rhiemer.api.util.annotations.entity.AtributoPrimaryKey;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.helper.HelperPojoKey;

public abstract class PojoKeyAbstract extends PojoAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2478964439043142234L;

	@Transient
	@JsonIgnore
	@XmlTransient
	private transient List<String> primaryKey;

	@Transient
	@JsonIgnore
	@XmlTransient
	private transient Map<String, Class<?>> primaryKeyTypes;

	@JsonIgnore
	@XmlTransient
	public List<String> getPrimaryKeyList() {
		if (primaryKey == null) {

			primaryKey = new ArrayList<>();

			for (Map.Entry<String, Class<?>> entry : primaryKeyTypes().entrySet()) {
				primaryKey.add(entry.getKey());
			}

		}
		return primaryKey;
	}

	public Map<String, Class<?>> primaryKeyTypes() {
		if (this.primaryKeyTypes == null) {
			primaryKeyTypes = new HashMap<>();
			primaryKeyTypes.putAll(Helper.getPrimaryKeyList(this.getClass()));
		}
		return this.primaryKeyTypes;
	}

	public Map<Object, Object> primaryKeyValueMap() {
		Map<Object, Object> map = new HashMap<>();
		this.primaryKey.forEach(t -> map.put(t, Helper.getValueMethodOrField(this, t)));
		return map;
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
		HelperPojoKey.setAtributoPrimaryKey(this, keys);
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
		return compareToKey(pojo);
	}

	public Object[] arrayPrimaryKey() {
		if (!getPrimaryKey().getClass().isArray()) {
			return new Object[] { getPrimaryKey() };
		} else {
			return (Object[]) getPrimaryKey();
		}
	}

	public int compareToKey(Object... keys) {
		return HelperPojoKey.compareToKey(this, keys);

	}

	public boolean equalsToKey(Object... keys) {
		return compareToKey(keys) == 0;
	}

}
