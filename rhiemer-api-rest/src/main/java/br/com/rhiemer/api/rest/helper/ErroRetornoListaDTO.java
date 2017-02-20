package br.com.rhiemer.api.rest.helper;

import java.util.List;

/**
 * DTO para retorno de erro
 * 
 * @author rodrigo.hiemer
 *
 */
@SuppressWarnings("serial")
public class ErroRetornoListaDTO extends ErroRetornoDTO {

	private List<?> lista;

	public ErroRetornoListaDTO(String tipo, String descricao, String complemento, List<?> lista) {
		super(tipo, descricao, complemento);
		this.lista = lista;
	}

	public ErroRetornoListaDTO() {
		super();
	}

	public List<?> getLista() {
		return lista;
	}

	public void setLista(List<?> lista) {
		this.lista = lista;
	}

}
