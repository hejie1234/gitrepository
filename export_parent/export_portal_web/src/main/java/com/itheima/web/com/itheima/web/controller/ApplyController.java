package com.itheima.web.com.itheima.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.domain.system.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前台申请的控制器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
public class ApplyController {

    @Reference
    private CompanyService companyService;

    /**
     * 申请企业注册的方法
     * @param company
     * @return
     */
    @RequestMapping("/apply")
    public @ResponseBody String apply(Company company){
        companyService.save(company);
        return "1";
    }
}
