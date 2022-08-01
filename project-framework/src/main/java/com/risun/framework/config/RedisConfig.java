package com.risun.framework.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import cn.hutool.core.util.StrUtil;

/**
 * redis配置
 * 
 * @author ruoyi
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	private static final String REDIS_PROTOCOL_PREFIX = "redis://";
	private static final String REDISS_PROTOCOL_PREFIX = "rediss://";
	@Autowired
	private RedisProperties redisProperties;

	@Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() {
		String prefix = REDIS_PROTOCOL_PREFIX;
		if (redisProperties.isSsl()) {
			prefix = REDISS_PROTOCOL_PREFIX;
		}
        Config config = new Config();
        config.useSingleServer()
        	.setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
        	.setDatabase(redisProperties.getDatabase())
        	.setPassword(StrUtil.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
        	.setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
        	.setClientName("risun-redisson-client")
        	;
        return Redisson.create(config);
    }

	@Bean
	@SuppressWarnings(value = { "unchecked", "rawtypes" })
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);

		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(serializer);

		// Hash的key也采用StringRedisSerializer的序列化方式
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(serializer);

		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public DefaultRedisScript<Long> limitScript() {
		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setScriptText(limitScriptText());
		redisScript.setResultType(Long.class);
		return redisScript;
	}

	/**
	 * 限流脚本
	 */
	private String limitScriptText() {
		return "local key = KEYS[1]\n" + "local count = tonumber(ARGV[1])\n" + "local time = tonumber(ARGV[2])\n"
				+ "local current = redis.call('get', key);\n" + "if current and tonumber(current) > count then\n"
				+ "    return tonumber(current);\n" + "end\n" + "current = redis.call('incr', key)\n"
				+ "if tonumber(current) == 1 then\n" + "    redis.call('expire', key, time)\n" + "end\n"
				+ "return tonumber(current);";
	}
}
