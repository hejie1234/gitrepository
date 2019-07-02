package com.itheima.web.shiro;

import com.itheima.common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 自定义密码比较器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{

    /**
     * 密码比较
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //1.取出表单中的明文密码和用户名
        UsernamePasswordToken uToken = (UsernamePasswordToken)token;
        String email = uToken.getUsername();
        String password = new String(uToken.getPassword(),0,uToken.getPassword().length);
        //2.使用密码加密工具类把密码加密
        String md5Password = Encrypt.md5(password,email);
        //3.取出数据库的密码
        String dbPassword = info.getCredentials().toString();
        //4.校验密码并返回
        return super.equals(md5Password,dbPassword);//当返回值是false会抛出异常
    }
}
