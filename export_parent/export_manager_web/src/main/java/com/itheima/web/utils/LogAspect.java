package com.itheima.web.utils;

import com.itheima.domain.system.SysLog;
import com.itheima.domain.system.User;
import com.itheima.service.system.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志的切面类
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Component
@Aspect
public class LogAspect {

    @Autowired
    private HttpServletRequest request;//用到用户信息，可以通过request的getSession获取

    @Autowired
    private SysLogService sysLogService;

    /**
     * 环绕通知记录日志
     * @return
     * ProceedingJoinPoint 它就相当于 method
     */
    @Around("execution(* com.itheima.web.controller.*.*.*(..))")
    public Object aroundAdvice(ProceedingJoinPoint pjp){
       //1.获取当前登录的用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //2.判断用户是否登录了
        if(user != null){
            //1.从pjp中获取方法签名
            Signature signature = pjp.getSignature();
            //2.判断是不是方法签名
            if(signature instanceof MethodSignature){
                //强转
                MethodSignature methodSignature = (MethodSignature)signature;
                //3.获取当前执行的方法
                Method method = methodSignature.getMethod();
                //4.判断当前方法上是否有RequestMapping注解
                boolean hasAnnotated = method.isAnnotationPresent(RequestMapping.class);
                if(hasAnnotated){
                    //5.取出注解
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    //6.得到注解的属性，用于填充SysLog对象
//                String[] value = requestMapping.value();
//                StringBuilder ss = new StringBuilder();
//                if(value.length>1){
//                    for(String path : value){
//                        ss.append(path).append(",");
//                    }
//                }else{
//                    ss.append(value);
//                }
                    String name = requestMapping.name();
                    //7.创建日志对象
                    SysLog sysLog = new SysLog();
                    sysLog.setIp(request.getRemoteAddr());//获取来访者ip
                    sysLog.setTime(new Date());//当前系统时间
                    sysLog.setMethod(method.getName());// 当前执行的方法
                    sysLog.setAction(name);//当前执行方法的说明
                    sysLog.setUserName(user.getUserName());
                    sysLog.setCompanyId(user.getCompanyId());
                    sysLog.setCompanyName(user.getCompanyName());
                    //8.保存日志
                    sysLogService.save(sysLog);
                }
            }
        }

        //获取执行方法所需的参数
        Object[] args = pjp.getArgs();
        //定义返回值
        Object rtValue = null;
        try{
            //切入点方法的执行
            rtValue = pjp.proceed(args);
        }catch (Throwable tl){
            tl.printStackTrace();
        }
        return rtValue;
    }
}
