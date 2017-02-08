package br.com.rhiemer.api.util.thread;

import static br.com.rhiemer.api.util.constantes.ConstantesModulos.MODULO_EJB_SESSION;

import javax.ejb.Remote;
import javax.enterprise.context.Dependent;

import br.com.rhiemer.api.util.annotations.bean.BeanDiscoveryEJB;
import br.com.rhiemer.api.util.trace.TransactionContext;

@Dependent
@BeanDiscoveryEJB(classe = SessionThreadContextAPI.class, modulo = MODULO_EJB_SESSION, beanName = "sessionThreadContextAPI")
@Remote
public interface SessionThreadContextAPI extends ThreadContextAPI {

	public void setTransactionContext(TransactionContext transactionContext);

}