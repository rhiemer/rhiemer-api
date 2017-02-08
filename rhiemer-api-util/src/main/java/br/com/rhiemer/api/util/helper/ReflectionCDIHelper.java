package br.com.rhiemer.api.util.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.interceptor.InterceptorBinding;

import org.reflections.Reflections;

public final class ReflectionCDIHelper {

	private ReflectionCDIHelper() {
	}

	public static Map<Class<?>, Set<Class<?>>> classesComInterceptor() {
		Reflections reflections = ReflectionHelper.reflections();
		Set<Class<?>> interceptorBindingClazzs = reflections.getTypesAnnotatedWith(InterceptorBinding.class);
		Map<Class<?>, Set<Class<?>>> result = new HashMap<>();

		for (Class<?> interceptorBindingClazz : interceptorBindingClazzs) {
			if (interceptorBindingClazz.isAnnotation()) {
				Set<Class<?>> interceptorBindingClazzsAnottation = reflections
						.getTypesAnnotatedWith((Class<? extends Annotation>) interceptorBindingClazz);
				for (Class<?> clazz : interceptorBindingClazzsAnottation) {
					if (result.get(clazz) == null)
						result.put(clazz, new HashSet<>());
					result.get(clazz).add(interceptorBindingClazz);

				}

				Set<Method> interceptorBindingClazzsMethod = reflections
						.getMethodsAnnotatedWith((Class<? extends Annotation>) interceptorBindingClazz);
				for (Method method : interceptorBindingClazzsMethod) {
					if (result.get(method.getDeclaringClass()) == null)
						result.put(method.getDeclaringClass(), new HashSet<>());
					result.get(method.getDeclaringClass()).add(interceptorBindingClazz);

				}
				Set<Method> interceptorBindingClazzsMethodParams = reflections
						.getMethodsWithAnyParamAnnotated((Class<? extends Annotation>) interceptorBindingClazz);
				for (Method method : interceptorBindingClazzsMethodParams) {
					if (result.get(method.getDeclaringClass()) == null)
						result.put(method.getDeclaringClass(), new HashSet<>());
					result.get(method.getDeclaringClass()).add(interceptorBindingClazz);

				}
			}
		}

		return result;
	}

}
