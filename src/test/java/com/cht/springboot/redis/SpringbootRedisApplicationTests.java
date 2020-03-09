package com.cht.springboot.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cht.springboot.redis.model.Person;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {SpringbootRedisApplication.class})
public class SpringbootRedisApplicationTests {

	@Autowired
	private StringRedisTemplate redis;
	
	@Test
	public void connection() {
		Person per=new Person("cht","m",11);
		String json=JSONObject.toJSONString(per);
		redis.opsForValue().set("test",json);
		String backJson=redis.opsForValue().get("test");
		Person p1=JSONObject.parseObject(backJson, Person.class);
		System.out.println(p1.toString());
	}	

}
