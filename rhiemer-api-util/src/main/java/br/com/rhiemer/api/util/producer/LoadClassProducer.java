package br.com.rhiemer.api.util.producer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.reflections.Reflections;

import br.com.rhiemer.api.util.annotations.app.LoadClasses;
import br.com.rhiemer.api.util.annotations.app.PropriedadeAplicacao;
import br.com.rhiemer.api.util.annotations.app.ReflectionBasePackage;
import br.com.rhiemer.api.util.cdi.CDIUtil;
import br.com.rhiemer.api.util.helper.Helper;

@ApplicationScoped
public class LoadClassProducer {

	@Inject
	@ReflectionBasePackage
	private Reflections reflections;

	@Produces
	@LoadClasses
	public <T> List<T> loadClassesProducer(InjectionPoint injectionPoint) {

		Class<?> classBean = Helper.getClassPrincipal(injectionPoint.getMember().getDeclaringClass());
		List<T> listaBeans = new ArrayList<>();
		Set lista = reflections.getSubTypesOf(classBean);
		while (lista.iterator().hasNext()) {
			Class<T> classe = (Class<T>) lista.iterator().next();
			T bean = (T)CDIUtil.getBean(classe);
			if (bean != null)
				listaBeans.add(bean);

		}

		return listaBeans;
	}

}
