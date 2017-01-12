package br.com.rhiemer.api.ejb.thread;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.com.rhiemer.api.util.thread.SessionThreadContextAPI;
import br.com.rhiemer.api.util.trace.TransactionContext;

@Stateless(name = "sessionThreadContextAPI", mappedName = "sessionThreadContextAPI")
@Remote(SessionThreadContextAPI.class)
public class SessionThreadContextAPIManager implements SessionThreadContextAPI, java.rmi.Remote {

	private final ThreadLocal<TransactionContext> contextualMaps = new ThreadLocal<TransactionContext>();

	@Override
	public TransactionContext getTransactionContext() {

		TransactionContext transactionContext = contextualMaps.get();

		if (transactionContext == null) {
			transactionContext = TransactionContext.builder();
			contextualMaps.set(transactionContext);
		}

		return transactionContext;
	}

	@Override
	public void setTransactionContext(TransactionContext transactionContext) {
		contextualMaps.set(transactionContext);

	}

}
