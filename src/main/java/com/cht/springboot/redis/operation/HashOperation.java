package com.cht.springboot.redis.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring boot 对redis中Hash的操作
 * @author 程红涛
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/hash")
public class HashOperation {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@GetMapping("/write")
	public void write() {
		
		//添加一个hset
		redisTemplate.opsForHash().put("hash01", "pro", "test");
		
		//添加多个hmset
		Map<String,String> map=new HashMap<>();
		map.put("name", "cht");
		map.put("sex","M");
		redisTemplate.opsForHash().putAll("hash01", map);
		
		//hashkey不存在，则添加hsetnx
		boolean fail=redisTemplate.opsForHash().putIfAbsent("hash01", "name", "fail");
		boolean succ=redisTemplate.opsForHash().putIfAbsent("hash01", "email", "success");
		System.out.println(fail+"--------------------"+succ);
		
		//修改
		
		//删除多个hashkey
		long count=redisTemplate.opsForHash().delete("hash01", "pro","email");
	}
	
	@GetMapping("/read")
	public void read() {
		
		//根据key 和 hashkey获取值
		redisTemplate.opsForHash().get("hash01", "name");
		
		//获取多个值hmget
		List<Object> list=new ArrayList<>();
		list.add("name");
		list.add("sex");
		List<Object> result=redisTemplate.opsForHash().multiGet("hash01", list);
		result.stream().forEach(val->{System.out.print(val.toString()+" ");});
		System.out.println();
		
		//获取所有hashkey hgetall key
		Set<Object> keys=redisTemplate.opsForHash().keys("hash01");
		keys.stream().forEach(ele->System.out.print(ele.toString()+" "));
		System.out.println();
		
		//获取hash中元中hashkey的个数
		long size=redisTemplate.opsForHash().size("hash01");
		System.out.println("size----------------"+size);
		
		//判断hash中是否存在该hashkey
		boolean f1=redisTemplate.opsForHash().hasKey("hash01", "sex");
		boolean f2=redisTemplate.opsForHash().hasKey("hash01", "aaa");
		System.out.println(f1+"-----------------------"+f2);
		
		//获取所有的val
		List<Object> vals=redisTemplate.opsForHash().values("hash01");
		vals.stream().forEach(ele->System.out.print(ele.toString()+" "));
		
	}
	
	@GetMapping("/math")
	public void math() {
		
		//为某个hashkey对应的值加上一个数
		long num1=redisTemplate.opsForHash().increment("hash01", "num1", 10);
		
		//ERR hash value is not an integer
		double num2=redisTemplate.opsForHash().increment("hash01", "num2", 0.4d);
		
		//为某个hashkey对应的val减去一个数
		long num3=redisTemplate.opsForHash().increment("hash01","num3",-10);
		double num4=redisTemplate.opsForHash().increment("hash01","num4",-0.8);
		System.out.println(num1+"--"+num2+"--"+num3+"--"+num4);
	}
}
