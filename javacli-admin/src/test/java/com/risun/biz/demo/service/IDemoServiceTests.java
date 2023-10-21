package com.risun.biz.demo.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;

import com.risun.RisunApplicationTests;
import com.risun.biz.demo.domain.Demo;
import com.risun.biz.demo.mapper.DemoMapper;
import com.risun.common.core.redis.RedisLock;

import cn.hutool.core.collection.CollUtil;

/**
 * 业务示例 Service 测试
 * 
 * @author dante
 *
 */
public class IDemoServiceTests extends RisunApplicationTests {

	@Autowired
	private IDemoService demoService;
	@Autowired
    private DemoMapper demoMapper;
	@Autowired
	private RedisLock redisLock;

	String key = "_concurrencyInsertLock";

	@Test
	void concurrencyInsert() {
//		IntStream.range(0, 10).parallel().forEach(i -> addDemo());
		IntStream.range(0, 10).parallel().forEach(i -> addDemo1());
//		IntStream.range(0, 10).parallel().forEach(i -> addDemo2());
	}

	/**
	 * Redission 分布式锁 - 用法1
	 */
	private void addDemo1() {
		RLock lock = redisLock.getRLock(key);
		if (lock.tryLock()) {
			try {
				addDemo();
			} finally {
				lock.unlock();
			}
		}
	}
	
	/**
	 * Redission 分布式锁 - 用法2
	 */
	private void addDemo2() {
		if (redisLock.tryLock(key, 2, TimeUnit.SECONDS)) {
			try {
				addDemo();
			} finally {
				redisLock.unlock(key);
			}
		}
	}
	
	/**
	 * 不加分布式锁
	 */
	private void addDemo() {
		Demo demo = new Demo();
		demo.setDemoName("xyx");
		demo.setAttachment(Thread.currentThread().getName());
		List<Demo> demos = demoService.selectDemoList(demo);
		if (CollUtil.isEmpty(demos)) {
			demoMapper.insert(demo);
		}
	}

}
