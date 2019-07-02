package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.Dept;
import com.itheima.domain.system.Role;
import com.itheima.domain.system.User;
import com.itheima.service.system.DeptService;
import com.itheima.service.system.RoleService;
import com.itheima.service.system.UserService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户的控制器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;
    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        //1.动态获取当前用户的企业id
        String companyId = "1";
        //2.调用service查询
        PageInfo pageInfo = userService.findAll(companyId,page,size);
        //3.存入请求域中
        request.setAttribute("page",pageInfo);
        //4.返回
        return "system/user/user-list";
    }

    /**
     * 前往添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        //1.根据企业id查询所有部门
        List<Dept> deptList = deptService.findAll(super.getCurrentUserCompanyId());
        //2.存入请求域中
        request.setAttribute("deptList",deptList);
        return "system/user/user-add";
    }

    /**
     * 保存或者更新用户
     * @param user
     * @return
     */
    @RequestMapping("/edit")
    public String edit(User user){
        user.setCompanyId(super.getCurrentUserCompanyId());
        user.setCompanyName(super.getCurrentUserCompanyName());
        //1.判断是保存还是更新
        if(UtilFuns.isEmpty(user.getId())){
            //保存
            userService.save(user);
        }else {
            //更新
            userService.update(user);
        }
        //2.重定向到列表页面
        return "redirect:/system/user/list.do";
    }

    /**
     * 前往更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //1.根据id查询用户
        User user = userService.findById(id);
        //2.把查出来的用户存入请求域
        request.setAttribute("user",user);
        //3.根据企业id查询所有部门
        List<Dept> deptList = deptService.findAll(super.getCurrentUserCompanyId());
        //4.存入请求域中
        request.setAttribute("deptList",deptList);
        //5.前往编辑页面
        return "system/user/user-update";
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        userService.delete(id);
        return "redirect:/system/user/list.do";
    }

    /**
     * 前往给用户分配角色的页面
     * @param id 用户的id
     * @return
     */
    @RequestMapping("/roleList")
    public String roleList(String id){
        //1.根据id查询用户
        User user = userService.findById(id);
        //2.获取所有角色
        List<Role> roleList = roleService.findAll(super.getCurrentUserCompanyId());//是查询角色表
        //3.获取当前用户所具备的角色 用户的角色信息，只包含角色的id即可
        List<String> userRoleList = userService.findUserRole(id);//只需要查询角色用户中间表即可
        //4.存入请求域中
        request.setAttribute("user",user);
        request.setAttribute("roleList",roleList);
        request.setAttribute("userRoleStr",userRoleList.toString());
        return "system/user/user-role";
    }

    /**
     * 给用户分配角色
     * @return
     */
    @RequestMapping("/changeRole")
    public String changeRole(@RequestParam("userid") String id,String[] roleIds){
        //1.调用service执行给用户分配角色
        userService.changeUserRole(id,roleIds);
        //2.重定向到用户的列表页面
        return "redirect:/system/user/list.do";
    }
}
