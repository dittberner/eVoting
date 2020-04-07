package br.jus.tredf.evoting.test;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class TesteRSA {

	public void RSAKeyPairGenerator() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair pair = keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public static PublicKey getPublicKey(String base64PublicKey) {
		PublicKey publicKey = null;
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	public static PrivateKey getPrivateKey(String base64PrivateKey) {
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;
	}

	public static byte[] encrypt(byte[] data, String publicKey) throws BadPaddingException, IllegalBlockSizeException,
			InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		return cipher.doFinal(data);
	}

	public static byte[] encrypt(byte[] data, PublicKey publicKey) throws BadPaddingException,
			IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
/*
    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }
*/
    public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
    
    public String ByteArrayToString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public void testar() throws NoSuchAlgorithmException, InvalidKeyException, BadPaddingException,
			IllegalBlockSizeException, NoSuchPaddingException {
		RSAKeyPairGenerator();

		BigInteger b1 = BigInteger.valueOf(50);
		BigInteger b2 = BigInteger.valueOf(31);
		BigInteger b3 = b1.add(b2);
		
		byte[] e1 = encrypt(b1.toByteArray(), this.publicKey);
		byte[] e2 = encrypt(b2.toByteArray(), this.publicKey);
		byte[] e3 = encrypt(b3.toByteArray(), this.publicKey);

		BigInteger d1 = new BigInteger(decrypt(e1, this.privateKey));
		BigInteger d2 = new BigInteger(decrypt(e2, this.privateKey));
		BigInteger d3 = new BigInteger(decrypt(e3, this.privateKey));
		
		System.out.println(b1);
		System.out.println(b2);
		System.out.println(b3);
		System.out.println();
		System.out.println(Base64.getEncoder().encodeToString(e1));
		System.out.println(Base64.getEncoder().encodeToString(e2));
		System.out.println(Base64.getEncoder().encodeToString(e3));
		System.out.println();
		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d3);
		System.out.println();

//		byte[] ef = new byte[e1.length];
//		for (int i = 0; i < e1.length; i++) {
//			ef[i] = (byte) (e1[i] & e2[i]);
//		}
//		System.out.println(Base64.getEncoder().encodeToString(ef));
		
		BigInteger f1 = new BigInteger(e1);
		BigInteger f2 = new BigInteger(e2);
		BigInteger f3 = f1.multiply(f2);
		System.out.println(f1);
		System.out.println(f2);
		System.out.println(f3);
		System.out.println(Base64.getEncoder().encodeToString(f3.toByteArray()));
		System.out.println();
		
		BigInteger m = new BigInteger("1d7777c38863aec21ba2d91ee0faf51", 16);
		BigInteger e = new BigInteger("5abb", 16);
		BigInteger d = new BigInteger("1146bd07f0b74c086df00b37c602a0b", 16);
		System.out.println(m);
		System.out.println(e);
		System.out.println(d);

	}

	private PrivateKey privateKey;
	private PublicKey publicKey;
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

}
