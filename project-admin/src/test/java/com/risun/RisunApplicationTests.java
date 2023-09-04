package com.risun;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.wxtool.ChinaCipher;

import cn.hutool.core.lang.Console;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RisunApplicationTests {
	
	/**
	 * 国密算法测试
	 */
	@Test
	void chinacipherTest() {
		ChinaCipher chinaCipher = new ChinaCipher();
		String str = "13922225566";
		String enc = chinaCipher.SM4EncDefault(str);
	    Console.log(enc);
	    String dec = chinaCipher.SM4DecDefault(enc);
	    Console.log(dec);
	    
	    assertEquals(str, dec);

	}
	
}
