package br.com.rhiemer.api.jpa.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.rhiemer.api.jpa.enums.EnumTipoOrderBY;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(OrderByAtributos.class)
public @interface OrderByAtributo {
   String value();
   EnumTipoOrderBY tipo() default EnumTipoOrderBY.ASC;
}
