package br.com.rhiemer.api.util.helper;

import java.io.InputStream;
import java.net.URL;

import br.com.rhiemer.api.util.exception.APPIllegalArgumentException;

public class HelperClassLoader {

	private HelperClassLoader() {

	}

	public static ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static InputStream getResourceAsStream(String path, Class<?> classe) {
		InputStream is = null;

		try {
			is = classe.getResourceAsStream(path);
		} catch (Exception e) {
		}

		if (is == null) {
			try {
				is = classe.getClassLoader().getResourceAsStream(path);
			} catch (Exception e) {
			}
		}

		if (is == null) {
			try {
				is = getContextClassLoader().getResourceAsStream(path);
			} catch (Exception e) {

			}
		}

		if (is == null)
			throw new APPIllegalArgumentException("ResourceStream não encontrado em nenhum classLoader. {}", path);

		return is;

	}

	public static InputStream getResourceAsStream(String path) {
		return getResourceAsStream(path, HelperClassLoader.class);
	}

	public static URL getResource(String path, Class<?> classe) {
		URL url = null;

		try {
			url = classe.getResource(path);
		} catch (Exception e) {
		}

		if (url == null) {
			try {
				url = classe.getClassLoader().getResource(path);
			} catch (Exception e) {
			}
		}

		if (url == null) {
			try {
				url = getContextClassLoader().getResource(path);
			} catch (Exception e) {

			}
		}

		if (url == null)
			throw new APPIllegalArgumentException("Resource não encontrado em nenhum classLoader. {}", path);

		return url;

	}

	public static URL getResource(String path) {
		return getResource(path, HelperClassLoader.class);
	}
}
