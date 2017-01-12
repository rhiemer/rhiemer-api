package br.com.rhiemer.api.util.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;

import javax.enterprise.inject.spi.CDI;

import br.com.rhiemer.api.util.cdi.qualifier.ProxyBuilderAplicacaoQualifier;
import br.com.rhiemer.api.util.proxy.ProxyMetodoBuilder;
import javassist.util.proxy.ProxyFactory;
import net.sf.cglib.proxy.Enhancer;

public class ProxyUtils {


	public static Class<?> getClassProxiedCdi(Class<?> classe) {
		Class<?> _clazz = null;
		if (isProxiedCdi(classe)) {
			_clazz = classe.getSuperclass();
		} else if (Proxy.isProxyClass(classe)) {
			_clazz = Proxy.getInvocationHandler(classe).getClass();
		} else if (Enhancer.isEnhanced(classe)) {
			_clazz = classe.getSuperclass();
		} else if (ProxyFactory.isProxyClass(classe)) {
			_clazz = getClassProxiedCdi(classe);
		}

		return _clazz;
	}

	public static boolean isProxiedCdi(Class<?> clazzBean) {

		return clazzBean.getCanonicalName().contains("$Proxy$_$$_Weld");
	}
	
	public static <T> T builderBeanCDISelect(Class<T> clazzBean,Annotation...annotations)
	{
		ProxyMetodoBuilder proxyBuilder = CDI.current().select(ProxyMetodoBuilder.class,new ProxyBuilderAplicacaoQualifier()).get();
		Object objetoCdi= CDI.current().select(clazzBean,annotations).get();
		Object bean = proxyBuilder.builderBean(objetoCdi,clazzBean);
		return (T)bean;
		
	}

}
