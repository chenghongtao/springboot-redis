package com.cht.springboot.redis.operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * List相关命令和api对应
 * 
 * @author 程红涛
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/list")
public class ListOperation {

	@Autowired
	private StringRedisTemplate redisTemplate;

	// 增加删除元素
	@GetMapping("/write")
	public void write() {
		
		for (int i = 0; i < 5; i++) {
			// lpush 链表头插入
			long lpush=redisTemplate.opsForList().leftPush("list01", String.valueOf(i));
			
			// rpush
			long rpush=redisTemplate.opsForList().rightPush("list01", String.valueOf(i));
		}
		
		//获取全部元素
		print("list01",0,-1);

		
		// lpop 返回被移除的元素
		String lpop=redisTemplate.opsForList().leftPop("list01");
		System.out.println("lpop-----"+lpop);
		print("list01",0,-1);
		
		// rpop 返回被移除的元素
		String rpop=redisTemplate.opsForList().rightPop("list01");
		System.out.println("lpop-----"+rpop);
		print("list01",0,-1);
		
		//lrem key N val 从左开始，删除N个值为val的元素元素
		redisTemplate.opsForList().remove("list01", 3, "1");
		print("list01",0,-1);
		
		//ltrim key start end 将截取出来的值再赋值给key的list
		redisTemplate.opsForList().trim("list01", 0, -2);
		print("list01",0,-1);
		
		//对list中某个位置的元素进行替换 lset key index newVal
		redisTemplate.opsForList().set("list01", 0, "1000");
		print("list01",0,-1);

		//linsert key before/after v0 v1 在v0之前/后插入v1
		//首先找到v0的位置，然后插入，后边元素后移
		
		//将源列表的尾部插入到目的列表的头部
		System.out.println("list01-------------------------------");
		print("list01",0,-1);
		System.out.println("list02-------------------------------");
		print("list02",0,-1);
		redisTemplate.opsForList().rightPopAndLeftPush("list01", "list02");
		print("list01",0,-1);
		print("list02",0,-1);
	}
	
	/**
	 * 获取值
	 */
	@GetMapping("/read")
	public void read() {
		//获取list指定范围内元素 range key start end 封闭区间 
		List<?> list=redisTemplate.opsForList().range("list01", 0, -1);
		list.stream().forEach(val->System.out.print(val+" "));
		
		//获取list长度
		long length=redisTemplate.opsForList().size("list01");
		System.out.println("list01 length：-----------------"+length);
		
		//按照索引获取列表元素
		String indexVal=redisTemplate.opsForList().index("list01", 0);
		System.out.println(indexVal);
		
	}
	
	private void print(String key,int start,int end) {
		List<?> list=redisTemplate.opsForList().range(key, start, end);
		list.stream().forEach(val->System.out.print(val+" "));
		System.out.println();
		
	}
}
