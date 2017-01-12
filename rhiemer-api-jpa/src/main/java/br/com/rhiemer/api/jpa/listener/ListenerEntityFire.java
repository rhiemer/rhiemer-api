package br.com.rhiemer.api.jpa.listener;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

public class ListenerEntityFire {
	
	@Inject
	@Any
	private Event<Object> evento; 
	
	public void fire(ListenerEnum tipo,Object data)
	{
		evento.select(new ListenerQualifier(tipo)).fire(data);
	}
	

}
