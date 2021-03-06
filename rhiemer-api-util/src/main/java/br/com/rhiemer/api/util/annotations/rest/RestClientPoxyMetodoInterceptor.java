package br.com.rhiemer.api.util.annotations.rest;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import br.com.rhiemer.api.util.proxy.ProxyCallMetodoHandlerJavassit;

/**
 * Qualificador para beans que existem somente no escopo dos projetos de API  
 * 
 * @author <a href="mailto:asouza@redhat.com">Ângelo Galvão</a>
 *
 */
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
public @interface RestClientPoxyMetodoInterceptor {
	
	Class<? extends ProxyCallMetodoHandlerJavassit> value();
	
	

}
