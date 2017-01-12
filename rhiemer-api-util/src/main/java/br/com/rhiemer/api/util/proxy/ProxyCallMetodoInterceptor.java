package br.com.rhiemer.api.util.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;

public abstract class ProxyCallMetodoInterceptor {

	private Object target;

	public ProxyCallMetodoInterceptor(Object target) {
		super();
		this.target = target;
	}

	public Object call(Object objeto, Method method, Object[] args) throws Throwable {
		try {
			if (method.getDeclaringClass() == Object.class)
				return method.invoke(this, args);
			else {

				Class<?> classeTarget = target.getClass();
				LinkedHashSet<Class<?>> listaClassesMetodoTarget = null;
				for (Object arg : args) {
					if (listaClassesMetodoTarget == null)
						listaClassesMetodoTarget = new LinkedHashSet<Class<?>>();
					listaClassesMetodoTarget.add(arg.getClass());
				}

				Method methodTarget = null;
				if (listaClassesMetodoTarget == null)
					methodTarget = classeTarget.getMethod(method.getName(), (Class[]) null);
				else {
					Method methodDefault = null;
					loopMethod: for (Method methodList : classeTarget.getMethods()) {
						if (!method.getName().equals(methodList.getName()))
							continue;
						
						if (methodList.getParameterTypes().length != args.length)
							continue;	

						methodDefault = methodList;
						for (Class<?> classeParam : methodList.getParameterTypes()) {
							for (Object arg : args) {
								if (arg == null || !(arg.getClass().isAssignableFrom(classeParam))
										|| classeParam == Object.class)
									continue loopMethod;
							}
						}

						methodTarget = methodList;
					}

					if (methodTarget == null)
						if (methodDefault != null)
							methodTarget = methodDefault;
						else
							methodTarget = method;

				}
				Object result = methodTarget.invoke(target, args);
				return result;
			}

		} catch (final InvocationTargetException ite) {
			throw ite.getCause();
		}

	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

}
