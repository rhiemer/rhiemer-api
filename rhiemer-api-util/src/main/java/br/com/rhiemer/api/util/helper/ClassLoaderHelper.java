package br.com.rhiemer.api.util.helper;

public final class ClassLoaderHelper {

	private ClassLoaderHelper() {
	}

	public static ClassLoader classLoaderAplicacao() {
		final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		return (contextClassLoader == null ? ClassLoader.getSystemClassLoader() : contextClassLoader);
	}

}
