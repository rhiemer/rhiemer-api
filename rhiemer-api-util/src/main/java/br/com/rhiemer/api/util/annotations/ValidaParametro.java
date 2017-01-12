package br.com.rhiemer.api.util.annotations;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.com.rhiemer.api.util.exception.AbstractValidacaoException;

@Documented
@Target({ PARAMETER })
@Retention(RUNTIME)
public @interface ValidaParametro {

	Class<? extends AbstractValidacaoException> validacaoException();

}
