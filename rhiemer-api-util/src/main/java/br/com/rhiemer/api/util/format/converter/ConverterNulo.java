package br.com.rhiemer.api.util.format.converter;

import br.com.rhiemer.api.util.helper.Helper;

public class ConverterNulo implements IConverter {

	@Override
	public <T> String converter(T obj, boolean pretty) {
		return obj.toString();
	}

	@Override
	public <T> T parseString(Class<T> classe, String str) {
		T newData = Helper.newInstance(classe);
		return newData;
	}

}
