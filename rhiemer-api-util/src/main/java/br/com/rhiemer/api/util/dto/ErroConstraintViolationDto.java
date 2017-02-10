package br.com.rhiemer.api.util.dto;

import java.io.Serializable;

import javax.validation.ConstraintViolation;

public class ErroConstraintViolationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8690763239187938923L;

	private String entidade;
	private String bean;
	private String atributo;
	private String valor;
	private String mensagem;

	public ErroConstraintViolationDto(ConstraintViolation<?> cv) {
		super();
		this.entidade = cv.getRootBeanClass() == null ? null : cv.getRootBeanClass().getName();
		this.bean = cv.getRootBean() == null ? null : cv.getRootBean().toString();
		this.atributo = cv.getPropertyPath() == null ? null : cv.getPropertyPath().toString();
		this.valor = cv.getInvalidValue() == null ? null : cv.getInvalidValue().toString();
		this.mensagem = cv.getMessage();

	}

	public String getEntidade() {
		return entidade;
	}

	public String getBean() {
		return bean;
	}

	public String getAtributo() {
		return atributo;
	}

	public String getValor() {
		return valor;
	}

	public String getMensagem() {
		return mensagem;
	}

}
