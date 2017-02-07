package br.com.rhiemer.api.util.cdi.evento.desligar;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.rhiemer.api.util.annotations.entity.Chave;

public class ParametrosBuscaDesligamentoEventoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 226836848409107563L;

	@Chave
	private String chaveEvento;
	private Map<String,Object> chavesParametro=new HashMap<>();
	
	public ParametrosBuscaDesligamentoEventoDto() {
		super();
	}
	
	public ParametrosBuscaDesligamentoEventoDto(String chaveEvento) {
		super();
		this.chaveEvento = chaveEvento;
	}
	public String getChaveEvento() {
		return chaveEvento;
	}
	public void setChaveEvento(String chaveEvento) {
		this.chaveEvento = chaveEvento;
	}
	public Map<String, Object> getChavesParametro() {
		return chavesParametro;
	}
	



}
