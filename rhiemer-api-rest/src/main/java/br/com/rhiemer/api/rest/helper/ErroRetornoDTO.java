package br.com.rhiemer.api.rest.helper;

import java.io.Serializable;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * DTO para retorno de erro
 * 
 * @author rodrigo.hiemer
 *
 */
@SuppressWarnings("serial")
public class ErroRetornoDTO implements Serializable {

	private String tipo;
	private String descricao;
	private String complemento;
	private String rootCause;

	public ErroRetornoDTO(String tipo, String descricao, String complemento) {
		super();
		this.tipo = tipo;
		this.descricao = descricao;
		this.complemento = complemento;
	}

	public ErroRetornoDTO(String tipo, String descricao, Exception exception) {
		this.tipo = tipo;
		this.descricao = descricao;
		this.complemento = exception.getMessage();
		this.rootCause = Optional.ofNullable(ExceptionUtils.getRootCause(exception))
				.map(t -> t.getClass().getName().concat(": ").concat(t.getMessage())).get();
	}

	public ErroRetornoDTO() {
		super();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

}
