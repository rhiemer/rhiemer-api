package br.com.rhiemer.api.rest.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DTO para retorno de erro
 * 
 * @author rodrigo.hiemer
 *
 */
@SuppressWarnings("serial")
public class ErroRetornoListaMapDTO extends ErroRetornoDTO {

	private List<Map<String, Object>> listaMap = new ArrayList<>();

	public ErroRetornoListaMapDTO(String tipo, String descricao, String complemento) {
		super(tipo, descricao, complemento);
	}

	public ErroRetornoListaMapDTO(String tipo, String descricao, String complemento,
			List<Map<String, Object>> listaMap) {
		super(tipo, descricao, complemento);
		this.listaMap = listaMap;
	}

	public ErroRetornoListaMapDTO() {
		super();
	}

	public List<Map<String, Object>> getListaMap() {
		return listaMap;
	}

	public void setListaMap(List<Map<String, Object>> listaMap) {
		this.listaMap = listaMap;
	}

}
