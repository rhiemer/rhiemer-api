package br.com.rhiemer.api.cdi.extension;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.interceptor.InterceptorBinding;

import org.reflections.Reflections;

import br.com.rhiemer.api.cdi.wrapper.AnnotatedTypeWrapper;
import br.com.rhiemer.api.util.audit.Audit;
import br.com.rhiemer.api.util.helper.ReflectionHelper;

public class AuditExtensionCDI implements Extension {

	private static Reflections reflections;
	static {
		reflections = ReflectionHelper.reflections();
	}

	public <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> processAnnotatedType) {

		AnnotatedType<T> annotatedType = processAnnotatedType.getAnnotatedType();
		Set<Class<?>> clazzs = reflections.getTypesAnnotatedWith(InterceptorBinding.class);

		if (annotatedType.getJavaClass().getAnnotation(Audit.class) != null) {

			for (Class<?> clazz : clazzs) {
				if (clazz.isAnnotation()) {
					Annotation auditAnnotation = new Annotation() {
						@Override
						public Class<? extends Annotation> annotationType() {
							return (Class<? extends Annotation>) clazz;
						}
					};

					AnnotatedTypeWrapper<T> wrapper = new AnnotatedTypeWrapper<T>(annotatedType,
							annotatedType.getAnnotations());
					wrapper.addAnnotation(auditAnnotation);

					processAnnotatedType.setAnnotatedType(wrapper);
				}
			}

		}

	}

}
