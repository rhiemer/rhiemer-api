package br.com.rhiemer.api.util.criptografia;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DescodecriptografarUtils {

	public static char[] descodecriptografar(String secret) {
		byte[] kbytes = "jaas is the way".getBytes();
		SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");

		BigInteger n = new BigInteger(secret, 16);
		byte[] encoding = n.toByteArray();

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("Blowfish");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
		byte[] decode = null;
		try {
			decode = cipher.doFinal(encoding);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
		return new String(decode).toCharArray();
	}

	public static void main(String args[]) {
		try {
			System.out.println(descodecriptografar(args[0]));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
