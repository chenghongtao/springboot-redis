package com.cht.springboot.redis.operation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring boot 对redis中ZSet的操作
 * @author 程红涛
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/zset")
public class ZSetOperation {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * zset 写操作
	 */
	@GetMapping("/write")
	public void write() {
		
		Set<TypedTuple<String>> eles=new HashSet<>();
		TypedTuple<String> val=new DefaultTypedTuple<>("1", 10d);
		TypedTuple<String> val1=new DefaultTypedTuple<>("2", 20d);
		TypedTuple<String> val2=new DefaultTypedTuple<>("3", 30d);
		TypedTuple<String> val3=new DefaultTypedTuple<>("4", 40d);
		TypedTuple<String> val4=new DefaultTypedTuple<>("5", 50d);
		eles.add(val);
		eles.add(val1);
		eles.add(val2);
		eles.add(val3);
		eles.add(val4);
		
		//添加
		redisTemplate.opsForZSet().add("zset01", eles);
		//删除 zrem key member1 member2,返回删除元素的个数
		long result=redisTemplate.opsForZSet().remove("zset01", "2");
		System.out.println(result);
	}
	
	/**
	 * zset 取值获取属性操作
	 */
	@GetMapping("/read")
	public void read() {
		
		//获取指定索引区间的值，封闭区间
		Set<String> data=redisTemplate.opsForZSet().range("zset01", 0, -1);
		print(data);
		
		//根据分数区间获取值,封闭区间
		Set<String> score=redisTemplate.opsForZSet().rangeByScore("zset01", 10.0, 30.0);
		print(score);
		
		//统计元素个数 zcard 
		long size=redisTemplate.opsForZSet().size("zset01");
		System.out.println("zset lengthis "+ size);
		
		//统计在分数区间元素个数zcount key min max
		long resize=redisTemplate.opsForZSet().count("zset01", 10.0, 30.0);
		System.out.println("zset count in min max is "+resize);
		
		//获取值对应的下标值
		long index=redisTemplate.opsForZSet().rank("zset01", "4");
		System.out.println("index is 4"+index);
		
		//根据对应值获取分数
		double doub=redisTemplate.opsForZSet().score("zset01", "4");
		System.out.println("获取的分数是："+doub);
		
		//逆序获取下标值
		long index1=redisTemplate.opsForZSet().reverseRank("zset01", "5");
		System.out.println("index1--------------:"+index1);
		
		//根据分数区间逆序获取val
		Set<String> rever=redisTemplate.opsForZSet().reverseRangeByScore("zset01", 10, 40);
		print(rever);
		
	}
	
	private void print(Collection<?> coll) {
		coll.stream().forEach(val->System.out.print(val+" "));
		System.out.println();
		
	}

}
