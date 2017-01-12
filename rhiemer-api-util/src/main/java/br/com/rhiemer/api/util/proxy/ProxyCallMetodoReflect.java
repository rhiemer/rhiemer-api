package br.com.rhiemer.api.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import br.com.rhiemer.api.util.helper.ClassLoaderHelper;

public class ProxyCallMetodoReflect extends ProxyCallMetodo {

	protected InvocationHandler handler;

	public ProxyCallMetodoReflect() {
		super();
	}

	public ProxyCallMetodoReflect(Object target) {
		super(target);
	}

	public ProxyCallMetodoReflect(Object target, Class<?>... classesBean) {
		super(target, classesBean);

	}

	public ProxyCallMetodoReflect(Object target, InvocationHandler handler, Class<?>... classesBean) {
		super(target, classesBean);
		setHandler(handler);
	}

	protected InvocationHandler getHandlerPadrao() {
		return new ProxyCallMetodoHandlerReflect(target);
	}

	@Override
	protected Class<?> getSuperClass() {
		return null;

	}

	public Object builder() {
		InvocationHandler _invocationHandle = getHandler();
		if (_invocationHandle == null)
			_invocationHandle = getHandlerPadrao();
		Object proxyInstance = Proxy.newProxyInstance(ClassLoaderHelper.classLoaderAplicacao(), getInterfaces(),
				_invocationHandle);
		return proxyInstance;
	}

	public InvocationHandler getHandler() {
		return handler;
	}

	public void setHandler(InvocationHandler handler) {
		this.handler = handler;
	}

}
