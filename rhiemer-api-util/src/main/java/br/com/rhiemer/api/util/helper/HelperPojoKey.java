package br.com.rhiemer.api.util.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import br.com.rhiemer.api.util.annotations.entity.AtributoPrimaryKey;

public class HelperPojoKey {

	public static Map<String, Object> mapPrimaryKey(Class<?> classe, Object... keys) {
		if (keys == null || keys.length == 0) {
			return null;
		} else if (keys.length == 1 && classe.isInstance(keys[0])) {
			return Helper.valuePrimaryKeyList(classe, keys[0]);
		} else if (isPrimaryKeyAtributo(classe, keys)) {
			return valueKeyAtributo(classe, keys);
		} else {
			return Helper.valuePrimaryKeyListParams(classe, keys);
		}

	}

	public static boolean isPrimaryKeyAtributo(Class<?> classe, Object... keys) {
		if (keys == null || keys.length == 0)
			return false;

		AtributoPrimaryKey[] atributosPrimaryKey = classe.getAnnotationsByType(AtributoPrimaryKey.class);
		if (atributosPrimaryKey.length == 0 || atributosPrimaryKey.length != keys.length)
			return false;

		if (IntStream.range(0, atributosPrimaryKey.length)
				.filter(i -> keys[i] == null
						|| !Helper.isAssignableFrom(classe, atributosPrimaryKey[i].value(), keys[i].getClass()))
				.findFirst().orElse(-1) >= 0)
			return false;
		return true;
	}

	public static Map<String, Object> valueKeyAtributo(Class<?> classe, Object... keys) {
		if (keys == null || keys.length == 0)
			return null;

		AtributoPrimaryKey[] atributosPrimaryKey = classe.getAnnotationsByType(AtributoPrimaryKey.class);
		if (atributosPrimaryKey.length == 0 || atributosPrimaryKey.length != keys.length)
			return null;

		final Map<String, Object> primaryKeyValues = new HashMap<>();
		IntStream.range(0, atributosPrimaryKey.length).forEach(
				i -> primaryKeyValues.put(atributosPrimaryKey[i].value(), Helper.convertObjectReflextion(keys[i],
						Helper.getPropertyType(classe, atributosPrimaryKey[i].value()))));
		return primaryKeyValues;
	}

	public static void setAtributoPrimaryKey(Object obj, Object... params) {
		final Map<String, Object> primaryKeyValues = mapPrimaryKey(obj.getClass(), params);
		primaryKeyValues.forEach((x, y) -> Helper.setValueMethodOrField(obj, x, y));

	}

	public static <T> T newInstancePrimaryKey(Class<T> classe, Object... params) {
		T result = Helper.newInstance(classe, params);
		setAtributoPrimaryKey(result, params);
		return result;

	}

	public static int compareToKey(Object objeto, Object... keys) {
		Map<String, Object> map = HelperPojoKey.mapPrimaryKey(objeto.getClass(), keys);
		if (map == null || map.size() == 0)
			return -1;
		List<Object> values = new ArrayList<>(map.values());
		int compara = IntStream.range(0, keys.length).map(i -> Helper.compareToObj(values.get(i), keys[i]))
				.filter(i -> i != 0).findFirst().orElse(0);
		return compara;

	}

	public static <T> T verifyNewInstancePrimaryKey(Class<T> classe, Object... keys) {
		if (keys == null || keys.length == 0) {
			return Helper.newInstance(classe);
		} else if (keys.length == 1 && classe.isInstance(keys[0])) {
			return (T) keys[0];
		} else {
			if (keys.length == 1) {
				T objConv = convertFieldToComplex(classe, keys[0]);
				if (objConv != null)
					return objConv;
			}

			return newInstancePrimaryKey(classe, keys);

		}

	}

	public static <T> T convertFieldToComplex(Class<T> classe, Object key) {

		final Map<String, Class<?>> primaryKeyClass = Helper.getPrimaryKeyList(key.getClass());
		if (primaryKeyClass.size() == 1) {
			List<Class<?>> values = new ArrayList<>(primaryKeyClass.values());
			List<String> chaves = new ArrayList<>(primaryKeyClass.keySet());
			if (classe.isAssignableFrom(values.get(0)))
				return (T) Helper.getValueMethodOrField(key, chaves.get(0));
		}

		return null;

	}

}
