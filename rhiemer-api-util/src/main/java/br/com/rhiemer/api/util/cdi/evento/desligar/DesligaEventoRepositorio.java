package br.com.rhiemer.api.util.cdi.evento.desligar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.rhiemer.api.util.helper.Helper;

public class DesligaEventoRepositorio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8753951217846508637L;

	private Map<String, List<ParametrosBuscaDesligamentoEventoDto>> map = new HashMap<>();

	public void addDesligamentoEventoDto(DesligamentoEventoDto param) {
		List<ParametrosBuscaDesligamentoEventoDto> lista = Helper.putMapToKey(map, param.getChaveMetodo(),
				new ArrayList<>());
		ParametrosBuscaDesligamentoEventoDto dto = new ParametrosBuscaDesligamentoEventoDto(param);
		lista.add(dto);
	}

	public Boolean temDesligamentoEvento(ParametrosBuscaDesligamentoEventoDto parametro) {
		for (Map.Entry<String, List<ParametrosBuscaDesligamentoEventoDto>> entryParametro : map.entrySet()) {
			ParametrosBuscaDesligamentoEventoDto parametrosBuscaDesligamentoEventoDto = Helper
					.getListByKey(entryParametro.getValue(), parametro);
			if (parametrosBuscaDesligamentoEventoDto != null) {
				if (parametrosBuscaDesligamentoEventoDto.getValores() == null
						|| parametrosBuscaDesligamentoEventoDto.getValores().size() == 0) {
					return true;
				} else if (parametro.getValores() != null) {
					boolean temChaveValor = Helper.mapTemChaveValor(parametro.getValores(),
							parametrosBuscaDesligamentoEventoDto.getValores());
					if (temChaveValor)
						return true;
				}
			}

		}

		return false;

	}

	public void removeDesligamentoEventoDto(String chave) {
		map.remove(chave);
	}

}
