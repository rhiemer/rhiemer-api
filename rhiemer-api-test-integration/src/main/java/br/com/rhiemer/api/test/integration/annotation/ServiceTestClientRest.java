package br.com.rhiemer.api.test.integration.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * Marca variaveis para injetar a propriedade do arquivo properties da aplicação
 * ( valor de "value") Verifica se a propriedade é requerida para subir a
 * aplicação
 * 
 * @author rodrigo.hiemer
 * 
 */


@Retention(RUNTIME)
@Target({ FIELD, METHOD, TYPE })
public @interface ServiceTestClientRest {

	public String path() default "";
	
	@Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface ServiceClientRestFilters {
    	Class[] filters();
    }



}
