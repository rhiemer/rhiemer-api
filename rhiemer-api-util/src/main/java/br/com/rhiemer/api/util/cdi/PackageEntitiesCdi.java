package br.com.rhiemer.api.util.cdi;

import java.lang.annotation.Annotation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.reflections.Reflections;

import br.com.rhiemer.api.util.annotations.app.ReflectionBasePackage;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.reflection.PackageEntities;

@Dependent
public abstract class PackageEntitiesCdi<T extends Annotation> extends PackageEntities {
	
	@Inject
	@ReflectionBasePackage
	private Reflections reflectionsCdi;


	@PostConstruct
	public void init() {
		this.reflections = reflectionsCdi;
		setClasseMap(Helper.getClassPrincipal(this.getClass()));
		carregaMapClasses();
	}

}
