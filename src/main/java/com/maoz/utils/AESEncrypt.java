package com.maoz.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64;


public class AESEncrypt {

	private final static String alg = "AES";
	private final static String cI = "AES/CBC/PKCS5Padding";
	//private final static String cI = "AES/CBC/PKCS7Padding";

	private static byte[] fromHexString(final String encoded) {
	    if ((encoded.length() % 2) != 0)
	        throw new IllegalArgumentException(
	        		"Input string must contain an even number of characters");

	    final byte result[] = new byte[encoded.length()/2];
	    final char enc[] = encoded.toCharArray();
	    for (int i = 0; i < enc.length; i += 2) {
	        StringBuilder curr = new StringBuilder(2);
	        curr.append(enc[i]).append(enc[i + 1]);
	        result[i/2] = (byte) Integer.parseInt(curr.toString(), 16);
	    }
	    return result;
	}
	
	public static String encrypt(String key, String iv, String cleartext) throws Exception {
		Cipher cipher = Cipher.getInstance(cI);
		byte[] keyBytes = fromHexString(key);
		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, alg);
		byte[] ivBytes = fromHexString(iv);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(cleartext.getBytes());
		return new String(encodeBase64(encrypted));
	}

	public static String decrypt(String key, String iv, String encrypted) throws Exception {
		Cipher cipher = Cipher.getInstance(cI);
		byte[] keyBytes = fromHexString(key);
		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, alg);
		byte[] ivBytes = fromHexString(iv);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		byte[] enc = decodeBase64(encrypted);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
		byte[] decrypted = cipher.doFinal(enc);
		return new String(decrypted);
	}


	
}