package br.com.rhiemer.api.util.lambda.optinal;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;
import java.util.function.Function;

import br.com.rhiemer.api.util.helper.Helper;

public class HelperOptional {

	private HelperOptional() {

	}

	public static <T> Optional<T> ofIsBlank(T value) {
		if (Helper.isBlank(value))
			return empty();
		else
			return of(value);
	}
	
	public static <T,U> Optional<U> mapIsBlank(T value,Function<T,U> mapper) {
		if (mapper == null)
		  return empty();	
		else 
  		  return ofIsBlank(mapper.apply(value));
	}

	
}
