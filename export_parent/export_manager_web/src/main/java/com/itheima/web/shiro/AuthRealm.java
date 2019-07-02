package com.itheima.web.shiro;

import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;
    /**
     * 授权
     * 把认证成功的用户权限全部都存起来
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1.获取认证成功的用户信息
        //如果我们只是当前一个用户登录的话，在开发阶段可以使用下面的代码
//        User user = (User)principals.getPrimaryPrincipal();//获取主要的主体信息
        User user = (User)principals.fromRealm(this.getName()).iterator().next();
        //2.获取当前用户的模块信息
        List<Module> moduleList = moduleService.findModuleByUserId(user);
        //3.创建用于封装用户权限名称的集合（注意要去重）
        Set<String> permissions = new HashSet<>();
        //4.遍历用户的模块集合
        for(Module module : moduleList){
            permissions.add(module.getName());
        }
        //5.创建返回值对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //6.把用户的权限信息加入到返回值中
//        info.addStringPermissions(permissions);//添加  以前有，追加到末尾
        info.setStringPermissions(permissions);//设置  以前有没有，都是新赋值
        //7.返回
        return info;
    }

    /**
     * 认证
     * @param token
     * @return 认证的信息  应该包含查询出来的用户信息，还有用户的凭证和每个用户自己的安全域的名称
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.把参数token转成UsernamePasswordToken
        UsernamePasswordToken uToken = (UsernamePasswordToken)token;
        //2.取出邮箱和密码
        String email = uToken.getUsername();
//        String password = new String(uToken.getPassword(),0,uToken.getPassword().length);
        //3.使用用户名去数据库查询
        User user = userService.findByEmail(email);
        //4.判断用户是否存在
        if(user != null){
            //5.创建方法返回值对象
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
            //6.返回
            return info;
        }
        //当此方法返回null的时候，shiro会抛出异常
        return null;
    }
}
