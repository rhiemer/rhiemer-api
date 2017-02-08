package br.com.rhiemer.api.ejb.desligar.evento;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import br.com.rhiemer.api.util.cdi.evento.desligar.DesligaEventoRepositorio;
import br.com.rhiemer.api.util.cdi.evento.desligar.DesligamentoEventoContext;
import br.com.rhiemer.api.util.cdi.evento.desligar.DesligamentoEventoDto;
import br.com.rhiemer.api.util.cdi.evento.desligar.ParametrosBuscaDesligamentoEventoDto;

@Stateless(name = "desligarEventoContext", mappedName = "desligarEventoContext")
@Remote(DesligamentoEventoContext.class)
public class DesligarEventoContextManager implements DesligamentoEventoContext {

	private final ThreadLocal<DesligaEventoRepositorio> threadLocal = new ThreadLocal<>();

	@Override
	public Boolean temDesligamentoEvento(ParametrosBuscaDesligamentoEventoDto parametro) {
		if (threadLocal.get() == null)
			return false;
		else
			return threadLocal.get().temDesligamentoEvento(parametro);

	}

	@Override
	public void addDesligamentoEventoDto(DesligamentoEventoDto dto) {
		if (threadLocal.get() == null)
			threadLocal.set(new DesligaEventoRepositorio());
		threadLocal.get().addDesligamentoEventoDto(dto);
	}

	@Override
	public void removeDesligamentoEventoDto(String chave) {
		if (threadLocal.get() != null)
			threadLocal.get().removeDesligamentoEventoDto(chave);
	}

}
