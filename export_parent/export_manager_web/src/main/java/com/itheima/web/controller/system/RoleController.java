package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.Role;
import com.itheima.service.system.RoleService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 角色的控制器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;


    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        //1.动态获取当前角色的企业id
        String companyId = "1";
        //2.调用service查询
        PageInfo pageInfo = roleService.findAll(companyId,page,size);
        //3.存入请求域中
        request.setAttribute("page",pageInfo);
        //4.返回
        return "system/role/role-list";
    }

    /**
     * 前往添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "system/role/role-add";
    }

    /**
     * 保存或者更新角色
     * @param role
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Role role){
        role.setCompanyId(super.getCurrentUserCompanyId());
        role.setCompanyName(super.getCurrentUserCompanyName());
        //1.判断是保存还是更新
        if(UtilFuns.isEmpty(role.getId())){
            //保存
            roleService.save(role);
        }else {
            //更新
            roleService.update(role);
        }
        //2.重定向到列表页面
        return "redirect:/system/role/list.do";
    }

    /**
     * 前往更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //1.根据id查询角色
        Role role = roleService.findById(id);
        //2.把查出来的角色存入请求域
        request.setAttribute("role",role);
        //3.前往编辑页面
        return "system/role/role-update";
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }

    /**
     * 前往给角色分配模块的页面
     * @return
     */
    @RequestMapping("/roleModule")
    public String roleModule(@RequestParam("roleid") String id){
        //1.根据Id查询角色
        Role role = roleService.findById(id);
        //2.存入请求域中
        request.setAttribute("role",role);
        //3.前往分配模块的页面
        return "system/role/role-module";
    }

    /**
     * ajax异步加载树形结构的数据内容
     * @param id
     * 把对象转成json：
     *     需要使用springmvc提供的注解：@ResponseBody，明确，它只是做了把响应数据用流的形式输出。并没有拼接json的功能
     *     response.getOutputStream().write(xxx);
     *     同时必须要配合jackson第三方开源包，才能实现转换成json
     *          jackson-core
     *          jackson-databind
     *          jackson-annotations
     *          而且要求必须是2.8.0版本及以上才支持spring5
     * 生成树的数据格式是：
     *     { id:221, pId:22, name:"随意勾选 2-2-1", checked:true}
    *          id	    父id      名称		    是否选中
     *     json格式数据的特点：
     *          key:value  它是map结构
     */
    @RequestMapping("/initModuleData")
    public @ResponseBody List<Map> initModuleData(String id){
        //1.获取树形结构菜单的数据并返回结果
        return roleService.findRoleModule(id);
    }

    /**
     *
     * @param id            角色的id
     * @param moduleIds     模块的id集合，拼接成了字符串，用的是逗号分隔
     * @return
     */
    @RequestMapping("/updateRoleModule")
    public String updateRoleModule(@RequestParam("roleid") String id,String moduleIds){
        //1.调用service实现模块（权限）的分配
        roleService.updateRoleModule(id,moduleIds);
        //2.重定向到角色的列表页面
        return "redirect:/system/role/list.do";
    }
}
