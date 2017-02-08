package br.com.rhiemer.api.rest.client.extension;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import br.com.rhiemer.api.rest.client.annotation.BeanDiscoveryRestClient;
import br.com.rhiemer.api.rest.client.bean.BeanRestClient;

public class BeanRestClientDiscoveryExtensionCDI implements Extension {

	private final List<BeanRestClient> detectedServices = new ArrayList<>();

	void captureAnnotatedType(@Observes final ProcessAnnotatedType<?> potential, BeanManager bm) {
		final AnnotatedType<?> annotatedType = potential.getAnnotatedType();
		if (annotatedType.isAnnotationPresent(BeanDiscoveryRestClient.class)) {
			detectedServices.add(new BeanRestClient(potential.getAnnotatedType().getJavaClass(),
					potential.getAnnotatedType().getJavaClass().getAnnotation(BeanDiscoveryRestClient.class), bm,
					annotatedType));
		}
	}

	void addBeans(@Observes final AfterBeanDiscovery abd, BeanManager bm) {
		detectedServices.forEach((beanRestClient) -> abd.addBean(beanRestClient));
		detectedServices.clear();
	}

}
