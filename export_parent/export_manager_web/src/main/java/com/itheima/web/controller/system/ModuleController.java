package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.Module;
import com.itheima.service.system.ModuleService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 模块的控制器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;

    /**
     * 查询所有
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        //1.调用service查询
        PageInfo pageInfo = moduleService.findAll(page,size);
        //3.存入请求域中
        request.setAttribute("page",pageInfo);
        //4.返回
        return "system/module/module-list";
    }

    /**
     * 前往添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        //1.查询所有模块，目的是为了新增时可以选择父模块
        List<Module> moduleList = moduleService.findAll();
        //2.存入请求域中
        request.setAttribute("menus",moduleList);
        return "system/module/module-add";
    }

    /**
     * 保存或者更新模块
     * @param module
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Module module){
        //1.判断是保存还是更新
        if(UtilFuns.isEmpty(module.getId())){
            //保存
            moduleService.save(module);
        }else {
            //更新
            moduleService.update(module);
        }
        //2.重定向到列表页面
        return "redirect:/system/module/list.do";
    }

    /**
     * 前往更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //1.根据id查询模块
        Module module = moduleService.findById(id);
        //2.把查出来的模块存入请求域
        request.setAttribute("module",module);
        //3.查询所有模块，目的是为了新增时可以选择父模块
        List<Module> moduleList = moduleService.findAll();
        //4.存入请求域中
        request.setAttribute("menus",moduleList);
        //5.前往编辑页面
        return "system/module/module-update";
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }
}
