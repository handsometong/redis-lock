package com.tom.demo.redislock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tom.demo.redislock.vo.UserVO;
import com.tom.lock.redis.spring.boot.autoconfigure.annotations.RedisAction;

@Service
public class RedisLockService {

	private final Logger logger = LoggerFactory.getLogger(RedisLockService.class);
	
	@RedisAction
	public void update(String key){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.error("exp", e);
		}
	}

	@RedisAction("'test'.concat(#user.id)")
	public void update(UserVO user){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.error("exp", e);
		}
	}
	
}
