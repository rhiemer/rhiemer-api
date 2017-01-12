package br.com.rhiemer.api.util.reflection;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import br.com.rhiemer.api.util.exception.NotFoundException;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.producer.Producer;

public class PackageEntities {

	private Class<? extends Annotation> classeMap;
	protected Map<Object, Class<?>> mapClasses = new HashMap<>();;
	protected Reflections reflections;

	protected String getFieldAnnotation() {
		return "value";
	}

	protected Object valueAnnotation(Class<?> clazz, Object obj) {
		if (obj == null || obj.toString().isEmpty()) {
			obj = clazz.getSimpleName();
		}
		return obj;
	}

	protected void carregaMapClasses() {

		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(getClasseMap());

		for (Class<?> clazz : annotated) {
			Object obj = Helper.valueAnnotationClass(clazz, getClasseMap(), getFieldAnnotation());
			obj = valueAnnotation(clazz, obj);
			mapClasses.put(obj, clazz);
		}
	}

	protected String notFoundMessage() {
		return null;
	}

	public Class<?> get(Object key) {
		if (reflections == null) {
			reflections = Producer.reflectionsAplicacao();
			carregaMapClasses();
		}
		return mapClasses.get(key);

	}

	public Class<?> getNotFound(Object key) {
		Class<?> value = this.get(key);
		if (value != null)
			return value;
		String notFoundMessage = notFoundMessage();
		if (!StringUtils.isBlank(notFoundMessage)) {
			throw new NotFoundException(String.format(notFoundMessage, key));
		} else {
			throw new NotFoundException(String.format("Chave %s não encontrada.", key));
		}

	}

	public Class<? extends Annotation> getClasseMap() {
		return classeMap;
	}

	public void setClasseMap(Class<? extends Annotation> classeMap) {
		this.classeMap = classeMap;
	}

}
