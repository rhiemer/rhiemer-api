package br.com.rhiemer.api.util.lambda.optinal;

import java.util.Optional;
import java.util.function.Consumer;

public class OptionalConsumerCheck<T> implements Consumer<Optional<T>> {

	private final Consumer<T> c;
	private final Runnable r;

	public OptionalConsumerCheck(Consumer<T> c, Runnable r) {
		super();
		this.c = c;
		this.r = r;
	}

	public static <T> OptionalConsumerCheck<T> of(Consumer<T> c, Runnable r) {
		return new OptionalConsumerCheck(c, r);
	}

	@Override
	public void accept(Optional<T> t) {
		if (t.isPresent())
			c.accept(t.get());
		else
			r.run();

	}

}
