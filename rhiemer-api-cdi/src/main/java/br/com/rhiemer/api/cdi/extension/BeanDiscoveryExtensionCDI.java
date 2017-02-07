package br.com.rhiemer.api.cdi.extension;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rhiemer.api.cdi.wrapper.AnnotatedTypeWrapper;
import br.com.rhiemer.api.util.annotations.bean.BeanDiscovery;
import br.com.rhiemer.api.util.annotations.bean.BeanDiscoveryProxy;
import br.com.rhiemer.api.util.cdi.bean.BeanAPI;
import br.com.rhiemer.api.util.helper.ReflectionCDIHelper;

public class BeanDiscoveryExtensionCDI implements Extension {

	private static Map<Class<?>, Set<Class<?>>> classesComInterceptor;
	static {
		classesComInterceptor = ReflectionCDIHelper.classesComInterceptor();
	}

	private static final Logger logger = LoggerFactory.getLogger(BeanDiscoveryExtensionCDI.class);
	private final List<BeanAPI> detectedServices = new ArrayList<>();

	private void addInterceptos(ProcessAnnotatedType<?> potential) {
		if (classesComInterceptor.get(potential.getAnnotatedType().getJavaClass()) != null) {

			AnnotatedTypeWrapper wrapper = new AnnotatedTypeWrapper(potential.getAnnotatedType(),
					potential.getAnnotatedType().getAnnotations());
			potential.setAnnotatedType(wrapper);
			for (Class<?> clazz : classesComInterceptor.get(potential.getAnnotatedType().getJavaClass())) {
				Annotation auditAnnotation = new Annotation() {
					@Override
					public Class<? extends Annotation> annotationType() {
						return (Class<? extends Annotation>) clazz;
					}
				};
				wrapper.addAnnotation(auditAnnotation);

			}
		}
	}

	void captureAnnotatedType(@Observes final ProcessAnnotatedType<?> potential, BeanManager bm) {
		final AnnotatedType<?> annotatedType = potential.getAnnotatedType();
		if (logger.isTraceEnabled()) {
			logger.trace("Classe Java CDI:{} BeanManager:{}", annotatedType.getJavaClass(), bm.toString());
		}
		if (annotatedType.isAnnotationPresent(BeanDiscoveryProxy.class)) {
			detectedServices.add(new BeanAPI(annotatedType.getJavaClass(),
					annotatedType.getJavaClass().getAnnotation(BeanDiscoveryProxy.class).proxyClass(), bm,
					annotatedType));

		} else if (annotatedType.isAnnotationPresent(BeanDiscovery.class)) {
			detectedServices.add(new BeanAPI(annotatedType.getJavaClass(), bm, annotatedType));
		}

		/*
		 * if (temBeanDiscovery) { addInterceptos(potential);
		 * 
		 * }
		 */

	}

	void addBeans(@Observes final AfterBeanDiscovery abd, BeanManager bm) {
		if (logger.isInfoEnabled()) {
			logger.info("BeanManager: {}.", bm.toString());
		}
		detectedServices.forEach((beanAPI) -> abd.addBean(beanAPI));
		detectedServices.clear();
	}

}
