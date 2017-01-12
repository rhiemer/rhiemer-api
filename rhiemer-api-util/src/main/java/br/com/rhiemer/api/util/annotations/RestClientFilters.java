package br.com.rhiemer.api.util.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Qualificador para beans que existem somente no escopo dos projetos de API
 * 
 * @author <a href="mailto:asouza@redhat.com">Ângelo Galvão</a>
 *
 */

@Target({ TYPE })
@Retention(RUNTIME)
public @interface RestClientFilters {
	
	Class<?>[] value();

}
