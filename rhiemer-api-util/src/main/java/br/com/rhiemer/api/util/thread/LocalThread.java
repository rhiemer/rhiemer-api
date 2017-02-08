package br.com.rhiemer.api.util.thread;

import br.com.rhiemer.api.util.annotations.cdi.ThreadScoped;
import br.com.rhiemer.api.util.trace.TransactionContext;

@ThreadScoped
public class LocalThread implements LocalThreadContextAPI {

	private TransactionContext transactionContext;
	
	@Override
	public TransactionContext getTransactionContext() {	
		if (transactionContext == null) {
			transactionContext = TransactionContext.builder();
		}
		return transactionContext;
	}

	
}
