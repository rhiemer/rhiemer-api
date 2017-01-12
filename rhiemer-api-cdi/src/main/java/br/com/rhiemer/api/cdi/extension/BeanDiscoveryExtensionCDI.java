package br.com.rhiemer.api.cdi.extension;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.rhiemer.api.util.annotations.BeanDiscovery;
import br.com.rhiemer.api.util.annotations.BeanDiscoveryProxy;
import br.com.rhiemer.api.util.cdi.bean.BeanAPI;

public class BeanDiscoveryExtensionCDI implements Extension {

	private static final Logger logger = LoggerFactory.getLogger(BeanDiscoveryExtensionCDI.class);
	private final Map<Class<?>, Class<?>> detectedServices = new HashMap<>();

	void captureAnnotatedType(@Observes final ProcessAnnotatedType<?> potential) {
		final AnnotatedType<?> annotatedType = potential.getAnnotatedType();
		if (logger.isTraceEnabled())
		{
			logger.trace("Classe Java CDI:{}.", annotatedType.getJavaClass());
		}	
		if (annotatedType.isAnnotationPresent(BeanDiscoveryProxy.class)) {
			detectedServices.put(annotatedType.getJavaClass(),
					annotatedType.getJavaClass().getAnnotation(BeanDiscoveryProxy.class).proxyClass());
		} else if (annotatedType.isAnnotationPresent(BeanDiscovery.class)) {
			detectedServices.put(annotatedType.getJavaClass(), null);
		}
	}
	
	

	void addBeans(@Observes final AfterBeanDiscovery abd) {
		detectedServices.forEach((classe, proxy) -> abd.addBean(new BeanAPI(classe, proxy)));
		detectedServices.clear();
	}
	
	

}
