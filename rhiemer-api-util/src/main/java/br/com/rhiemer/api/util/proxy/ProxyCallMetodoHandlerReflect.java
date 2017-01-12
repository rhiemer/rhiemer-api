package br.com.rhiemer.api.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyCallMetodoHandlerReflect extends ProxyCallMetodoInterceptor implements InvocationHandler {

	
	public ProxyCallMetodoHandlerReflect(Object target) {
		super(target);
	}

	@Override
	public Object invoke(Object objeto, Method method, Object[] args) throws Throwable {
		return super.call(getTarget(), method, args);

	}
	
}
