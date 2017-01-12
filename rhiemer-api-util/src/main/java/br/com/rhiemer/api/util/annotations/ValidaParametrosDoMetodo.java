package br.com.rhiemer.api.util.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@Target({ METHOD, TYPE })
@Retention(RUNTIME)
@InterceptorBinding
@InterceptorDiscovery
public @interface ValidaParametrosDoMetodo {
}