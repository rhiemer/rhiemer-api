package br.com.rhiemer.api.util.dto;

import java.io.Serializable;

public class DadosRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1933331252010035542L;
	
	private String nomeUrl;
	private String servletPath;
	private String requestURL;

	public String getNomeUrl() {
		return nomeUrl;
	}

	public void setNomeUrl(String nomeUrl) {
		this.nomeUrl = nomeUrl;
	}

	public String getServletPath() {
		return servletPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

}
