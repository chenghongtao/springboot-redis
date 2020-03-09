package com.cht.springboot.redis.operation;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring boot 对redis中key的操作
 * @author 程红涛
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/key")
public class KeyOperation {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 获取所有的key，返回set集合
	 */
	@GetMapping("/keys")
	public void keys() {
		//查看所有key
		Set<String> keys=redisTemplate.keys("*");
		keys.stream().forEach(val->System.out.println(val));
	}
	
	/**
	 * 判断key是存在，存在返回true，否则返回false
	 */
	@GetMapping("/exists")
	public void exists() {
		String contains="test";
		String unContains="10";
		boolean result1=redisTemplate.hasKey(contains);
		boolean result2=redisTemplate.hasKey(unContains);
		System.out.println(result1+"-----------"+result2);
	}
	
	/**
	 * 将键值对从一个库移动到另一个库,如果不存在库，则报ERR index out of range
	 * 成功为true，失败为false
	 */
	@GetMapping("move")
	public void move() {
		//将test从0号库移动到一号库
		boolean su=redisTemplate.move("test", 1);
		//boolean fa=redisTemplate.move("k4", 1);
		System.out.println(su+"--------------"	);
	}
	
	/**
	 * 为指定键设置过期时间和查看还有几秒过期
	 */
	@GetMapping("/expire")
	public void expirt() {
		
		//设置10秒过期
		redisTemplate.expire("test", 10, TimeUnit.SECONDS);
		
		//查看还有几秒过期ttl (time to leave),返回剩余秒数
		Long ttl=redisTemplate.getExpire("test", TimeUnit.SECONDS);
		System.out.println("------------------"+ttl);
	}
	
	/**
	 * 获取key对应的值类型
	 */
	@GetMapping("/type")
	public void type() {
		System.out.println(redisTemplate.type("test").code());
	}
	
	

}
