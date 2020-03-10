package com.cht.springboot.redis.operation;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring boot 对redis中Set的操作
 * @author 程红涛
 *
 */

@RestController
@CrossOrigin
@RequestMapping("/set")
public class SetOperation {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@GetMapping("/write")
	public void write() {
		
		//添加元素 返回插入了几个元素
		long size=redisTemplate.opsForSet().add("set01", "a","b","c");
		System.out.println(size);
		
		//删除元素 返回删除了几个元素
		long resize=redisTemplate.opsForSet().remove("set01", "a","b");
		System.out.println(resize);
		
		//spop随机删除元素，返回被删除的元素
		List<String> list=redisTemplate.opsForSet().pop("set01", 3);
		print(list);
		
		//smove key1 key2 val 删除多个set中的共同值val,暂时无对应api
		
		
	}
	
	@GetMapping("/read")
	public void read() {
		
		//获取集合所有元素
		Set<String> set=redisTemplate.opsForSet().members("set01");
		print(set);
		
		//srandmember key count 随机取出count个元素,如果count大于size，则取出全部
		List<String> list=redisTemplate.opsForSet().randomMembers("set01", 2);
		print(list);
	}
	
	@GetMapping("/other")
	public void other() {
		
		//获取set的长度scard key
		long size=redisTemplate.opsForSet().size("set01");
		System.out.println("length is "+size);
		
		//判断元素是否在set中
		boolean flag=redisTemplate.opsForSet().isMember("set01", "i");
		System.out.println("scard key:"+flag);
	}
	
	@GetMapping("/math")
	public void math() {
		
		//差集，sdiff key01 key02 在01中查找02中没有的元素
		Set<String> diff=redisTemplate.opsForSet().difference("set01", "set02");
		print(diff);
		
		//并集 sunion key1 key2
		Set<String> union=redisTemplate.opsForSet().union("set01", "set02");
		print(union);
		
		//交集，取公共部分sinter key1 key2
		Set<String> sinter=redisTemplate.opsForSet().intersect("set01", "set02");
		print(sinter);
	}
	
	
	
	private void print(Collection<?> coll) {
		coll.stream().forEach(ele->System.out.print(ele+" "));
		System.out.println();
	}
	
}
