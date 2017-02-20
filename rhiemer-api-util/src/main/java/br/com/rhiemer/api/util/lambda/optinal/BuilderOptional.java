package br.com.rhiemer.api.util.lambda.optinal;

import static java.util.Optional.empty;

import java.util.Optional;
import java.util.function.Function;

public class BuilderOptional<T> {

	protected Optional<?> optional;
	protected T value;
	
	protected BuilderOptional()
	{
		super();
		this.value=null;
	}
	
	protected BuilderOptional(T value)
	{
		super();
		this.value=value;
	}
	
	protected BuilderOptional(T value,Optional<T> optional)
	{
		super();
		this.value=value;
		this.optional=optional;		
	}

	public BuilderOptional<T> isBlank(T value) {
		this.value = value;
		this.optional = HelperOptional.ofIsBlank(value);
		return this;
	}

	public <U> BuilderOptional<T> mapIsBlank(Function<T, U> mapper) {
		if (mapper == null) {
			clear();
		} else {
			this.optional = HelperOptional.mapIsBlank(this.value,mapper);
		}
		return this;
	}

	protected void clear() {
		this.optional = empty();
		this.value = null;
	}

	public T get() {
		if (optional == null || !optional.isPresent()) {
			return null;
		} else {
			return (T) optional.get();
		}
	}

	public static <T> BuilderOptional<T> of() {
		return new BuilderOptional<>();
	}
	
	public static <T> BuilderOptional<T> ofIsBlank(T value) {
		BuilderOptional<T> builder = new BuilderOptional<>();
		return builder.ofIsBlank(value);
	}
	
	public static <T,U> BuilderOptional<T> ofmapIsBlank(T value,Function<T, U> mapper) {
		BuilderOptional<T> builder = new BuilderOptional<>(value);
		return builder.mapIsBlank(mapper);
	}

}
