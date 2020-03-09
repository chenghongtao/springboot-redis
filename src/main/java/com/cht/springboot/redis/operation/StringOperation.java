package com.cht.springboot.redis.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring boot 对redis中String的操作
 * @author 程红涛
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/string")
public class StringOperation {
	
	@Autowired
	private StringRedisTemplate redisTemplate;

	@GetMapping("/getAndSet")
	public void getAndSet() {
		//set key val
		redisTemplate.opsForValue().set("string-set", "string-set");
		
		//get key
		String get=redisTemplate.opsForValue().get("string-set");
		System.out.println("string-set:"+get);
		
		//mset 添加多个键值对
		Map<String,String> map=new HashMap<>();
		map.put("shanxi","tongchuan");
		map.put("henan","zhengzhou");
		map.put("hubei","wuhan");
		redisTemplate.opsForValue().multiSet(map);
		
		//mget 一次获取多个键值对
		List<String> keys=new ArrayList<>();
		keys.add("shanxi");
		keys.add("henan");
		keys.add("hubei");
		List<String> result=redisTemplate.opsForValue().multiGet(keys);
		result.stream().forEach(val->System.out.println(val));
		
		//setnx 如果键不存在，则成功true，否则，失败，false
		boolean b1=redisTemplate.opsForValue().setIfAbsent("k1", "k1");
		boolean b2=redisTemplate.opsForValue().setIfAbsent("k100", "k100");
		System.out.println(b1+"---------------"+b2);
		
		//msetnx 如果键都不存在，则成功，否则失败
		Map<String,String> absent=new HashMap<>();
		absent.put("k1", "100000");
		absent.put("hunan","changsha");
		
		boolean b3=redisTemplate.opsForValue().multiSetIfAbsent(absent);
		
		absent.remove("k1");
		absent.put("qinghai","xining");
		boolean b4=redisTemplate.opsForValue().multiSetIfAbsent(absent);
		System.out.println("msetnx:"+b3+"-------------------"+b4);
		
		//getset key newval 先获取再设置新值,返回值为oldval
		String result1=redisTemplate.opsForValue().getAndSet("k1", "v11");
		System.out.println(result1);
	}
	
	/**
	 * 为新插入的key-val设置过期时间
	 */
	@GetMapping("/setex")
	public void setex() {
		redisTemplate.opsForValue().set("k30", "20", 10L,TimeUnit.SECONDS);
		long sec=redisTemplate.getExpire("k30");
		System.out.println(sec);
	}
	
	@GetMapping("/decrAndIncr")
	public void decrAndIncr() {
		//incr key
		long incr=redisTemplate.opsForValue().increment("k1");
		
		//decr
		long decr=redisTemplate.opsForValue().decrement("k2");
		
		//incrby key num
		long incrby=redisTemplate.opsForValue().increment("k3", 10);
		
		//decrby key num
		long decrby=redisTemplate.opsForValue().decrement("k4", 20);
		
		//incrby key float
		double incrbydouble=redisTemplate.opsForValue().increment("k5", 0.4D);
		
		double incrbydouble1=redisTemplate.opsForValue().increment("k6", 0.4D);
		
		System.out.println(incr+"--"+decr+"--"+incrby+"--"+decrby+"--"+incrbydouble+"--"+incrbydouble1);
	}
}
