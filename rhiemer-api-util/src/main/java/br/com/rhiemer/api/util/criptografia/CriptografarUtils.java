package br.com.rhiemer.api.util.criptografia;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Projeto SAD Classe de apoio a criptografia
 * 
 * @author DBA Engenharia de Sistemas Ltda
 * @since 10/08/2010
 * @version 1.0
 */
public abstract class CriptografarUtils {

	/**
	 * Criptografa a senha.
	 * 
	 * @param senha
	 *            - java.lang.String - senha a ser criptografada.
	 * @return java.lang.String - senha criptografada.
	 */
	public static String criptografar(final String senha) {
		String senhaCifrada = "";
		try {
			final byte[] bites = digest(senha.getBytes());
			senhaCifrada = encodeBase64(bites);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return senhaCifrada;

	}

	/**
	 * Realiza um digest em um array de bytes atrav�s do algoritmo especificado
	 * 
	 * @param input
	 *            - O array de bytes a ser criptografado
	 * @return byte[] - O resultado da criptografia
	 * @throws NoSuchAlgorithmException
	 *             - Caso o algoritmo fornecido n�o seja v�lido
	 */
	private static byte[] digest(final byte[] input) throws NoSuchAlgorithmException {
		final MessageDigest msgDig = MessageDigest.getInstance("MD5");
		msgDig.reset();
		return msgDig.digest(input);
	}

	/**
	 * Converte o array de bytes em uma representa��o de base64.
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static String encodeBase64(final byte[] bytes) throws IOException {
		final byte[] enc = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87,
				88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115,
				116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47, 61 };

		final ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int grt = -1;
		int off = 0;
		int count = 0;

		grt = input.read(buffer, off, 1024 - off);
		while (grt > 0) {
			if (grt >= 3) {
				grt += off;
				for (off = 0; off + 3 <= grt; off += 3) {
					final int chr1 = (buffer[off] & 0xfc) >> 2;
					final int chr2 = (buffer[off] & 3) << 4 | (buffer[off + 1] & 0xf0) >>> 4;
					final int chr3 = (buffer[off + 1] & 0xf) << 2 | (buffer[off + 2] & 0xc0) >>> 6;
					final int chr4 = buffer[off + 2] & 0x3f;
					if (count == 73) {
						out.write(enc[chr1]);
						out.write(enc[chr2]);
						out.write(enc[chr3]);
						out.write(10);
						out.write(enc[chr4]);
						count = 1;
					} else if (count == 74) {
						out.write(enc[chr1]);
						out.write(enc[chr2]);
						out.write(10);
						out.write(enc[chr3]);
						out.write(enc[chr4]);
						count = 2;
					} else if (count == 75) {
						out.write(enc[chr1]);
						out.write(10);
						out.write(enc[chr2]);
						out.write(enc[chr3]);
						out.write(enc[chr4]);
						count = 3;
					} else if (count == 76) {
						out.write(10);
						out.write(enc[chr1]);
						out.write(enc[chr2]);
						out.write(enc[chr3]);
						out.write(enc[chr4]);
						count = 4;
					} else {
						out.write(enc[chr1]);
						out.write(enc[chr2]);
						out.write(enc[chr3]);
						out.write(enc[chr4]);
						count += 4;
					}
				}
				for (int i = 0; i < 3; i++) {
					buffer[i] = i >= grt - off ? 0 : buffer[off + i];
				}
				off = grt - off;
			} else {
				off += grt;
			}
			grt = input.read(buffer, off, 1024 - off);
		}
		if (off == 1) {
			out.write(enc[(buffer[0] & 0xfc) >> 2]);
			out.write(enc[(buffer[0] & 3) << 4 | (buffer[1] & 0xf0) >>> 4]);
			out.write(61);
			out.write(61);
		} else if (off == 2) {
			out.write(enc[(buffer[0] & 0xfc) >> 2]);
			out.write(enc[(buffer[0] & 3) << 4 | (buffer[1] & 0xf0) >>> 4]);
			out.write(enc[(buffer[1] & 0xf) << 2 | (buffer[2] & 0xc0) >>> 6]);
			out.write(61);
		}
		return out.toString("ISO-8859-1");
	}

	public static void main(String args[]) {
		try {
			System.out.println(criptografar(args[0]));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}