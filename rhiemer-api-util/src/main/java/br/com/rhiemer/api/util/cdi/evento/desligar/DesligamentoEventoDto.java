package br.com.rhiemer.api.util.cdi.evento.desligar;

import java.io.Serializable;

import br.com.rhiemer.api.util.annotations.entity.Chave;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public class DesligamentoEventoDto extends PojoKeyAbstract implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 226836848409107563L;

	@Chave
	private String chaveMetodo;
	@Chave
	private String chaveEvento;
	private String nomeChaveParametro;
	private Object valorChaveParametro;
	
	public String getChaveMetodo() {
		return chaveMetodo;
	}
	public void setChaveMetodo(String chaveMetodo) {
		this.chaveMetodo = chaveMetodo;
	}
	public String getChaveEvento() {
		return chaveEvento;
	}
	public void setChaveEvento(String chaveEvento) {
		this.chaveEvento = chaveEvento;
	}
	public String getNomeChaveParametro() {
		return nomeChaveParametro;
	}
	public void setNomeChaveParametro(String nomeChaveParametro) {
		this.nomeChaveParametro = nomeChaveParametro;
	}
	public Object getValorChaveParametro() {
		return valorChaveParametro;
	}
	public void setValorChaveParametro(Object valorChaveParametro) {
		this.valorChaveParametro = valorChaveParametro;
	}

	



}
