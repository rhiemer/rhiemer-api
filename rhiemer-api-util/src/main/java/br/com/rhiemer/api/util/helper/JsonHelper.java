package br.com.rhiemer.api.util.helper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.rhiemer.api.util.exception.ConversaoObjetoException;

public final class JsonHelper {

	private JsonHelper() {

	}

	public static String toJson(Object objeto) {
		ObjectMapper mapper = new ObjectMapper();
		String strObjFormatado = null;
		try {
			strObjFormatado = mapper.writeValueAsString(objeto);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return strObjFormatado;
	}

	public static String toJsonFormatado(Object objeto) {
		ObjectMapper mapper = new ObjectMapper();
		String strObjFormatado = null;
		try {
			strObjFormatado = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objeto);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return strObjFormatado;
	}

	public static <T> T toObject(Object json, Class<T> classe) {
		ObjectMapper mapper = new ObjectMapper();

		T jsonObj = null;
		String jsonConverter = null;
		if (String.class.isInstance(json)) {
			jsonConverter = json.toString();
		} else {
			try {
				jsonConverter = mapper.writeValueAsString(json);
			} catch (JsonProcessingException e) {
				throw new ConversaoObjetoException(
						String.format("Esse objeto não é passivel de conversão para uma String no formato JSON:%s",
								e.getMessage()),
						e);
			}
		}

		try {
			if (jsonConverter.startsWith("[")) {

				Class<?> _clazz = classe;
				JavaType type = null;
				boolean isArray = false;
				if (classe.isAssignableFrom(Collection.class) || classe.isArray()) {
					_clazz = Helper.getClassPrincipal(classe);
					if (_clazz.isAssignableFrom(Collection.class) || _clazz.isArray()) {
						if (_clazz.isArray()) {
							_clazz = List.class;
							isArray = true;
						}
						type = mapper.getTypeFactory().constructCollectionType((Class<? extends Collection>) _clazz,
								Map.class);
					} else {
						Class<?> _clazz2 = classe;
						if (_clazz2.isArray()) {
							_clazz2 = List.class;
							isArray = true;
						}
						type = mapper.getTypeFactory().constructCollectionType((Class<? extends Collection>) _clazz2,
								_clazz);
					}
				} else {
					type = mapper.getTypeFactory().constructCollectionType(List.class, classe);
				}
				jsonObj = mapper.readValue(jsonConverter, type);
				if (isArray) {
					jsonObj = (T) ((List<?>) jsonObj).toArray(new Object[] {});
				}

			} else {
				jsonObj = mapper.readValue(jsonConverter, classe);
			}
		} catch (IOException e) {
			throw new ConversaoObjetoException(
					String.format("Não foi possível converter JSON passado para o Objeto %s do sistema :%s",
							classe.getName(), e.getMessage()),
					e);
		}
		return jsonObj;

	}

}
