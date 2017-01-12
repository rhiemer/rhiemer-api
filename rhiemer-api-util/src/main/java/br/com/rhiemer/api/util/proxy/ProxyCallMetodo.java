package br.com.rhiemer.api.util.proxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.rhiemer.api.util.helper.Helper;

public abstract class ProxyCallMetodo {

	protected Class<?>[] classesBean;
	protected Object target;

	public ProxyCallMetodo() {
		super();
		if (Helper.isClasseParametrizada(this.getClass())) {
			Class classePrincipal = Helper.getClassPrincipal(this.getClass());
			classesBean = new Class[] { classePrincipal };
		}
	}

	public ProxyCallMetodo(Object target) {
		super();
		setTarget(target);
	}

	public ProxyCallMetodo(Object target, Class<?>... classesBean) {
		super();
		setClassesBean(classesBean);
		setTarget(target);
	}

	protected Class<?> getSuperClass() {
		if (classesBean == null || classesBean.length == 0) {
			return target.getClass();
		} else if (!classesBean[0].isInterface()) {
			return classesBean[0];
		}
		return null;

	}

	protected Class[] getInterfaces() {
		if (classesBean != null && classesBean.length != 0) {
			Class superClass = getSuperClass();
			if (superClass != null) {
				List<Class<?>> listaInterfaces = new ArrayList<>(Arrays.asList(classesBean));
				listaInterfaces.remove(0);
				return listaInterfaces.toArray(new Class[] {});
			} else
				return classesBean;
		}
		return null;

	}

	public abstract Object builder();

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Class<?>[] getClassesBean() {
		return classesBean;
	}

	public void setClassesBean(Class<?>... classesBean) {
		this.classesBean = classesBean;
	}

}
