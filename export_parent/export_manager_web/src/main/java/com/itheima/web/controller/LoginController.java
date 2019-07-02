package com.itheima.web.controller;

import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;


    /**
     * 使用apache shiro的登录方式
     * 登录功能
     * @param email
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(String email,String password) {
        //判断当前用户是否已经登录了
        User user = (User) session.getAttribute("user");
        if(user != null){
            return "home/main";
        }
        //1.判断email是否有值，如果没值的话，直接前往登录页面
        if(UtilFuns.isEmpty(email)){
            return "redirect:/login.jsp";
        }
        try {
            //2.获取到Subject
            Subject subject = SecurityUtils.getSubject();
            //3.创建带有用户名和密码的令牌
            UsernamePasswordToken token = new UsernamePasswordToken(email, password);
            //4.使用subject的login方法实现登录
            subject.login(token);//========================>需要有真正的去数据库查询用户，和密码比较的操作。此方法就要访问Realm的信息,Realm我们自己写
            //5.取出当前登录用户的信息
            user = (User) subject.getPrincipal();
            //6.登录成功，存入会话域中
            session.setAttribute("user", user);
            //7.获取当前登录用户的菜单
            List<Module> moduleList = moduleService.findModuleByUserId(user);
            //8.把查询出来的模块列表存入session域中
            session.setAttribute("modules", moduleList);
            //9.前往主页面
            return "home/main";// /WEB-INF/pages/home/main.jsp
        }catch (Exception e){
            //当shiro登录失败是会抛出异常
            request.setAttribute("error","用户名或密码错误");
            //重新前往登录页面
            return "forward:/login.jsp";
        }
    }



    /**
     * 传统登录方式
     * 登录功能
     * @param
     * @param
     * @return
	@RequestMapping("/login")
	public String login(String email,String password) {
	    //1.判断email是否有值，如果没值的话，直接前往登录页面
        if(UtilFuns.isEmpty(email)){
            return "redirect:/login.jsp";
        }
        //2.使用输入的邮箱去数据库查询用户
        User user = userService.findByEmail(email);
        //3.判断用户是否存在
        if(user == null){
            request.setAttribute("error","登录邮箱不存在或登录密码错误");
            return "forward:/login.jsp";
        }
        //4.判断密码是否匹配
        if(!user.getPassword().equals(password)){
            request.setAttribute("error","登录邮箱不存在或登录密码错误");
            return "forward:/login.jsp";
        }
        //5.登录成功，存入会话域中
        session.setAttribute("user",user);
        //6.获取当前登录用户的菜单
        List<Module> moduleList = moduleService.findModuleByUserId(user);
        //7.把查询出来的模块列表存入session域中
        session.setAttribute("modules",moduleList);
        //8.前往主页面
		return "home/main";// /WEB-INF/pages/home/main.jsp
	}*/

    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
        //SecurityUtils.getSubject().logout();   //登出
        return "forward:login.jsp";
    }

    @RequestMapping("/home")
    public String home(){
	    return "home/home";
    }
}
