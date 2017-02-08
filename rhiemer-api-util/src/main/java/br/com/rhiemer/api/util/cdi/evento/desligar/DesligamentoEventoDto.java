package br.com.rhiemer.api.util.cdi.evento.desligar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
	private Map<String,Object> valores=new HashMap<>();
	
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
	public Map<String,Object> getValores() {
		return valores;
	}
	public void setValores(Map<String,Object> valores) {
		this.valores = valores;
	}

	



}
