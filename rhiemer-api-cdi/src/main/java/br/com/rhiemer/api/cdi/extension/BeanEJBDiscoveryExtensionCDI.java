package br.com.rhiemer.api.cdi.extension;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import br.com.rhiemer.api.util.annotations.BeanDiscoveryEJB;
import br.com.rhiemer.api.util.cdi.bean.BeanEJB;
import br.com.rhiemer.api.util.ejb.client.EJBClientDto;

public class BeanEJBDiscoveryExtensionCDI implements Extension {

	private final Map<EJBClientDto, Class<?>> detectedServices = new HashMap();

	void captureAnnotatedType(@Observes final ProcessAnnotatedType<?> potential) {
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

			detectedServices.put(ejbClientDto,
					Void.class.equals(beanDiscoveryEJB.classeProxy()) ? null : beanDiscoveryEJB.classeProxy());
		}
	}

	void addBeans(@Observes final AfterBeanDiscovery abd) {
		detectedServices.forEach((ejbClientDto, proxy) -> abd.addBean(new BeanEJB(ejbClientDto, proxy)));
		detectedServices.clear();
	}

}
