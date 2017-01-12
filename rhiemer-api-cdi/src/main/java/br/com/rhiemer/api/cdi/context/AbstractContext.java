package br.com.rhiemer.api.cdi.context;

import java.util.Map;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

/**
 * A baseclass for {@link Context}s.
 *
 * @author Arne Limburg - open knowledge GmbH (arne.limburg@openknowledge.de)
 */
public abstract class AbstractContext implements Context {

  @Override
  public <T> T get(Contextual<T> contextual) {
    return get(contextual, null);
  }

  @Override
  public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
    Map<Contextual<?>, Instance<?>> instances = getContextualMap(creationalContext);
    Instance<T> instance = getInstance(contextual, instances);
    if (instance != null) {
      return instance.get();
    }
    if (creationalContext == null) {
      return null;
    }
    instance = new Instance<T>(contextual, creationalContext);
    instances.put(contextual, instance);
    return instance.get();
  }

  protected abstract Map<Contextual<?>, Instance<?>> getContextualMap(CreationalContext<?> creationalContext);

  @SuppressWarnings("unchecked")
  private <T> Instance<T> getInstance(Contextual<T> requestedContextual, Map<Contextual<?>, Instance<?>> instances) {
    return (Instance<T>) instances.get(requestedContextual);
  }

  protected static class Instance<T> {

    private Contextual<T> contextual;
    private CreationalContext<T> creationalContext;
    private T instance;

    public Instance(Contextual<T> contextual, CreationalContext<T> creationalContext) {
      this.contextual = contextual;
      this.creationalContext = creationalContext;
      this.instance = contextual.create(creationalContext);
    }
    
    public T get() {
      return this.instance;
    }

    public void destroy() {
      this.contextual.destroy(this.instance, this.creationalContext);
    }
    
    protected Contextual<T> getContextual() {
      return contextual;
    }

    protected CreationalContext<T> getCreationalContext() {
      return creationalContext;
    }
  }
}
