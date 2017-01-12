package br.com.rhiemer.api.util.cdi.bean;

import br.com.rhiemer.api.util.ejb.client.EJBClientDto;
import br.com.rhiemer.api.util.ejb.client.EJBClientUtils;

public class BeanEJB<T> extends BeanAPI<T> {

	
	private final EJBClientDto eJBClientDto;

	public BeanEJB(final EJBClientDto eJBClientDto) {
		super((Class<T>)eJBClientDto.getClasse());
		this.eJBClientDto = eJBClientDto;
	}

	public BeanEJB(final EJBClientDto eJBClientDto, final Class<?> proxyType) {
		super((Class<T>)eJBClientDto.getClasse(),proxyType);
		this.eJBClientDto = eJBClientDto;

	}

	public EJBClientDto geteJBClientDto() {
		return eJBClientDto;
	}
	
	@Override
	protected Object createClass() {
		Object objetoProxyEJB = EJBClientUtils.createProxy(eJBClientDto);
	    return objetoProxyEJB;	
	}

	
}
