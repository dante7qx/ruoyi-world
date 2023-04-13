package com.risun.common.utils.sign;

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

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RsaUtils {
	
	// Rsa 公钥
	public static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx2jg/2OJ3dNpXOFvwNKB"
			+ "kL3Ytm9/NhMxAEVdrt3tPulsV0TzQquIS1C10sMgDgpBHrL8921iwZ8JycM10PhH"
			+ "UCuBPBitpsTjIg2sNaVGSwevzh7pDAC25QTIBzz4zM7GnoJfa9/fW1jjwocslv9W"
			+ "4Slj5OCKnt3bBVwVpzhrJURNbgKM+5rYeWoVYO6VqCJmsPM2Ok1ILTyzfwNOhenb"
			+ "8Xl5+JPHTHVXw5g1vr/XJY1NcYLEGuPyj3juZuPu0dVVkI3cIMSQxSlZTwxbA2Fk"
			+ "h1qISqdsFsy1m+wVyHIyxsRE2uJUiSYNekM1jz/li4N/0iqAAuLWcu+S5U6JzZEc"
			+ "MQIDAQAB";
	
	// Rsa 私钥
	public static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHaOD/Y4nd02lc"
			+ "4W/A0oGQvdi2b382EzEARV2u3e0+6WxXRPNCq4hLULXSwyAOCkEesvz3bWLBnwnJ"
			+ "wzXQ+EdQK4E8GK2mxOMiDaw1pUZLB6/OHukMALblBMgHPPjMzsaegl9r399bWOPC"
			+ "hyyW/1bhKWPk4Iqe3dsFXBWnOGslRE1uAoz7mth5ahVg7pWoImaw8zY6TUgtPLN/"
			+ "A06F6dvxeXn4k8dMdVfDmDW+v9cljU1xgsQa4/KPeO5m4+7R1VWQjdwgxJDFKVlP"
			+ "DFsDYWSHWohKp2wWzLWb7BXIcjLGxETa4lSJJg16QzWPP+WLg3/SKoAC4tZy75Ll"
			+ "TonNkRwxAgMBAAECggEBALxUyWtqzyK6MKCQKcOrDMNvCeYDl9mydm0TFk+5OgBY"
			+ "5QHnFPi6mJbJO3A66WNZO/eKhHAK12KWmd/8hi8+zdthu0TO6fK/sOTQlPx1g9YJ"
			+ "BHlfqCq14gaaZXCwnSqRf/TknMp9Pa5AXZuznuFdduhg9v+LMmCol9qQBcNQ/aDL"
			+ "Hqt3wz4OFqY5B/N5Hp+FnWp88oDvbshNmtlnduvG499R6lxNNtmYL4XersEtUSFL"
			+ "xJGatqrtZDlNwhZEH10Ca4pry2OhJkG40pxvNtySq/imvbipxverjz2D9GVz2trO"
			+ "I4JcQ23/YaMN9XTISbKfWcnrY/vRCQf6Iih6H1fSiCECgYEA4249ZWJXeTj1sxz8"
			+ "DFYRbkTx6ZMXdvPdfOBmzVK2RaHW0uaMHYLhUkwibE3iK+uIx3m8mQB4ZXiIIUGm"
			+ "xOJT/RRgmdZFM/TqGRZHaQ/OzG3KkirakhL6ScBOzs0QAA3Ozcu4Nx3x13gTa1yz"
			+ "VEiUj8uAAYs+RjauKjtQbdIkz9cCgYEA4HWJNHqV1yTcWmetgbUuAENliOGzjUvI"
			+ "njA4lAvvdf3ieNI0lVgXV5C/H5Gjlx1xUx9zBIhanRwHJ5c70SgLfBm+UyKTLKA1"
			+ "G6vO0yGO601EbcWjXHDeZ1IFscAW2yUNbXYzY31CG/3SHcQLmP3w55VaIeyWWUW9"
			+ "FQHjm1QwkzcCgYBOuC2QFgXo84o3wOSM8I1/+WUyT7NjfO1A9/sdPm/Qkj/lJ99/"
			+ "p3mOHsQbcKnEQfZJ9R0OaFdD6ABWYn7yztP7WT5GgR/a5/7PSCLXfp05rRbcmPCm"
			+ "duMi++lLMzdCjYTzPnw4t6ipx+oQ0zlPBwBE9U8NOcEIVIzcGUOxf3c2aQKBgAu0"
			+ "1ZURF6OC3qMwaRIsutt0qGT9MHocC7iEV08RWEnVFeyrOebYE0+T0wP4eom1FWX6"
			+ "a+s+mUs8XPspngsIhaDRTuXTFc3sBRkOWtkUFPocq4b2GdEGafMFihp9JDQFuK+O"
			+ "zJtRuxLup/i5csll5RyO44aFakn5oyTnVlGRkH79AoGAIjJS8i34UTfJvooakwcr"
			+ "lNAzMhUn0j+cqhrkIaxV6DH2hjgxI7M5JlCgt/euAx1dhHm1munx8UTzQWFyp1nH"
			+ "6MsSBoElZpojbWFLsvauGCbVufBexvep8dyTfPhZq7Tcu6lXcctZFQzZWRVa4Stv"
			+ "5FBOTjwENTxC6N5SYvMAjOw=";
	
	
	private static final String ALGORITHM_KEY = "RSA";
	private static final String ALGORITHM_MODE = "RSA/None/OAEPWITHSHA-256ANDMGF1PADDING";

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
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
//		String str = decryptByPrivateKey(privateKey, "hiINoicchx+KOKN7RgmkT2MqvcjDlt285YXM//nVL9XHwO79iC7sSIl2dIQTPZPVa7LY6OLrCtcbRXnq7p6ZB/HahLHeSqSApP74MUCGRM9VvfARjTK2qucp/Nf55U6T/xFcpgbHql8tOtEr9rd41FSI2fhJoHhNTRWkEmXK+Rrntynuo6Rf1bj8/7TrzenzC4dJGXuB19gq85k+7QyWckHN853KGavaHESfAMH/8QHTdVVOfPHH+UYA1LLwym7hjSP1madepXLEQqpLfZMoLukMf1RXhEhY033CqptombF8otMm07Y9I7k5ND44f/swDGfJzYw8uwGfVcJF1LrFWA==");
//		System.out.println(str);
//		Provider[] providers = Security.getProviders();
//		for (Provider provider : providers) {
//			System.out.println(provider.getName());
//		}
		
//		String xx = "gzxqj1028#";
//		String ens = encryptByPublicKey(xx);
//		System.out.println(ens);
//		String des = decryptByPrivateKey("Yt1wmMi9EvoXrmq7cmBj2woAIY9dltwFfCIzrGlfbht6yitj7KEZR5THh/lv45xpLyQn31fRXIJauZTzv37ogJbEp2/U1IDvvf6hBkb1kVX/KOpFBdHIMBwL5P5bqJtRE8MpJiNDZlfCHMVTrwqGEHT5L4aAjXH1kcTLZ0Kb8yHXv8bsgQNsgDhZzqIF5u6YnEekP1wppQbIbf+H8yNjGQJPPzP6Yl9v8vK82BtMqAmam7UKC5yWthK14O2JTBJr5weNN5hhB+vvHf1Gd+eCH0lf8oDBJYj4cO9PyZRzzNrNh8QLNvSRt4r2L90tWLpNoIbenQWvKtxkShDeD7zXoQ==");
//		System.out.println(des);
		
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
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_KEY);
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
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
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_KEY);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
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
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_KEY);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE, new BouncyCastleProvider());
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
			log.error(e.getMessage(), e);
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
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_KEY);
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE, new BouncyCastleProvider());
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
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_MODE);
		keyPairGenerator.initialize(2048);
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
