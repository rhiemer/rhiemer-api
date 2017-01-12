package br.com.rhiemer.api.util.trace;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TraceApplication {

	private Map<String, String> map = new HashMap<>();

	public boolean inicializa(String codigoServico) {
		String idThread = String.valueOf(Thread.currentThread().getId());
		if (map.get(idThread) == null) {
			map.put(idThread, codigoServico);
		}
		return false;

	}

	public boolean finaliza(String codigoServico) {

		String idThread = String.valueOf(Thread.currentThread().getId());
		if (map.get(idThread) != null && map.get(idThread).equals(codigoServico)) {
			map.remove(idThread);
			return true;
		}
		return false;

	}

}
