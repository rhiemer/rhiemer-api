package br.com.rhiemer.api.util.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import br.com.rhiemer.api.util.annotations.ValidaParametro;
import br.com.rhiemer.api.util.annotations.ValidaParametrosDoMetodo;
import br.com.rhiemer.api.util.exception.AbstractValidacaoException;
import br.com.rhiemer.api.util.exception.ValidacaoException;

@Interceptor
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
@ValidaParametrosDoMetodo
public class ValidarParametrosMetodoInterceptor {

	@Inject
	private Validator validator;

	@SuppressWarnings("unchecked")
	private void validar(Object parameter,
			Class<? extends AbstractValidacaoException> clazz) throws Throwable {
		Set<ConstraintViolation<Object>> violations = validator
				.validate(parameter);
		if (violations.isEmpty() == false) {
			Constructor<AbstractValidacaoException> constructor = (Constructor<AbstractValidacaoException>) clazz
					.getConstructor(Set.class);
			AbstractValidacaoException validacaoException = constructor
					.newInstance(violations);
			throw validacaoException;
		}
	}

	@AroundInvoke
	public Object validarMetodo(InvocationContext ic) throws Throwable {

		for (int i = 0; i < ic.getMethod().getParameterAnnotations().length; i++) {

			Annotation[] annotations = ic.getMethod().getParameterAnnotations()[i];
			Object parameter = ic.getParameters()[i];

			for (Annotation annotation : annotations) {

				if (ValidaParametro.class.isInstance(annotation)) {
					Class<? extends AbstractValidacaoException> clazz = ((ValidaParametro) annotation)
							.validacaoException();
					if (clazz == null)
					 clazz = ValidacaoException.class;	
					validar(parameter, clazz);

				}

			}
		}

		return ic.proceed();

	}

}
