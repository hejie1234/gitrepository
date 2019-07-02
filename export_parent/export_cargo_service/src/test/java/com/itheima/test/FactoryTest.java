package com.itheima.test;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.FactoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class FactoryTest {

//    @Autowired

    @Reference
    private FactoryService factoryService;

    @Test
    public void testFindAll(){
        FactoryExample example = new FactoryExample();
        example.createCriteria().andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(example);
        for(Factory factory : factoryList){
            System.out.println(factory);
        }
    }
}
