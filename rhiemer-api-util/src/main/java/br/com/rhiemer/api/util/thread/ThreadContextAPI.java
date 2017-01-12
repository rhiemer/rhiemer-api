package br.com.rhiemer.api.util.thread;

import br.com.rhiemer.api.util.trace.TransactionContext;

public interface ThreadContextAPI {


	TransactionContext getTransactionContext();

}