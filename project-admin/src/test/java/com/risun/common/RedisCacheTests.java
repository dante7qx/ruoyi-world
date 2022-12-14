package com.risun.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.risun.RisunApplicationTests;
import com.risun.common.core.redis.RedisCache;

public class RedisCacheTests extends RisunApplicationTests {
	
	@Autowired
	private RedisCache redisCache;
	
	String key = "test_key";
	
	@Test
	public void operCache() {
		String val = "你好，Redis！";
		redisCache.setCacheObject(key, val);
		Object cacheObject = redisCache.getCacheObject(key);
		assertEquals(val, cacheObject);
	}
	
	@Test
	public void delCache() {
		boolean result = redisCache.deleteObject(key);
		assertTrue(result, key+"不存在！");
	}

}
