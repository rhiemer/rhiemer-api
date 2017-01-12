package br.com.rhiemer.api.util.proxy;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class ProxyCallMetodoJavassist extends ProxyCallMetodo {

	protected MethodHandler methodHandler;



	public ProxyCallMetodoJavassist() {
		super();
	}

	public ProxyCallMetodoJavassist(Object target) {
		super(target);
	}

	public ProxyCallMetodoJavassist(Object target, Class<?>... classesBean) {
		super(target, classesBean);

	}

	public ProxyCallMetodoJavassist(Object target, MethodHandler methodHandler, Class<?>... classesBean) {
		super(target, classesBean);
		setMethodHandler(methodHandler);
	}
	
	protected MethodHandler methodHandlerPadrao() {
		return new ProxyCallMetodoHandlerJavassit(target);
	}

	public Object builder() {
		ProxyFactory factory = new ProxyFactory();
		Class superClass = getSuperClass();
		if (superClass != null)
			factory.setSuperclass(superClass);

		Class[] interfaces = getInterfaces();
		if (interfaces != null && interfaces.length > 0)
			factory.setInterfaces(interfaces);
		Class aClass = factory.createClass();
		Object newInstance = null;
		try {
			newInstance = aClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		MethodHandler _methodHandler = getMethodHandler();
		if (_methodHandler== null)
			_methodHandler = new ProxyCallMetodoHandlerJavassit(target);
		((ProxyObject) newInstance).setHandler(_methodHandler);
		return newInstance;
	}

	public MethodHandler getMethodHandler() {
		return methodHandler;
	}

	public void setMethodHandler(MethodHandler methodHandler) {
		this.methodHandler = methodHandler;
	}

	

}
