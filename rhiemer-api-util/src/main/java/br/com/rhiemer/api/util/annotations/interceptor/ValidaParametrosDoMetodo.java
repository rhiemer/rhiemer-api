package br.com.rhiemer.api.util.annotations.interceptor;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

import br.com.rhiemer.api.util.annotations.cdi.InterceptorDiscovery;

@Target({ METHOD, TYPE })
@Retention(RUNTIME)
@InterceptorBinding
@Inherited
@InterceptorDiscovery
public @interface ValidaParametrosDoMetodo {
}