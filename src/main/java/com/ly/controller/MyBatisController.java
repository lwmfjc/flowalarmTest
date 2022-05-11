package com.ly.controller;

import com.ly.entity.TestL;
import com.ly.mapper.TestLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("mybatis")
public class MyBatisController {

    @Autowired
    private TestLMapper testLMapper;

    @RequestMapping("hello")
    public String hello(){
        List<TestL> testLS = testLMapper.selectList(null);
        System.out.printf("扫到%s记录\n",testLS.size());
        for (TestL testL:testLS){
            System.out.println(testL);
        }
        TestL testL=new TestL();
        testL.setId(UUID.randomUUID().toString());
        testL.setAge(30);
        testL.setName(UUID.randomUUID().toString().substring(3,6));
        testLMapper.insert(testL);

        return "-1";
    }
}
