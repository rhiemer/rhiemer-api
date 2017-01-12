package br.com.rhiemer.api.util.dto;

import br.com.rhiemer.api.util.pojo.PojoAbstract;

public class Arquivo extends PojoAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = -79936562277860607L;
	
	
	private String nome;
	private String caminho;
	private byte[] conteudo;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	public byte[] getConteudo() {
		return conteudo;
	}
	public void setConteudo(byte[] conteudo) {
		this.conteudo = conteudo;
	}
	

}
