package br.com.rhiemer.api.util.trace;

import java.io.Serializable;
import java.util.Optional;
import java.util.Stack;
import java.util.UUID;

public class TransactionContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3027685467068512247L;

	public TransactionContext() {
		super();
	}

	public TransactionContext(String transactionId) {
		super();
		this.transactionId = transactionId;
	}

	public TransactionContext(String transactionId, String serviceUUID) {
		super();
		this.transactionId = transactionId;
		this.serviceUUID = serviceUUID;
		this.codigoServicoInicial = this.serviceUUID;
	}

	public TransactionContext(String transactionId, String requestId, String sessionId, String userId) {
		super();
		this.transactionId = transactionId;
		this.requestId = requestId;
		this.sessionId = sessionId;
		this.userId = userId;
	}

	private String codigoServicoInicial;
	private String transactionId;
	private String requestId;
	private String sessionId;
	private String userId;
	private Stack<String> stackMethods = new Stack<String>();
	private AdvancedStopWatch stopWatch;
	private String serviceUUID;
	private boolean filterEnabled;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Stack<String> getStackMethods() {
		return stackMethods;
	}

	public boolean hasStack() {
		return !stackMethods.isEmpty();
	}

	public AdvancedStopWatch getStopWatch() {
		return stopWatch;
	}

	public void setStopWatch(AdvancedStopWatch stopWatch) {
		this.stopWatch = stopWatch;
	}

	public String prettyPrint() {
		StringBuilder builder = new StringBuilder("[" + transactionId);
		builder.append(" | " + serviceUUID);
		builder.append(" | " + requestId);
		builder.append(" | " + userId);
		builder.append(" | " + sessionId + "]");
		return builder.toString();
	}

	public String getServiceUUID() {
		return serviceUUID;
	}

	public void setServiceUUID(String serviceUUID) {
		if (this.codigoServicoInicial == null)
			this.codigoServicoInicial = serviceUUID;
		this.serviceUUID = serviceUUID;
	}

	public boolean isFilterEnabled() {
		return filterEnabled;
	}

	public void setFilterEnabled(boolean filterEnabled) {
		this.filterEnabled = filterEnabled;
	}

	public static TransactionContext builder() {
		String idThread = UUID.randomUUID().toString().toUpperCase();
		TransactionContext transactionContext = new TransactionContext(idThread);
		AdvancedStopWatch watch = new AdvancedStopWatch(idThread);
		watch = new AdvancedStopWatch(idThread);
		transactionContext.setStopWatch(watch);
		return transactionContext;
	}

	public String getCodigoServicoInicial() {
		return codigoServicoInicial;
	}

	public void setCodigoServicoInicial(String codigoServicoInicial) {
		this.codigoServicoInicial = codigoServicoInicial;
	}

	public boolean verificaCodigoServicoInicial() {
		return Optional.ofNullable(this.codigoServicoInicial).map(t -> t.equalsIgnoreCase(this.serviceUUID)).get();
	}
	
	

}
