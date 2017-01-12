package br.com.rhiemer.api.util.helper;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import sun.misc.BASE64Decoder;

/**
 * Reúne funções utilitárias relacionadas a textos *
 * 
 * 
 */
@SuppressWarnings("restriction")
public class TextUtils {

	public static String[] decodeBase64(String cod) {
		String[] valores = new String[2];
		BASE64Decoder decoder = new BASE64Decoder();

		try {
			byte[] bts = decoder.decodeBuffer(cod);
			String valor = new String(bts);
			valores = valor.split(":");

			return valores;

		} catch (IOException e) {

		}
		return null;
	}

	/**
	 * Confere se a String é um valor numérico
	 * 
	 * @param str
	 *            texto a ser validado
	 * @return TRUE se for um número
	 */
	public static boolean eNumerico(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	/**
	 * Confere se a String é um valor numérico inteiro
	 * 
	 * @param str
	 *            texto a ser validado
	 * @return TRUE se for um número
	 */
	public static boolean eNumericoInteiro(String str) {
		return str.matches("-?\\d+(\\d+)?");
	}

	/**
	 * Confere se a String é um valor numérico inteiro não negativo
	 * 
	 * @param str
	 *            texto a ser validado
	 * @return TRUE se for um número
	 */
	public static boolean eNumericoInteiroNaoNegativo(String str) {
		return str.matches("\\d+");
	}

	public static String formataCpf(String cpf) {
		if (cpf.length() < 11) {
			cpf = StringUtils.leftPad(cpf, 11, '0');
		} else if (cpf.length() > 11) {
			throw new RuntimeException("CPF excedeu o tamanho esperado: 11");
		}

		return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
	}

	public static String formataCnpj(String cnpj) {
		if (cnpj.length() < 14) {
			cnpj = StringUtils.leftPad(cnpj, 14, '0');
		} else if (cnpj.length() > 14) {
			throw new RuntimeException("CNPJ excedeu o tamanho esperado: 14");
		}

		return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + cnpj.substring(5, 8) + "/"
				+ cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
	}

	/***
	 * Verifica se o CNPJ é válido mediante cálculos específicos e comparação
	 * com seus dígitos verificadores
	 * 
	 * @param cnpj
	 *            CNPJ (sem caracteres especiais, pontuação, etc) que se queira
	 *            checar validade
	 * 
	 * @return TRUE se o CNPJ é válido considerando seus dígitos verificadores
	 *         ou FALSE caso contrário
	 */
	public static boolean eCNPJValido(String cnpj) {
		final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

		if ((cnpj == null) || (cnpj.length() != 14))
			return false;

		Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}

	/***
	 * Verifica se o CPF é válido mediante cálculos específicos e comparação com
	 * seus dígitos verificadores
	 * 
	 * @param cnpj
	 *            CNPJ (sem caracteres especiais, pontuação, etc) que se queira
	 *            checar validade
	 * 
	 * @return TRUE se o CNPJ é válido considerando seus dígitos verificadores
	 *         ou FALSE caso contrário
	 */
	public static boolean eCPFValido(String cpf) {
		final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

		if ((cpf == null) || (cpf.length() != 11))
			return false;

		Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}

	/***
	 * Cálculo de dígito verificador compartilhada na validação de CNPJ e CPF
	 * 
	 * @param str
	 *            CPF/CNPJ validado
	 * @param peso
	 *            array de pesos utilizados para se validar CPF/CNPJ
	 * @return valor do dígito verificador
	 */
	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

}
