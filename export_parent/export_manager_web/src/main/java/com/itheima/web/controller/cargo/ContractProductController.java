package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ContractProductExample;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.ContractProductService;
import com.itheima.service.cargo.FactoryService;
import com.itheima.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 货物的控制器
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    @Reference
    private FactoryService factoryService;

    /**
     * 查询合同下货物的列表
     * @param contractId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(String contractId, @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        //1.创建查询条件
        ContractProductExample contractProductExample = new ContractProductExample();
        //2.设置查询条件，合同的id等于方法的形参contractId
        contractProductExample.createCriteria().andContractIdEqualTo(contractId);
        //3.使用条件查询
        PageInfo pageInfo = contractProductService.findAll(contractProductExample,page,size);
        //4.存入请求域中
        request.setAttribute("page",pageInfo);
        //5.设置合同的id存入请求域
        request.setAttribute("contractId",contractId);
        //6.创建factory的特例（查询条件）
        FactoryExample factoryExample = new FactoryExample();
        //7.设置查询条件
        factoryExample.createCriteria().andCtypeEqualTo("货物");
        //8.查询生产厂家
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        //9.把厂家列表存入请求域中
        request.setAttribute("factoryList",factoryList);
        //10.返回
        return "cargo/product/product-list";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(){

        return "cargo/product/product-update";
    }
}
