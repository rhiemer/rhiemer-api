package br.com.rhiemer.api.jpa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(UniqueKeys.class)
public @interface UniqueKey {

	String nome();
	
    String[] columnNames();

    String messageValidate() default "{UniqueKey.message}";
    
    boolean validar() default false;
    
    boolean atualizar() default true;

   
}