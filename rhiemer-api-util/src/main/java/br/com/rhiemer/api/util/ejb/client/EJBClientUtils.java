package br.com.rhiemer.api.util.ejb.client;

import org.jboss.ejb.client.EJBClient;
import org.jboss.ejb.client.EJBLocator;
import org.jboss.ejb.client.StatelessEJBLocator;

public final class EJBClientUtils {

	private EJBClientUtils() {
	}

	public static EJBLocator<?> createEJBLocator(EJBClientDto ejbClientDto) {
		switch (ejbClientDto.getTipo()) {
		case STATELESS:
			return new StatelessEJBLocator(ejbClientDto.getClasse(), ejbClientDto.getApp(), ejbClientDto.getModulo(),
					ejbClientDto.getBeanName(), ejbClientDto.getDistinctName());

		case STATEFUL:

			try {
				return EJBClient.createSession(ejbClientDto.getClasse(), ejbClientDto.getApp(),
						ejbClientDto.getModulo(), ejbClientDto.getBeanName(), ejbClientDto.getDistinctName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		}

		return null;

	}

	public static Object createProxy(EJBClientDto ejbClientDto) {

		EJBLocator locator = createEJBLocator(ejbClientDto);
		Object proxy = EJBClient.createProxy(locator);
		return proxy;

	}

}
