package com.itheima.web.convertes;

import com.itheima.common.utils.UtilFuns;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义类型转换器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class StringToDateConverter implements Converter<String,Date> {

    //支持注入
    private String pattern;

    public void setPattern(String pattern){
        this.pattern = pattern;
    }

    @Override
    public Date convert(String source) {
        try{
            //1.给解析规则提供默认值
            if(UtilFuns.isEmpty(pattern)){
                pattern = "yyyy-MM-dd";
            }
            //2.使用解析规则创建解析对象
            DateFormat format = new SimpleDateFormat(pattern);
            //3.解析日期
            Date date = format.parse(source);
            //4.返回
            return date;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
