package br.com.rhiemer.api.util.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;

import br.com.rhiemer.api.util.enums.EnumTypeEJBClient;

/**
 * Qualificador para beans que existem somente no escopo dos projetos de API  
 * 
 * @author <a href="mailto:asouza@redhat.com">Ângelo Galvão</a>
 *
 */

@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
public @interface BeanDiscoveryEJB {

	EnumTypeEJBClient tipo() default EnumTypeEJBClient.STATELESS;
	
	Class<?> classe() ;
	
	@Nonbinding
	String app() default "";
	@Nonbinding
	String modulo() default "";
	@Nonbinding
	String beanName() default "";
	@Nonbinding
	String distinctName() default "" ;
	@Nonbinding
	Class<?> classeProxy() default Void.class;
	
	
	
}
