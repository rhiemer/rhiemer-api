package br.com.rhiemer.api.ejb.desligar.evento;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import br.com.rhiemer.api.util.cdi.desligar.evento.DesligamentoEventoContext;
import br.com.rhiemer.api.util.cdi.desligar.evento.DesligamentoEventoDto;
import br.com.rhiemer.api.util.cdi.desligar.evento.ParametrosBuscaDesligamentoEventoDto;

@Stateless(name = "desligarEventoContext", mappedName = "desligarEventoContext")
@Remote(DesligamentoEventoContext.class)
public class DesligarEventoContextManager implements DesligamentoEventoContext {

	private final ThreadLocal<Map<String, Map<String, Map<String, Object>>>> threadLocal = new ThreadLocal<>();

	@PostConstruct
	private void postConstruct() {
		threadLocal.set(new HashMap<>());
	}

	@Override
	public Boolean temDesligamentoEvento(ParametrosBuscaDesligamentoEventoDto parametro) {
		for (Map.Entry<String, Map<String, Map<String, Object>>> entryParametro : threadLocal.get().entrySet()) {
			for (Map.Entry<String, Map<String, Object>> entryEvento : entryParametro.getValue().entrySet()) {

				if (parametro.getChaveEvento().equalsIgnoreCase(entryEvento.getKey())) {
					if (entryEvento.getValue() == null || entryEvento.getValue().size() == 0)
						return true;
					if (parametro.getChavesParametro().equals(entryEvento.getValue())) {
						return true;
					}
				}
			}
		}

		return false;

	}

	@Override
	public void addDesligamentoEventoDto(DesligamentoEventoDto dto) {
		Map<String, Map<String, Object>> mapaMetodo = threadLocal.get().get(dto.getChaveMetodo());
		if (mapaMetodo == null) {
			mapaMetodo = new HashMap<>();
			mapaMetodo.put(dto.getChaveMetodo(), new HashMap<>());
		}
		Map<String, Object> mapChavesEvento = mapaMetodo.get(dto.getChaveEvento());
		if (mapChavesEvento == null) {
			mapChavesEvento = new HashMap<>();
			mapChavesEvento.put(dto.getChaveEvento(), new HashMap<>());
		}
		if (!StringUtils.isBlank(dto.getNomeChaveParametro()))
			mapChavesEvento.put(dto.getNomeChaveParametro(), dto.getValorChaveParametro());
	}

	@Override
	public void removeDesligamentoEventoDto(String chave) {
		threadLocal.get().remove(chave);
	}

}
