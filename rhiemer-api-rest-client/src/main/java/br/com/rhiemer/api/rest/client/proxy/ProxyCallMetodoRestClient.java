package br.com.rhiemer.api.rest.client.proxy;

import java.lang.reflect.Method;

import br.com.rhiemer.api.util.helper.JsonHelper;
import br.com.rhiemer.api.util.proxy.ProxyCallMetodoHandlerJavassit;

public class ProxyCallMetodoRestClient extends ProxyCallMetodoHandlerJavassit {

	public ProxyCallMetodoRestClient(Object target) {
		super(target);
	}

	protected Object converter(Object objeto,Method thisMethod) {
		Object objetoConvertido = JsonHelper.toObject(objeto.toString(), thisMethod.getReturnType());
		return objetoConvertido;
	}
	
	@Override
	public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
		Object objetoResultadoRest = super.invoke(self, thisMethod, proceed,args);
		if (objetoResultadoRest != null &&  !thisMethod.getReturnType().equals(Void.class)) {
			return converter(objetoResultadoRest,thisMethod);

		} else
			return objetoResultadoRest;
	}

	

}
