package br.com.rhiemer.api.util.cdi.bean;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;

import br.com.rhiemer.api.util.ejb.client.EJBClientDto;
import br.com.rhiemer.api.util.ejb.client.EJBClientUtils;

public class BeanEJB<T> extends BeanAPI<T> {

	private final EJBClientDto eJBClientDto;

	public BeanEJB(final EJBClientDto eJBClientDto, BeanManager bm, AnnotatedType annotatedType) {
		super((Class<T>) eJBClientDto.getClasse(), bm, annotatedType);
		this.eJBClientDto = eJBClientDto;
	}

	public BeanEJB(final EJBClientDto eJBClientDto, final Class<?> proxyType, BeanManager bm,
			AnnotatedType annotatedType) {
		super((Class<T>) eJBClientDto.getClasse(), proxyType, bm, annotatedType);
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
