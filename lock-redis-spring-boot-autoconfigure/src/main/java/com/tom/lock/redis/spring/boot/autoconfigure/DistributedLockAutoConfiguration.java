package com.tom.lock.redis.spring.boot.autoconfigure;

import com.tom.lock.redis.spring.boot.autoconfigure.lock.DistributedLock;
import com.tom.lock.redis.spring.boot.autoconfigure.lock.RedisDistributedLock;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 装配分布式锁的bean
 * @author handsometong
 * @date 2022年4月14日 下午3:11:31
 * @version 1.0.0
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class DistributedLockAutoConfiguration {
	
	@Bean
	@ConditionalOnBean(RedisTemplate.class)
	public DistributedLock redisDistributedLock(RedisTemplate<Object, Object> redisTemplate){
		return new RedisDistributedLock(redisTemplate);
	}
	
}
