package br.com.rhiemer.api.util.thread;

import javax.ejb.Remote;
import javax.enterprise.context.Dependent;

import br.com.rhiemer.api.util.annotations.BeanDiscoveryEJB;
import br.com.rhiemer.api.util.trace.TransactionContext;

@Dependent
@Remote
@BeanDiscoveryEJB(classe=SessionThreadContextAPI.class,app="beerpoints",modulo="rhiemer-api-ejb",beanName="sessionThreadContextAPI")
public interface SessionThreadContextAPI extends ThreadContextAPI {
	
	public void setTransactionContext(TransactionContext transactionContext);


}