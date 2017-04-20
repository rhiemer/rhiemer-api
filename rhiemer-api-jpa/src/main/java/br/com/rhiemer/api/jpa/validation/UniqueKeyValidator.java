package br.com.rhiemer.api.jpa.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.rhiemer.api.jpa.annotations.UniqueKey;
import br.com.rhiemer.api.jpa.builder.BuilderCriteriaJPA;
import br.com.rhiemer.api.jpa.dao.DaoJPA;
import br.com.rhiemer.api.jpa.dao.factory.DaoFactory;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public class UniqueKeyValidator implements ConstraintValidator<UniqueKey, PojoKeyAbstract> {

	@Inject
	private DaoFactory daoFacroty;

	private UniqueKey constraintAnnotation;

	@Override
	public void initialize(UniqueKey constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;

	}

	@Override
	public boolean isValid(PojoKeyAbstract target, ConstraintValidatorContext context) {

		DaoJPA dao = (DaoJPA) daoFacroty.buscarDao(target.getClass());
		if (dao == null) {
			return true;
		}

		PojoKeyAbstract result = dao.excutarQueryUniqueResult(
				BuilderCriteriaJPA.builderCreate().resultClass(target.getClass()).build().uniqueKeyValida(target));
		return (result == null);
	}

}
