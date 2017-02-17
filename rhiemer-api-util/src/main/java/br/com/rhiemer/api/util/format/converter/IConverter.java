package br.com.rhiemer.api.util.format.converter;

public interface IConverter {

	default <T> String converter(T obj) {
		return converter(obj, true);
	}

	<T> String converter(T obj, boolean pretty);
	
	<T> T parseString(Class<T> classe,String str);
}
