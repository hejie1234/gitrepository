package com.itheima.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class CargoProvider {

    public static void main(String[] args)throws Exception {
        //1.读取配置文件创建容器
        ClassPathXmlApplicationContext ac =
                new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        //2.启动容器
        ac.start();
        //3.任意键退出
        System.in.read();
    }
}
