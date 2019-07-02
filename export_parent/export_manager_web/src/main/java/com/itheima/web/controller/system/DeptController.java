package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.Dept;
import com.itheima.service.system.DeptService;
import com.itheima.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 部门的控制器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询所有部门，但是别忘了只能是当前用户所属企业的
     * 由于我们现在没有用户信息，也没有登录用户
     * 所以用户的所属企业只能先写死在此处
     * 等我们讲完了用户的登录，可以动态获取
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        //1.动态获取当前用户的企业id
        String companyId = "1";
        //2.调用service查询
        PageInfo pageInfo = deptService.findAll(companyId,page,size);
        //3.存入请求域中
        request.setAttribute("page",pageInfo);
        //4.返回
        return "system/dept/dept-list";
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
        return "system/dept/dept-add";
    }

    /**
     * 保存或者更新部门
     * @param dept
     * @return
     * 更新的问题：
     *    由于springmvc在实现封装请求参数时，会反射创建该对象
     *    Dept dept = Class.forName("dept").newInstance();
     *    此时的dept是所有字段全是null，当表单提供了哪些数据时，就哪些字段有值，其他字段全是null
     *    如果直接调用，就会出现，表单中不能修改的字段是数据丢失
     *
     * 解决思路：
     *   第一个：在非全字段更新时，不要直接更新表单提供的对象
     *          而是使用表单提供的id，先把数据库的数据查出来，然后再使用表单的数据替换掉数据库的数据
     *          更新的是数据库的对象。
     *          此种方式有个问题：需要跟数据库交互两次
     *   第二个：动态SQL语句
     *              判断当前字段是否有值，有值就更新，没有值，就不更新
     */
    @RequestMapping("/edit")
    public String edit(Dept dept){
        //1.判断是保存还是更新
        if(UtilFuns.isEmpty(dept.getId())){
            //保存
            dept.setCompanyId(super.getCurrentUserCompanyId());
            dept.setCompanyName(super.getCurrentUserCompanyName());
            deptService.save(dept);
        }else {
            //更新
            //此种方式的问题：跟数据库交互了两次才实现的更新
//            Dept dbDept = deptService.findById(dept.getId());
//            BeanUtils.copyProperties(dept,dbDept,new String[]{"companyId","companyName"});
//            deptService.update(dbDept);

            deptService.update(dept);
        }
        //2.重定向到列表页面
        return "redirect:/system/dept/list.do";
    }

    /**
     * 前往更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //1.根据id查询部门
        Dept dept = deptService.findById(id);
        //2.把查出来的部门存入请求域
        request.setAttribute("dept",dept);
        //3.根据企业id查询所有部门
        List<Dept> deptList = deptService.findAll(super.getCurrentUserCompanyId());
        //4.存入请求域中
        request.setAttribute("deptList",deptList);
        //5.前往编辑页面
        return "system/dept/dept-update";
    }

    /**
     * 根据id删除
     * 此处我们先不考虑删除部门时，同时删除用户
     * 也不考虑删除父部门，同时删除子部门
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        deptService.delete(id);
        return "redirect:/system/dept/list.do";
    }
}
