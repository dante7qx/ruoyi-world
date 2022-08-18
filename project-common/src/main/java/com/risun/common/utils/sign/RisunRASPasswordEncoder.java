package com.risun.common.utils.sign;

import com.risun.common.utils.sign.RsaUtils;

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

}
