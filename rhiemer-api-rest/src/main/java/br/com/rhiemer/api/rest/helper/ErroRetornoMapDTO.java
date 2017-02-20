package br.com.rhiemer.api.rest.helper;

import java.util.HashMap;
import java.util.Map;


/**
 * DTO para retorno de erro
 * 
 * @author rodrigo.hiemer
 *
 */
@SuppressWarnings("serial")
public class ErroRetornoMapDTO extends ErroRetornoDTO  {

	
    private Map<String,Object> map=new HashMap<>();
    
	public ErroRetornoMapDTO(String tipo, String descricao, String complemento) {
		super(tipo,descricao,complemento);
	}
	
	public ErroRetornoMapDTO(String tipo, String descricao, String complemento,Map<String,Object> map) {
		super(tipo,descricao,complemento);
		this.map=map;
	}

	public ErroRetornoMapDTO() {
		super();
	}

	public Map<String,Object> getMap() {
		return map;
	}

	public void setMap(Map<String,Object> map) {
		this.map = map;
	}

	
	

}
