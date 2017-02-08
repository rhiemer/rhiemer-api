package br.com.rhiemer.api.util.annotations.evento;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

import br.com.rhiemer.api.util.annotations.cdi.InterceptorDiscovery;

@Documented
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
@InterceptorBinding
@Inherited
@InterceptorDiscovery
@Repeatable(DesligaEventos.class)
public @interface DesligaEvento {

	@Nonbinding
	String chaveEvento() default "";
	
	@Nonbinding
	String nomeChaveParametro() default "";
	
	@Nonbinding
	String atributoChaveParametro() default "";
	
	

}
