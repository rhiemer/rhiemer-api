package br.com.rhiemer.api.util.helper;

import java.text.Normalizer;

/** 
 * Projeto SAD
 * Classe com fun��es utilit�rias
 * @author DBA Engenharia de Sistemas Ltda
 * @version 1.0
 */
public final class StringUtils {
	
	/**
	 * Construtor padr�o
	 */
	private StringUtils() {
		// N�o permite instanciar
	}
	
	/**
	 * Verifica se o texto informado � uma representa��o de Sim ou N�o.
	 * Case insensitive
	 * @param valor O texto a ser verificado
	 * @return Se o valor � <code>"S"</code> ou <code>"N"</code>
	 */
	public static boolean isBoolean(final String valor) {
		boolean retorno = false;
		if ("S".equalsIgnoreCase(valor) || "N".equalsIgnoreCase(valor)) {
			retorno = true;
		}
		return retorno;
	}
	
	/**
	 * Verifica se o texto informado � um n�mero
	 * @param valor O texto a ser verificado
	 * @return Se � n�mero ou n�o
	 * @see Integer.parseInt
	 * @see Double.parseDouble
	 */
	public static boolean isNumber(final String valor) {
		boolean res = true;
		try {
			Integer.parseInt(valor);
			
			Double.parseDouble(valor);
		} catch (NumberFormatException e) {
			res = false;
		}
		return res;
	}
	
	public static String retiraMascaraCpfCnpj(final String valor){
		String novaStringFmt = Normalizer.normalize(valor, Normalizer.Form.NFD);
		novaStringFmt = retiraString(valor);
		return novaStringFmt;
	}

	public static String retiraString(final String valor) {
		String retorno;
		if (null == valor) {
			retorno = null;
		} else {
			String novaStringFmt = Normalizer.normalize(valor, Normalizer.Form.NFD);
			novaStringFmt = valor.replaceAll("[^0-9]", "");
			retorno = novaStringFmt;
		}
		return retorno;
	}
	
	public static String removeAcentos(String str) {
		if(str != null){
			str = Normalizer.normalize(str, Normalizer.Form.NFD);
		    str = str.replaceAll("[^\\p{ASCII}]", "").trim();
		}
	    return str;
	}
}