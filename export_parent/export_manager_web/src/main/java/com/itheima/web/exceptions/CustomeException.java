package com.itheima.web.exceptions;

/**
 * 自定义异常处理类
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class CustomeException extends Exception {

    private String message;

    public CustomeException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
