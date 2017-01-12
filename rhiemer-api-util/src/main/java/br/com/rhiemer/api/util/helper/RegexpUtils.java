package br.com.rhiemer.api.util.helper;

import org.apache.commons.lang3.StringUtils;

/**
 * Projeto SAD <br>
 * Esta classe � um ajudante para montar string de express�o regular para
 * pesquisa por campo, considerando os acentos e caixa
 * 
 * 
 */
public final class RegexpUtils {

	/**
	 * Construtor privado para n�o permitir inst�ncias
	 */
	private RegexpUtils() {
	}

	/**
	 * Formata uma se��o para bloco regular expression, usando as classes de
	 * equival�ncia do Regexp do Oracle.<br>
	 * Ex: a => [[=a=]]<br>
	 * <br>
	 * 
	 * � feito um tratamento especial para o carectere <b>%</b>, para fazer um
	 * tratamento semelhante ao like
	 * 
	 * @param texto
	 *            Texto a ser formatado
	 * @return Texto formatado
	 */
	private static String formatar(final String texto) {
		return "%".equals(texto) ? ".*" : String.format("[[=%s=]]", texto);
	}

	/**
	 * Prepara a string de pesquisa como express�o regular, para que a busca
	 * seja feita independente de caixa e considerando os caracteres acentuados
	 * mesmo que na string de pesquisa n�o seja digitado.<br>
	 * <br>
	 * 
	 * <b>Nota: </b> A express�o regular � para o formato do select do Oracle
	 * 
	 * @param arg
	 *            A string a ser processada
	 * @return A string no formato de express�o regular.<br>
	 *         Ex: carro => [[=c=]][[=a=]][[=r=]][[=r=]][[=o=]]
	 */
	public static String prepararRegexp(String arg) {
		final StringBuilder ret = new StringBuilder();
		final String pesq = StringUtils.trimToEmpty(arg);

		for (int i = 0; i < pesq.length(); i++) {
			ret.append(formatar(pesq.substring(i, i + 1)));
		}

		return ret.toString();
	}

}
