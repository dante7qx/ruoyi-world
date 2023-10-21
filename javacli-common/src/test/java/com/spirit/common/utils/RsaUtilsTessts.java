package com.spirit.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.spirit.common.utils.sign.RsaUtils;

import cn.hutool.core.lang.Console;

public class RsaUtilsTessts {

	@Test
	void encryptDecrypt() {
		String pwd = "SdlL!B$RV@";
		String encPwd = RsaUtils.encryptByPublicKey(pwd);
		String decPwd = RsaUtils.decryptByPrivateKey(encPwd);
		Console.log(encPwd);
		Console.log("长度: {}", encPwd.length());
		assertEquals(pwd, decPwd);
	}
	
}
