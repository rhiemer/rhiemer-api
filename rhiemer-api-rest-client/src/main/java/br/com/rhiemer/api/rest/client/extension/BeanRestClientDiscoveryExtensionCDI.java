package br.com.rhiemer.api.rest.client.extension;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import br.com.rhiemer.api.rest.client.annotation.BeanDiscoveryRestClient;
import br.com.rhiemer.api.rest.client.bean.BeanRestClient;

public class BeanRestClientDiscoveryExtensionCDI implements Extension {

	private final Map<BeanDiscoveryRestClient, Class<?>> detectedServices = new HashMap();

	void captureAnnotatedType(@Observes final ProcessAnnotatedType<?> potential) {
		final AnnotatedType<?> annotatedType = potential.getAnnotatedType();
		if (annotatedType.isAnnotationPresent(BeanDiscoveryRestClient.class)) {
			detectedServices.put(
					potential.getAnnotatedType().getJavaClass().getAnnotation(BeanDiscoveryRestClient.class),
					potential.getAnnotatedType().getJavaClass());
		}
	}

	void addBeans(@Observes final AfterBeanDiscovery abd) {
		detectedServices.forEach((beanDiscoveryRestClient, classe) -> abd.addBean(new BeanRestClient(classe,beanDiscoveryRestClient)));
		detectedServices.clear();
	}

}
