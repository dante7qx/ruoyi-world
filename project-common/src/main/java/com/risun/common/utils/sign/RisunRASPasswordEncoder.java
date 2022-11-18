package com.risun.common.utils.sign;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 加密类
 */
public class RisunRASPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return RsaUtils.encryptByPublicKey(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.toString().equals(RsaUtils.decryptByPrivateKey(encodedPassword));
	}
	
//	public static void main(String[] args) {
//		System.out.println(new RisunRASPasswordEncoder().encode("123@qwe"));
//	}

}
