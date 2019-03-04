package com.pjqdyd.dockertest.api;

import com.pjqdyd.dockertest.mapper.PeopleMapper;
import com.pjqdyd.dockertest.pojo.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PeopleMapper peopleMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //测试是否可以接收get请求
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello springboot in Docker!";
    }

    //测试保存一个people的信息到mysql数据库
    @GetMapping("/save")
    public String save(String id, String name){
        People people = new People();
        people.setId(id);
        people.setName(name);
        peopleMapper.save(people);

        return "保存成功!";
    }

    //测试查询mysql的数据
    @GetMapping("/query")
    public People query(@RequestParam String id){
       People result = peopleMapper.findOne(id);
       return result;
    }

    //测试保存一条redis数据
    @GetMapping("/saveRedis")
    public String saveRedis(){
        stringRedisTemplate.opsForValue().set("testkey","12345testvalue12345");
        return "保存成功!";
    }

    //测试获取一条redis数据
    @GetMapping("/getRedis")
    public String getRedis(){
        return stringRedisTemplate.opsForValue().get("testkey");
    }


}
