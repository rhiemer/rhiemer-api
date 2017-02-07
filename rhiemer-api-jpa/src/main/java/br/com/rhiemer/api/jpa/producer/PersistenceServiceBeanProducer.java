package br.com.rhiemer.api.jpa.producer;

import static br.com.rhiemer.api.util.helper.ConstantesAPI.BASE_PACKAGE_API;
import static br.com.rhiemer.api.util.helper.ConstantesAPI.BASE_PACKAGE;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import br.com.rhiemer.api.jpa.annotations.PersistenceServiceBeanEntities;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.util.annotations.app.ReflectionBasePackage;
import br.com.rhiemer.api.util.service.PersistenceServiceBean;

@ApplicationScoped
public class PersistenceServiceBeanProducer {

	@Inject
	private transient Instance<Object> creator;
	private Map<Class, PersistenceServiceBean> mapPersistenceServiceBean = new HashMap<>();

	@Inject
	@ReflectionBasePackage
	private Reflections reflections;

	private void adicionarPersistenceServiceBean(Class<?> clazz,
			Map<Class, PersistenceServiceBean> mapPersistenceServiceBean) {

		if (clazz.getPackage().getName().startsWith(BASE_PACKAGE_API))
			return;

		if (clazz.getGenericInterfaces().length == 0 || !(clazz.getGenericInterfaces()[0] instanceof ParameterizedType))
			return;

		Class<? extends Entity> clazzParameter = (Class<? extends Entity>) ((ParameterizedType) clazz
				.getGenericInterfaces()[0]).getActualTypeArguments()[0];

		// recupera o bean do serviço
		PersistenceServiceBean<?, ?> bean = (PersistenceServiceBean<?, ?>) creator.select(clazz).get();

		mapPersistenceServiceBean.put(clazzParameter, bean);

	}

	@Produces
	@PersistenceServiceBeanEntities
	public Map<Class, PersistenceServiceBean> producerPersistenceServiceBean(InjectionPoint ip) {

		mapPersistenceServiceBean.clear();

		Set<Class<? extends PersistenceServiceBean>> clazzs = reflections.getSubTypesOf(PersistenceServiceBean.class);

		for (Class<?> clazz : clazzs) {
			adicionarPersistenceServiceBean(clazz, mapPersistenceServiceBean);
		}

		return mapPersistenceServiceBean;
	}

}
