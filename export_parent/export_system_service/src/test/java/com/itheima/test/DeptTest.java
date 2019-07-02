package com.itheima.test;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;
import com.itheima.service.system.DeptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试dept的方法
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class DeptTest {

    @Autowired
    private DeptService deptService;

    @Test
    public void testFindAll(){
        PageInfo pageInfo = deptService.findAll("1",1,10);
        for(Object obj : pageInfo.getList()){
            System.out.println(obj);//对象的地址，还是部门的内容
        }
    }

    @Test
    public void testSave(){
        Dept dept = new Dept();
        dept.setCompanyName("传智");
        dept.setCompanyId("1");
        dept.setDeptName("黑马107");
        dept.setState(1);
        deptService.save(dept);
    }


}
