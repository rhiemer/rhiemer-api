package br.com.rhiemer.api.util.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyCallMetodoInterceptorCgLib extends ProxyCallMetodoInterceptor implements MethodInterceptor {

	public ProxyCallMetodoInterceptorCgLib(Object target) {
		super(target);
	}

	@Override
	public Object intercept(Object objeto, Method method, Object[] args, MethodProxy pMethodProxy) throws Throwable {
		return super.call(getTarget(), method, args);
	}

}
