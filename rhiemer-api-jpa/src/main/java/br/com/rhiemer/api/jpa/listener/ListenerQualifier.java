package br.com.rhiemer.api.jpa.listener;

import javax.enterprise.util.AnnotationLiteral;

import br.com.rhiemer.api.jpa.annotations.ListenerEvent;

public class ListenerQualifier extends AnnotationLiteral<ListenerEvent> implements ListenerEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5495996538082799029L;
	private final ListenerEnum evento;
	
	
	public ListenerQualifier(ListenerEnum evento)
	{
		this.evento=evento;
	}

	

	@Override
	public ListenerEnum value() {
		return this.evento;
	}

	

}
