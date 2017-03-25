package br.com.rhiemer.api.jpa.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

import br.com.rhiemer.api.util.annotations.cdi.InterceptorDiscovery;

@Documented
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@InterceptorBinding
@Inherited
@InterceptorDiscovery
public @interface TratarLazy {

}
