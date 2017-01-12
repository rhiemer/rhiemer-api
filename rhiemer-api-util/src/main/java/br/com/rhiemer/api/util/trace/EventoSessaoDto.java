package br.com.rhiemer.api.util.trace;

import br.com.rhiemer.api.util.enums.EnumTypeEventoSessao;

public class EventoSessaoDto {
	
	private EnumTypeEventoSessao enumTypeEventoSessao;
	private String transactionId;
	private String serviceUUID;
	private String nomeDoMetodoComParametros;
	private String nomeDoMetodo;
	private String codigoServicoInicial;
	private Boolean falha=false;
	
	public EnumTypeEventoSessao getEnumTypeEventoSessao() {
		return enumTypeEventoSessao;
	}
	public void setEnumTypeEventoSessao(EnumTypeEventoSessao enumTypeEventoSessao) {
		this.enumTypeEventoSessao = enumTypeEventoSessao;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getServiceUUID() {
		return serviceUUID;
	}
	public void setServiceUUID(String serviceUUID) {
		this.serviceUUID = serviceUUID;
	}
	public String getNomeDoMetodoComParametros() {
		return nomeDoMetodoComParametros;
	}
	public void setNomeDoMetodoComParametros(String nomeDoMetodoComParametros) {
		this.nomeDoMetodoComParametros = nomeDoMetodoComParametros;
	}
	public String getNomeDoMetodo() {
		return nomeDoMetodo;
	}
	public void setNomeDoMetodo(String nomeDoMetodo) {
		this.nomeDoMetodo = nomeDoMetodo;
	}
	public String getCodigoServicoInicial() {
		return codigoServicoInicial;
	}
	public void setCodigoServicoInicial(String codigoServicoInicial) {
		this.codigoServicoInicial = codigoServicoInicial;
	}
	public Boolean getFalha() {
		return falha;
	}
	public void setFalha(Boolean falha) {
		this.falha = falha;
	}
	

}
