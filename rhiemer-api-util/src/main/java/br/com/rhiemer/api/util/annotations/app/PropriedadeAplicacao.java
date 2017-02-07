package br.com.rhiemer.api.util.annotations.app;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/***
 * Marca variaveis para injetar a propriedade do arquivo properties da aplicação
 * ( valor de "value") Verifica se a propriedade é requerida para subir a
 * aplicação
 * 
 * @author rodrigo.hiemer
 * 
 */

@Qualifier
@Retention(RUNTIME)
@Target({ FIELD, METHOD, TYPE })
public @interface PropriedadeAplicacao {

	@Nonbinding
	String value() default "";

	@Nonbinding
	boolean required() default true;

}
