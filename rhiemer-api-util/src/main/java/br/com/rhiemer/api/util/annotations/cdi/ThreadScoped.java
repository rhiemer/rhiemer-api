package br.com.rhiemer.api.util.annotations.cdi;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.NormalScope;

/**
 * <strong>Don't use in ThreadPools</strong> unless you know what you are doing.
 * @author Arne Limburg
 */
@Target( { TYPE, METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Inherited
@NormalScope
public @interface ThreadScoped
{

}