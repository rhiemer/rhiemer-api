package br.com.rhiemer.api.util.thread;

import javax.enterprise.context.Dependent;

import br.com.rhiemer.api.util.annotations.bean.BeanDiscoveryEJB;
import br.com.rhiemer.api.util.trace.TransactionContext;

@Dependent
@BeanDiscoveryEJB(classe = SessionThreadContextAPI.class, app = "beerpoints", modulo = "rhiemer-api-ejb", beanName = "sessionThreadContextAPI")
public interface SessionThreadContextAPI extends ThreadContextAPI {

	public void setTransactionContext(TransactionContext transactionContext);

}