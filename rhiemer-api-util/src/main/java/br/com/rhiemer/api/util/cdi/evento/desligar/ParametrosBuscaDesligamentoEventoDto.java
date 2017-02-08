package br.com.rhiemer.api.util.cdi.evento.desligar;

import java.util.HashMap;
import java.util.Map;

import br.com.rhiemer.api.util.annotations.entity.Chave;
import br.com.rhiemer.api.util.helper.Helper;
import br.com.rhiemer.api.util.pojo.PojoKeyAbstract;

public class ParametrosBuscaDesligamentoEventoDto extends PojoKeyAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 226836848409107563L;

	@Chave
	private String chaveEvento;
	private Map<Object, Object> valores = new HashMap<>();

	public ParametrosBuscaDesligamentoEventoDto() {
		super();
	}

	public ParametrosBuscaDesligamentoEventoDto(DesligamentoEventoDto dto) {
		super();
		setChaveEvento(dto.getChaveEvento());
		valores.putAll(dto.getValores());
	}

	public String getChaveEvento() {
		return chaveEvento;
	}

	public Map<Object, Object> getValores() {
		return valores;
	}

	public void setValores(Map<Object, Object> valores) {
		this.valores = valores;
	}

	public void setChaveEvento(String chaveEvento) {
		this.chaveEvento = chaveEvento;
	}

	public boolean comparaChave(String chave, Object valor) {
		return Helper.mapTemChaveValor(valores, chave, valor);

	}

}
