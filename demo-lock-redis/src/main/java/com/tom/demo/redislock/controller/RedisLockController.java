package com.tom.demo.redislock.controller;

import javax.annotation.Resource;

import com.tom.demo.redislock.vo.ResultMap;
import com.tom.lock.redis.spring.boot.autoconfigure.annotations.RedisAction;
import com.tom.lock.redis.spring.boot.autoconfigure.lock.DistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tom.demo.redislock.service.RedisLockService;
import com.tom.demo.redislock.vo.UserVO;

@RestController
@RequestMapping("redislock")
@EnableAspectJAutoProxy(exposeProxy = true)
public class RedisLockController {
	
	private final Logger logger = LoggerFactory.getLogger(RedisLockController.class);

	@Resource
	private RedisLockService redisLockService;
	
	@Resource
	private DistributedLock redisDistributedLock;
	
	@RequestMapping("aspect")
	public ResultMap lockAspect(){
		for(int i=0; i<10; i++){
			new RedisLockAspectThread().start();
		}
		return ResultMap.buildSuccess();
	}
	
	@RequestMapping("lock")
	public ResultMap lock(){
		for(int i=0; i<10; i++){
			new RedisLockThread().start();
		}
		return ResultMap.buildSuccess();
	}
	
	class RedisLockThread extends Thread {

		@Override
		public void run() {
			String key = "lockKey";
			boolean result = redisDistributedLock.lock(key);
			logger.info(result ? "get lock success : " + key : "get lock failed : " + key);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error("exp", e);
			} finally {
				redisDistributedLock.releaseLock(key);
				logger.info("release lock : " + key);
			}
		}
	}
	
	class RedisLockAspectThread extends Thread {
		
		@Override
		public void run() {
//			String key = "lockKey2";
//			redisLockService.update(key);
			
			UserVO userVO = new UserVO();
			userVO.setId(10L);
			userVO.setName("handsometong");
			redisLockService.update(userVO);
		}
	}

	@RedisAction("'iot:dev:'.concat(#user.id)")
	public void update(UserVO user){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.error("exp", e);
		}
	}
}
