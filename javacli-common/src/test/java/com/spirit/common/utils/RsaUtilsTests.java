package com.spirit.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.spirit.common.constant.Constants;
import com.spirit.common.utils.sign.RsaUtils;

import cn.hutool.core.lang.Console;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class RsaUtilsTests {
	
	@Test
	void encryptDecrypt() {
		String pwd = "SdlL!B$RV@";
		String encPwd = RsaUtils.encryptByPublicKey(pwd);
		String decPwd = RsaUtils.decryptByPrivateKey(encPwd);
		Console.log(encPwd);
		Console.log("长度: {}", encPwd.length());
		assertEquals(pwd, decPwd);
	}
	
	@Test
	void sm4() {
		String str = "18292998812";
		SymmetricCrypto chinaCipher = SmUtil.sm4(Constants.SM4_KEY.getBytes());
		String enc = chinaCipher.encryptHex(str);
		String dec = chinaCipher.decryptStr(enc, StandardCharsets.UTF_8);
		Console.log(dec);

		SymmetricCrypto chinaCipher2 = SmUtil.sm4(Constants.SM4_KEY.getBytes());
		String dec2 = chinaCipher2.decryptStr(enc, StandardCharsets.UTF_8);
		Console.log(dec2);
	}
}
