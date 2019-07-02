package com.itheima.test;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader cl = ClassLoaderTest.class.getClassLoader();
        System.out.println(cl);
        System.out.println(cl.getParent());
        System.out.println(cl.getParent().getParent());
    }
}
