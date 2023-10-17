package com.spirit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpiritApplicationTests {
	
	/**
	 * 国密算法测试
	 */
	@Test
	void chinacipherTest() {
		SymmetricCrypto chinaCipher = SmUtil.sm4();
//		String str = "hello world world ";
		String str = "62050219840119010X";
		Console.log(str.length(), str.getBytes().length);
		String enc = chinaCipher.encryptHex(str);
	    Console.log(enc, enc.length());
	    String dec = chinaCipher.decryptStr(enc);
	    Console.log(dec);
	    SM4 sm4 = SmUtil.sm4();
	    String enc2 = sm4.encryptHex(str);
	    String dec2 = sm4.decryptStr(enc2);
	    Console.log(enc2, enc2.length());
	    Console.log(dec2);
	    assertEquals(str, dec);

	}
	
	/**
	 * 国密算法并发测试
	 */
	@Test
	void chinacipherConCurrentTest() {
		SymmetricCrypto chinaCipher = SmUtil.sm4();
		String str = "62050219840119010X";
		ThreadUtil.concurrencyTest(10, () -> {
			Console.log("{} -> {}", Thread.currentThread().getName(), chinaCipher.encryptHex(str));
		});
	    assertTrue(true);

	}
	
}
