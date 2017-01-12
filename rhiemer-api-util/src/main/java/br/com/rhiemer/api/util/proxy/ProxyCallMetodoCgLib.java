package br.com.rhiemer.api.util.proxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

public class ProxyCallMetodoCgLib extends ProxyCallMetodo {

	protected Callback callBack;

	public ProxyCallMetodoCgLib() {
		super();
	}

	public ProxyCallMetodoCgLib(Object target) {
		super(target);
	}

	public ProxyCallMetodoCgLib(Object target, Class<?>... classesBean) {
		super(target, classesBean);

	}

	public ProxyCallMetodoCgLib(Object target, Callback callBack, Class<?>... classesBean) {
		super(target, classesBean);
		setCallBack(callBack);
	}

	protected Callback callBackPadrao() {
		return new ProxyCallMetodoInterceptorCgLib(target);
	}

	public Object builder() {
		Callback _callBack = getCallBack();
		if (_callBack == null)
			_callBack = callBackPadrao();
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(_callBack);

		Class superClass = getSuperClass();
		if (superClass != null)
			enhancer.setSuperclass(superClass);
		

		Class[] interfaces = getInterfaces();
		if (interfaces != null && interfaces.length > 0)
			enhancer.setInterfaces(interfaces);

		Object proxyInstance = enhancer.create();
		return proxyInstance;
	}

	public Callback getCallBack() {
		return callBack;
	}

	public void setCallBack(Callback callBack) {
		this.callBack = callBack;
	}

}
