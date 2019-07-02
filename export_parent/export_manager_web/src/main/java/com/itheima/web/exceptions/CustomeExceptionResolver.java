package com.itheima.web.exceptions;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义异常处理器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Component
public class CustomeExceptionResolver implements HandlerExceptionResolver{

    /**
     * 自定义异常处理器的处理规则
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //创建返回值对象
        ModelAndView mv = new ModelAndView();
        //设置响应视图
        mv.setViewName("error");
        //1.判断是什么异常
        if(ex instanceof CustomeException){
            //业务异常。需要给用户提示
            mv.addObject("errorMsg",ex.getMessage());
        }else {
            //系统异常。给开发人员看的
            mv.addObject("errorMsg","服务器忙！");
            ex.printStackTrace();
        }
        return mv;
    }
}
