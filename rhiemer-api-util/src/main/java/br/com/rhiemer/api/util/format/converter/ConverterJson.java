package br.com.rhiemer.api.util.format.converter;

import br.com.rhiemer.api.util.helper.JsonHelper;

public class ConverterJson implements IConverter {

	@Override
	public <T> String converter(T obj, boolean pretty) {
		if (!pretty)
			return JsonHelper.toJson(obj);
		else
			return JsonHelper.toJsonFormatado(obj);
	}

	@Override
	public <T> T parseString(Class<T> classe, String str) {
		return JsonHelper.toObject(str, classe);
	}

}
