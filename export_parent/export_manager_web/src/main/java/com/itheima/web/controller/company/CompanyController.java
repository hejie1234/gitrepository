package com.itheima.web.controller.company;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.domain.system.Company;
import com.itheima.service.company.CompanyService;
import com.itheima.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 企业的控制器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Reference
    private CompanyService companyService;

    /**
     * 获取企业列表，带分页
     * @return
     */
    @RequiresPermissions("企业管理")
    @RequestMapping(value="/list",name="查询所有企业")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size){
        //1.调用业务层查询
        PageInfo pageInfo = companyService.findByPageHelper(page,size);
        //2.存入请求域中
        request.setAttribute("page",pageInfo);
        //3.转发到列表页面
        return "company/company-list";
        //request.getRequestDispatcher("/WEB-INF/pages/.jsp").forward(request,response);
    }

    //有分页，但是用的不是pageHelper
//    @RequestMapping("/list")
//    public String list(HttpServletRequest request, @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "2") int size){
//        //1.调用业务层查询
//        PageResult result = companyService.findPage(page,size);
//        //2.存入请求域中
//        request.setAttribute("page",result);
//        //3.转发到列表页面
//        return "company/company-list";
//        //request.getRequestDispatcher("/WEB-INF/pages/.jsp").forward(request,response);
//    }

    //没分页
//    @RequestMapping("/list")
//    public String list(HttpServletRequest request){
//        //1.调用业务层查询
//        List<Company> companyList = companyService.findAll();
//        //2.存入请求域中
//        request.setAttribute("list",companyList);
//        //3.转发到列表页面
//        return "company/company-list";
//        //request.getRequestDispatcher("/WEB-INF/pages/.jsp").forward(request,response);
//
//    }

    /**
     * 前往新增页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(){
        return "company/company-add";
    }

    /**
     * 保存或者更新
     * @param company
     * @return
     * 转发：  1次请求    地址栏不变    请求域中数据不丢失    服务器内部跳转           只能在当前应用中转发
     * 重定向：2此请求    地址栏改变    请求域中数据丢失      浏览器发送新请求         可以定向到任意应用
     */
    @RequestMapping("/edit")
    public String edit(Company company)throws Exception{
        /*if(UtilFuns.isEmpty(company.getName())){
            throw new CustomeException("企业名称必须存在");
        }*/
//        int i=1/0;
        //1.判断当前提交的company有没有id
        if(UtilFuns.isEmpty(company.getId())){
            //保存
            companyService.save(company);
        }else{
            //更新
            companyService.update(company);
        }

        return "redirect:/company/list.do";//必须写！原因：重定向是两次请求，是浏览器行为。就相当于在浏览器上输入访问地址
    }

    /**
     * 前往更新页面
     * @param id
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //1.根据id查询企业
        Company company = companyService.findById(id);
        //2.把企业存入请求域中
        request.setAttribute("company",company);
        //3.转发到更新页面
        return "company/company-update";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id){
        //1.调用service删除
        companyService.delete(id);
        //2.重定向到列表页面
        return "redirect:/company/list.do";
    }
}
