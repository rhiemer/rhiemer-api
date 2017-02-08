package br.com.rhiemer.api.cdi.extension;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;

import br.com.rhiemer.api.cdi.context.thread.ThreadContext;
import br.com.rhiemer.api.util.annotations.cdi.ThreadScoped;

/**
 * @author Arne Limburg - open knowledge GmbH (arne.limburg@openknowledge.de)
 */
public class ThreadContextExtension implements Extension {

	private Set<Bean<?>> startupBeans = new HashSet<Bean<?>>();
	
	public void registerContexts(@Observes AfterBeanDiscovery afterBeanDiscovery) {
		afterBeanDiscovery.addContext(new ThreadContext());

	}

	  public void registerStartupBean(@Observes ProcessBean<?> event) {
	    if (event.getBean().getBeanClass().getAnnotation(ThreadScoped.class) != null) {
	      startupBeans.add(event.getBean());
	    }
	  }

	  public void initializeBeans(@Observes AfterDeploymentValidation event, BeanManager beanManager) {
	    for (Bean<?> startupBean : startupBeans) {
	      CreationalContext<?> creationalContext = beanManager.createCreationalContext(startupBean);
	      beanManager.getReference(startupBean, Object.class, creationalContext).toString(); //initializes the bean
	    }
	  }

	

	
	
	
}