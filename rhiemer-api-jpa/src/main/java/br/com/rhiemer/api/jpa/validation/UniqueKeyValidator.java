package br.com.rhiemer.api.jpa.validation;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.rhiemer.api.jpa.annotations.UniqueKey;
import br.com.rhiemer.api.jpa.builder.BuilderCriteriaJPA;
import br.com.rhiemer.api.jpa.entity.Entity;
import br.com.rhiemer.api.jpa.mapper.EntityManagerMapClass;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public class UniqueKeyValidator implements ConstraintValidator<UniqueKey, PojoKeyAbstract> {

	@Inject
	private EntityManagerMapClass entityManagerMapClass;

	private UniqueKey constraintAnnotation;

	@Override
	public void initialize(UniqueKey constraintAnnotation) {	
		this.constraintAnnotation = constraintAnnotation;

	}

	@Override
	public boolean isValid(PojoKeyAbstract target, ConstraintValidatorContext context) {

		EntityManager entityManager = entityManagerMapClass
				.getEntityManagerByEntity((Class<? extends Entity>) target.getClass());
		if (entityManager == null) {
			return true;
		}
		 

		PojoKeyAbstract result = (PojoKeyAbstract) BuilderCriteriaJPA.builderCreate().resultClass(target.getClass())
				.build().uniqueKeyValida(target).buildQuery(entityManager).getSingleResult();
		return (result == null);
	}

}
