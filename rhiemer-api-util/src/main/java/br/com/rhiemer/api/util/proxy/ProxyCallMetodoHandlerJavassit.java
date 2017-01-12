package br.com.rhiemer.api.util.proxy;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;

public class ProxyCallMetodoHandlerJavassit extends ProxyCallMetodoInterceptor implements MethodHandler {

	public ProxyCallMetodoHandlerJavassit(Object target) {
		super(target);
	}

	@Override
	public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		return super.call(getTarget(), thisMethod, args);
	}

}
