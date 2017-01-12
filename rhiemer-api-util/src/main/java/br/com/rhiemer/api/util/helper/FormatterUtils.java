package br.com.rhiemer.api.util.helper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

/**
 * Projeto SAD
 * Conjunto de fun��es auxiliares para formata��o de dados em String.
 * @author  DBA Engenharia de Sistemas Ltda
 * @version 1.0
 */
public final class FormatterUtils {
    private static final int TAM_CPF = 11;
    private static final int TAM_CNPJ = 14;
    private static final int TAM_CEP = 8;
    private static final int TAM_FONE_DDD = 3;
    private static final int TAM_FONE_DDI = 3;
    private static final int TAM_FONE_DDD_2 = 2;
    private static final int TAM_FONE_DDI_2 = 2;    
    private static final int TAM_FONE_NUM = 8;
    
    /**
     * Construtor padr�o
     */
    private FormatterUtils() {
    	// N�o permite o instanciar esta classe.
    }
    
    /**
     * Retorna uma <code>String</code> com a data atual no formato especificado.
     * @param formato   formato desejado no padr�o <code>SimpleDateFormat</code>
     * @see java.text.SimpleDateFormat
     */
    public static String dataHoraAtual(final String formato) {
    	String retorno;
        retorno = formato.replace('A', 'y');
        retorno = retorno.replace('D', 'd');
        retorno = retorno.replace('h', 'H');
        final SimpleDateFormat sdf = new SimpleDateFormat(retorno, Locale.getDefault());

        return sdf.format(new Date());
    }

	/**
	 * Alinha o texto � esquerda
	 * @param str O texto a ser formatado
	 * @param tam O tamanho que o texto dever� ter
	 * @param chr O caractere de preenchimento
	 * @return O texto formatado
	 */
    public static String alinhaEsquerda(final String str, final int tam, final char chr) {
    	return StringUtils.rightPad(str, tam, chr);
	}
    
    /**
     * Alinha o texto � esquerda, preenchendo com espa�os
     * @param str O texto a ser formatado
     * @param tam O tamanho que o texto dever� ter
     * @return O texto formatado
     */
    public static String alinhaEsquerda(final String str, final int tam) {
        return alinhaEsquerda(str, tam, ' ');
    }

    /**
	 * Alinha o texto � direita
	 * @param str O texto a ser formatado
	 * @param tam O tamanho que o texto dever� ter
	 * @param chr O caractere de preenchimento
	 * @return O texto formatado
	 */
    public static String alinhaDireita(final String str, final int tam, final char chr) {
    	return StringUtils.leftPad(str, tam, chr);
    }
    
    /**
     * Alinha o texto � direita, preenchendo com espa�os
     * @param str O texto a ser formatado
     * @param tam O tamanho que o texto dever� ter
     * @return O texto formatado
     */
    public static String alinhaDireita(final String str, final int tam) {
        return alinhaDireita(str, tam, '0');
    }
    
    /**
     * Formata um texto de acordo com uma m�scara
     * @param mask A m�scara de formata��o
     * @param str O texto a ser formatado
     * @return O texto formatado, ou <code>""</code> caso n�o seja poss�vel aplicar a m�scara
     * @see MaskFormatter
     */
    private static String valMascarado(final String mask, final String str) {
    	String ret;
		
		try {
			final MaskFormatter fmt = new MaskFormatter(mask);
			fmt.setValueContainsLiteralCharacters(false);
			ret = fmt.valueToString(str);
		}
		catch (ParseException ex) {
			ret = "";
		}
		
		return ret;
    }

	/**
	 * Formata o texto de acordo com a m�scara de CPF
	 * @param cpf O texto sem m�scara
	 * @return O texto formatado como CPF
	 */
    public static String cpf(final String cpf) {
		return valMascarado("###.###.###-##", alinhaDireita(cpf, TAM_CPF, '0'));
	}

    /**
	 * Formata o texto de acordo com a m�scara de CNPJ
	 * @param cnpj O texto sem m�scara
	 * @return O texto formatado como CNPJ
	 */
    public static String cnpj(final String cnpj) {
		return valMascarado("##.###.###/####-##", alinhaDireita(cnpj, TAM_CNPJ, '0'));
	}

    /**
	 * Formata o texto de acordo com a m�scara de CEP
	 * @param cep O texto sem m�scara
	 * @return O texto formatado como CEP
	 */
    public static String cep(final String cep) {
    	return valMascarado("#####-###", alinhaDireita(cep, TAM_CEP, '0'));
    }

    /**
	 * Formata o texto como um DDD de telefone
	 * @param fone O texto sem m�scara
	 * @return O texto formatado como DDD de telefone
	 */
    public static String foneDdd(final String fone) {
        return alinhaDireita(fone == null ? "" : fone, TAM_FONE_DDD, '0');
    }
    
    /**
	 * Formata o texto como um DDI de telefone
	 * @param fone O texto sem m�scara
	 * @return O texto formatado como DDD de telefone
	 */
    public static String foneDdi(final String fone) {
    	return alinhaDireita(fone == null ? "" : fone, TAM_FONE_DDI, '0');
    }

    /**
	 * Formata o texto como um DDD de telefone
	 * @param fone O texto sem m�scara
	 * @return O texto formatado como DDD de telefone
	 */
    public static String foneDddDoisDigitos(final String fone) {
        return alinhaDireita(fone == null ? "" : fone, TAM_FONE_DDD_2, '0');
    }
    
    /**
	 * Formata o texto como um DDI de telefone
	 * @param fone O texto sem m�scara
	 * @return O texto formatado como DDD de telefone
	 */
    public static String foneDdiDoisDigitos(final String fone) {
    	return alinhaDireita(fone == null ? "" : fone, TAM_FONE_DDI_2, '0');
    }
    
    
    /**
	 * Formata o texto como um n�mero de telefone
	 * @param fone O texto sem m�scara
	 * @return O texto formatado como n�mero de telefone
	 */
    public static String foneNum(final String fone) {
        return alinhaDireita(fone == null ? "" : fone, TAM_FONE_NUM, '0');
    }
    
    /**
     * Retira do texto os caracteres que n�o sejam d�gitos
     * @param str O texto a ser formatado
     * @return O texto contendo somente d�gitos
     */
    public static String numeroSemFormato(final String str) {
    	return StringUtils.trimToEmpty(str).replaceAll("\\D", "");
    }
    
    /**
     * Limita o texto a um tamanho espec�fico, adicionando um marcador
     * de fim caso o tamanho do texto seja maior que o desejado
     * @param texto O texto a ser formatado
     * @param tamanho Tamanho ideal do texto
     * @param caracter Marcador de interrup��o do texto caso seja maior que o tamanho ideal
     * @return O texto formatado
     */
    public static String limitaTexto(final String texto, final Integer tamanho, final String caracter) {
    	String retorno;
    	if (StringUtils.isNotBlank(texto)) {
    		retorno = texto.length() > tamanho ? texto.substring(0, tamanho-1) + caracter : texto;
    	} else {
    		retorno = "";
    	}
    	return retorno;
    }
}