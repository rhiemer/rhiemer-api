package br.com.rhiemer.api.cdi.extension;

import java.lang.annotation.Annotation;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import br.com.rhiemer.api.cdi.annotation.InterceptorDiscoveryExtension;
import br.com.rhiemer.api.cdi.wrapper.AnnotatedTypeWrapper;
import br.com.rhiemer.api.util.annotations.InterceptorDiscovery;

public class InterceptorExtensionCDI implements Extension {

	public <T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> processAnnotatedType) {
		AnnotatedType<T> annotatedType = processAnnotatedType.getAnnotatedType();

		Annotation[] annotations = annotatedType.getJavaClass().getAnnotations();
		for (Annotation annotation : annotations) {

			if (annotation.annotationType().getAnnotation(InterceptorDiscovery.class) != null) {
				Annotation discoveyInterceptorAnnotation = new InterceptorDiscoveryExtension() {

					@Override
					public Class<? extends Annotation> annotationType() {
						return annotation.annotationType();
					}
				};
				AnnotatedTypeWrapper<T> wrapper = new AnnotatedTypeWrapper<T>(annotatedType,
						annotatedType.getAnnotations());
				wrapper.addAnnotation(discoveyInterceptorAnnotation);

				processAnnotatedType.setAnnotatedType(wrapper);
			}
		}

	}

}
