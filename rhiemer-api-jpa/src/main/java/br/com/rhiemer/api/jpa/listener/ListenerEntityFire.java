package br.com.rhiemer.api.jpa.listener;

import static br.com.rhiemer.api.jpa.constantes.ConstantesDesligarEvenetosJPA.PRE_UPDATE;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import br.com.rhiemer.api.util.annotations.evento.DesligaEventoDestino;
import br.com.rhiemer.api.util.annotations.evento.DesligaEventoInterceptorDestinoDiscovery;

public class ListenerEntityFire {
	
	@Inject
	@Any
	private Event<Object> evento; 
	
	public void fire(ListenerEnum tipo,Object data)
	{
		evento.select(new ListenerQualifier(tipo)).fire(data);
	}

	@DesligaEventoInterceptorDestinoDiscovery
	@DesligaEventoDestino(chaveEvento = PRE_UPDATE)
	public void fireUpdate(ListenerEnum tipo,Object data)
	{
		evento.select(new ListenerQualifier(tipo)).fire(data);
	}
	

}
