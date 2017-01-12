package br.com.rhiemer.api.cdi.context.thread;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

import br.com.rhiemer.api.cdi.context.AbstractContext;
import br.com.rhiemer.api.util.annotations.ThreadScoped;

public class ThreadContext extends AbstractContext {

	private static ThreadLocal<Map<Contextual<?>, Instance<?>>> contextualMaps = new ThreadLocal<Map<Contextual<?>, Instance<?>>>();

	@Override
	public Class<? extends Annotation> getScope() {
		return ThreadScoped.class;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	protected Map<Contextual<?>, Instance<?>> getContextualMap(CreationalContext<?> creationalContext) {

		

		Map<Contextual<?>, Instance<?>> contextualMap = contextualMaps.get();
		if (contextualMap == null) {
			contextualMap = new HashMap<Contextual<?>, AbstractContext.Instance<?>>();
			contextualMaps.set(contextualMap);
		}

		return contextualMaps.get();
	}

}