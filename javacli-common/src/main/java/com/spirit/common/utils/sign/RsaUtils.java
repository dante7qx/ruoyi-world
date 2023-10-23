package com.spirit.common.utils.sign;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RsaUtils {
	
	// Rsa 公钥
	public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcCMq56AI4zdkfXMmL71FJY1rP"
			+ "+0JYOzoJc8QyMcT0biimOMeHsz6uPXAT4pInJ4cQdTl3d9voaMLrKhtNb6l2ZdqW"
			+ "0Owow9SRLtuG9OT392y01szN2GyHJENDTOa7+0l9t5yUxiLpNYGWX/uI67NVxZ1F"
			+ "Co7/oXnhOX++PdbUnQIDAQAB";
	
	// Rsa 私钥
	public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJwIyrnoAjjN2R9c"
			+ "yYvvUUljWs/7Qlg7OglzxDIxxPRuKKY4x4ezPq49cBPikicnhxB1OXd32+howusq"
			+ "G01vqXZl2pbQ7CjD1JEu24b05Pf3bLTWzM3YbIckQ0NM5rv7SX23nJTGIuk1gZZf"
			+ "+4jrs1XFnUUKjv+heeE5f7491tSdAgMBAAECgYBQMSyPky/hJdpbBgMHij2KrCd2"
			+ "ELouQnI3fWnuKioBPcRieXgCxBqIzkaV0bIvsV73FGgugEljDsRuvDs/9w1uYL40"
			+ "v0oaDhh24PxfXNCMoXONW+lpDa3ZwL9xn2r+/vDH+zn28Mh1b2465oyHG5UpCFJT"
			+ "f8XoHkKdqYLA0Ubi+QJBAMuAiP0WoeEiOIiQ7c/TPHyjqipUULyQKiB19NbTj41K"
			+ "kZgKSKt9Qe009gpMrCZSNlkDemot7atL6iPlvr2yhXcCQQDESXF1aguuER36V+lh"
			+ "wJf4WBt5kmYpiOoHq0yN91ZepPx6wz5D98xIUSs8SVpMzZPC6rkVclL5nAF7HFQz"
			+ "RcuLAkBwtFcqFAbDcPhuahXLK+or/ViY6OluBUnPgISBFdpDHjFUSx3EHDMO9G3v"
			+ "dldBQfbnhY2ekKE4ZWrfRrZEyUIxAkEAsfN/eBovAp0kF20R+XxYil6ecgYmtqsY"
			+ "uOE041QEKoPbHhTZ41bJDhsAXoSF6DFML58LMwVVclRcB1e0glNZZwJAWZDEvDEC"
			+ "rptcSMw2egDVR/rUC2uDoUPN0GC2dWqlB2ZdMP+WYRbJzF8+qBCwPOT5MSVujE44"
			+ "bxHNPmLCAuEAcA==";

	/**
	 * 私钥解密
	 *
	 * @param privateKeyString 私钥
	 * @param text             待解密的文本
	 * @return 解密后的文本
	 */
	public static String decryptByPrivateKey(String text) {
		try {
			return decryptByPrivateKey(privateKey, text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 公钥解密
	 *
	 * @param publicKeyString 公钥
	 * @param text            待解密的信息
	 * @return 解密后的文本
	 */
	public static String decryptByPublicKey(String publicKeyString, String text) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] result = cipher.doFinal(Base64.decode(text));
		return new String(result);
	}

	/**
	 * 私钥加密
	 *
	 * @param privateKeyString 私钥
	 * @param text             待加密的信息
	 * @return 加密后的文本
	 */
	public static String encryptByPrivateKey(String privateKeyString, String text) throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] result = cipher.doFinal(text.getBytes());
		return Base64.encode(result);
	}

	/**
	 * 私钥解密
	 *
	 * @param privateKeyString 私钥
	 * @param text             待解密的文本
	 * @return 解密后的文本
	 */
	public static String decryptByPrivateKey(String privateKeyString, String text) throws Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] result = cipher.doFinal(Base64.decode(text));
		return new String(result);
	}
	
	/**
	 * 公钥加密
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String text) {
		try {
			return encryptByPublicKey(publicKey, text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 公钥加密
	 *
	 * @param publicKeyString 公钥
	 * @param text            待加密的文本
	 * @return 加密后的文本
	 */
	public static String encryptByPublicKey(String publicKeyString, String text) throws Exception {
		X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decode(publicKeyString));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] result = cipher.doFinal(text.getBytes());
		return Base64.encode(result);
	}

	/**
	 * 构建RSA密钥对
	 *
	 * @return 生成后的公私钥信息
	 */
	public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
		String publicKeyString = Base64.encode(rsaPublicKey.getEncoded());
		String privateKeyString = Base64.encode(rsaPrivateKey.getEncoded());
		return new RsaKeyPair(publicKeyString, privateKeyString);
	}

	/**
	 * RSA密钥对对象
	 */
	public static class RsaKeyPair {
		private final String publicKey;
		private final String privateKey;

		public RsaKeyPair(String publicKey, String privateKey) {
			this.publicKey = publicKey;
			this.privateKey = privateKey;
		}

		public String getPublicKey() {
			return publicKey;
		}

		public String getPrivateKey() {
			return privateKey;
		}
	}
}
