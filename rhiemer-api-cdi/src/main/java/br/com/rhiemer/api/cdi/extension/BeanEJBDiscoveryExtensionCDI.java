package br.com.rhiemer.api.cdi.extension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import br.com.rhiemer.api.util.annotations.bean.BeanDiscoveryEJB;
import br.com.rhiemer.api.util.cdi.bean.BeanEJB;
import br.com.rhiemer.api.util.ejb.client.EJBClientDto;

public class BeanEJBDiscoveryExtensionCDI implements Extension {

	private final List<BeanEJB> detectedServices = new ArrayList<>();

	void captureAnnotatedType(@Observes final ProcessAnnotatedType<?> potential, BeanManager bm) {
		final AnnotatedType<?> annotatedType = potential.getAnnotatedType();
		if (annotatedType.isAnnotationPresent(BeanDiscoveryEJB.class)) {
			BeanDiscoveryEJB beanDiscoveryEJB = annotatedType.getAnnotation(BeanDiscoveryEJB.class);
			EJBClientDto ejbClientDto = new EJBClientDto();
			ejbClientDto.setTipo(beanDiscoveryEJB.tipo());
			ejbClientDto.setBeanName(beanDiscoveryEJB.beanName());
			ejbClientDto.setClasse(beanDiscoveryEJB.classe());
			ejbClientDto.setApp(beanDiscoveryEJB.app());
			ejbClientDto.setModulo(beanDiscoveryEJB.modulo());
			ejbClientDto.setDistinctName(beanDiscoveryEJB.distinctName());

			detectedServices.add(new BeanEJB(ejbClientDto,
					(Void.class.equals(beanDiscoveryEJB.classeProxy()) ? null : beanDiscoveryEJB.classeProxy()), bm,
					potential.getAnnotatedType()));
		}
	}

	void addBeans(@Observes final AfterBeanDiscovery abd, BeanManager bm) {
		detectedServices.forEach((beanEjb) -> abd.addBean(beanEjb));
		detectedServices.clear();
	}

}
