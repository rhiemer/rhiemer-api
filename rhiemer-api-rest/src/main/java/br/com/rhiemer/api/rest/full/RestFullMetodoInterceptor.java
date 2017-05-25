package br.com.rhiemer.api.rest.full;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import br.com.rhiemer.api.util.annotations.rest.RESTful;
import br.com.rhiemer.api.util.annotations.rest.RestClientMetodoClasse;
import br.com.rhiemer.api.util.helper.JsonHelper;
import br.com.rhiemer.api.util.proxy.ProxyCallMetodoHandlerJavassit;
import br.com.rhiemer.api.util.reflection.PackageEntities;

public class RestFullMetodoInterceptor extends ProxyCallMetodoHandlerJavassit {
	
	private final static PackageEntities packageEntities = new PackageEntities();
	static
	{
		packageEntities.setClasseMap(RESTful.class);
		
	}

	public RestFullMetodoInterceptor(Object target) {
		super(target);
	}

	protected Object converter(Object objeto,Method thisMethod, Object[] args) {
		
		
		for (int i = 0; i < thisMethod.getParameterAnnotations().length; i++) {

			Annotation[] annotations = thisMethod.getParameterAnnotations()[i];
			Object parameter = args[i];

			for (Annotation annotation : annotations) {
				if (RestClientMetodoClasse.class.isInstance(annotation)) {
					Class<?> classeResultado = packageEntities.getNotFound(parameter);
					Object objetoConvertido = JsonHelper.toObject(objeto, classeResultado);
					return objetoConvertido;
				}

			}
		}
		
		return objeto;
		
	}

	@Override
	public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
		Object objetoResultadoRest = super.invoke(self, thisMethod, proceed, args);
		if (objetoResultadoRest != null && !Void.class.isInstance(objetoResultadoRest)) {
			return converter(objetoResultadoRest, thisMethod,args);

		} else
			return objetoResultadoRest;
	}

}
