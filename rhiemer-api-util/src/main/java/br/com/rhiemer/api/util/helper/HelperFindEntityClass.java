package br.com.rhiemer.api.util.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.reflections.Reflections;

import br.com.rhiemer.api.util.annotations.rest.RESTful;

public final class HelperFindEntityClass {

	private static Reflections reflections;

	static {
		reflections = ReflectionHelper.reflections();
	}

	private HelperFindEntityClass() {
	}

	public static Class findEntityByString(String className, Class<? extends Annotation>... annotations) {

		Class<?> result = null;
		try {
			result = Class.forName(className);
		} catch (ClassNotFoundException e) {

		}

		List<Class<? extends Annotation>> paramsAnnotations = Helper.convertArgs(annotations);
		Set<Class<? extends Annotation>> listAnnotations = new HashSet<>();
		listAnnotations.addAll(paramsAnnotations);
		paramsAnnotations.add(RESTful.class);

		result = paramsAnnotations
				.stream().map(t -> reflections.getTypesAnnotatedWith(t).stream()
						.filter(x -> compareNameAliasClass(x, className)).findFirst())
				.filter(Objects::nonNull).findFirst().get().orElse(null);
		return result;

	}

	public static boolean compareNameAliasClass(Class<?> classe, String name) {

		if (compareNameClass(classe, name))
			return true;

		if (compareAliasClass(classe, name))
			return true;

		return false;
	}

	public static boolean compareNameClass(Class<?> classe, String name) {

		if (classe.getName() != null && classe.getName().equals(name))
			return true;

		if (classe.getCanonicalName() != null && classe.getCanonicalName().equals(name))
			return true;

		if (classe.getSimpleName() != null && classe.getSimpleName().equals(name))
			return true;

		return false;
	}

	public static boolean compareAliasClass(Class<?> classe, String name) {
		String aliasClasse = aliasClass(classe);
		if (aliasClasse.equals(name))
			return true;
		else
			return false;
	}

	public static String aliasClass(Class<?> classe) {
		return Optional.ofNullable(classe.getAnnotation(RESTful.class)).map(t -> t.value())
				.orElse(classe.getSimpleName());
	}

}
