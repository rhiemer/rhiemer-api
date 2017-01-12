package br.com.rhiemer.api.util.bundle;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/** 
 * Projeto SAD
 * Classe com fun��es utilit�rias
 * @author DBA Engenharia de Sistemas Ltda
 * @version 1.0
 */
public final class ResourceBundleUtils {
	
	/**
	 * Construtor padr�o
	 */
	private ResourceBundleUtils() {
		// N�o permite instanciar
	}
	
	
	public static String getMessageResourceString(String bundleName, String key, Object params[], Locale locale) {
		String text = null;

		final ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale, getCurrentClassLoader(params));

		try {
			text = bundle.getString(key);
		} catch (MissingResourceException e) {
			text = "?? key " + key + " not found ??";
		}
		if (params != null) {
			final MessageFormat msgFmt = new MessageFormat(text, locale);
			text = msgFmt.format(params, new StringBuffer(), null).toString();
		}
		return text;
	}
	
	protected static ClassLoader getCurrentClassLoader(Object defaultObject) {
		return Thread.currentThread().getContextClassLoader();
	}
	
}