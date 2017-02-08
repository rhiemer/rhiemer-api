package br.com.rhiemer.api.jpa.producer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import br.com.rhiemer.api.jpa.dao.DaoJPA;
import br.com.rhiemer.api.jpa.dao.factory.DaoFactory;
import br.com.rhiemer.api.util.annotations.dao.DaoEntity;
import br.com.rhiemer.api.util.annotations.service.Crud;

public class DaoProducer {

	@Inject
	private DaoFactory daoFactory;

	@Produces
	@Dependent
	@DaoEntity
	public DaoJPA produce(InjectionPoint ip) {
		if (ip.getAnnotated().isAnnotationPresent(Crud.class)) {

			ParameterizedType type = (ParameterizedType) ip.getType();
			Type[] typeArgs = type.getActualTypeArguments();
			Class<?> entityClass = (Class<?>) typeArgs[0];

			DaoJPA dao = (DaoJPA)daoFactory.buscarDao(entityClass);

			return dao;
		}
		throw new IllegalArgumentException("Annotation @DaoAPI is required when injecting DaoJPA");
	}

}
