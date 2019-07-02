package com.itheima.test;

import com.itheima.dao.system.UserDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class ProxyTest {


    public static void main(String[] args) {

        UserDao proxyUserDao = (UserDao) Proxy.newProxyInstance(String.class.getClassLoader(),
                new Class[]{UserDao.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if(method.getName().equals("findById")){
                            System.out.println("持久层执行了根据id查询用户");
                        }
                        return null;
                    }
                });

        proxyUserDao.findById("1");

    }


//    public static void main(String[] args) {
//        UserServiceImpl userServiceImpl = new UserServiceImpl();
////        userServiceImpl.getClass().getInterfaces();
//
//
//        UserService proxyImpl = (UserService)Proxy.newProxyInstance(userServiceImpl.getClass().getClassLoader(),
//                userServiceImpl.getClass().getInterfaces(),
//                new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        if(method.getName().equals("save")){
//                            System.out.println("执行了保存用户");
//                        }
//                       return null;
//                    }
//                });
//
//        proxyImpl.save(null);
//
//    }
}
