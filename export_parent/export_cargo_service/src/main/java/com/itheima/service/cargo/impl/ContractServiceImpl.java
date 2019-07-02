package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ContractProductDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 购销合同的业务层实现类
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class ContractServiceImpl implements ContractService{

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    /**
     * 购销合同应该是每个企业有它自己的合同
     * example是应该有条件的。条件是由控制器传入进来的。
     * @param example
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findAll(ContractExample example, int page, int size) {
        //1.设置分页信息
        PageHelper.startPage(page,size);
        //2.根据条件查询购销合同
        List<Contract> contractList = contractDao.selectByExample(example);
        //3.返回
        return new PageInfo(contractList);
    }

    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Contract contract) {
        //1.设置id
        contract.setId(UtilFuns.generateId());
        //2.设置其他信息
        contract.setState(0);//添加好的购销合同默认应该是草稿的状态
        contract.setProNum(0);//刚添加的购销合同中货物数和附件数应该都是0
        contract.setExtNum(0);
        contract.setTotalAmount(0d);//刚添加的购销合同的总金额应该是0
        contract.setCreateTime(new Date());//购销合同的创建时间
        //3.保存
        contractDao.insertSelective(contract);
    }

    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);
    }


    /**
     * @param id
     */
    @Override
    public void delete(String id) {
        //1.创建货物的查询条件对象
        ContractProductExample contractProductExample = new ContractProductExample();
        //2.设置查询条件
        contractProductExample.createCriteria().andContractIdEqualTo(id);
        //3.查询所有符合条件的货物
        List<ContractProduct> contractProducts = contractProductDao.selectByExample(contractProductExample);
        //4.遍历集合，删除所有
        for(ContractProduct contractProduct : contractProducts){
            contractProductDao.deleteByPrimaryKey(contractProduct.getId());
        }
        //5.创建附件的查询条件对象
        ExtCproductExample extCproductExample = new ExtCproductExample();
        //6.设置查询条件
        extCproductExample.createCriteria().andContractIdEqualTo(id);
        //7.查询所有符合条件的附件
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
        //8.遍历集合，删除所有
        for(ExtCproduct extCproduct : extCproducts){
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
        }
        //最后一步：删除合同
        contractDao.deleteByPrimaryKey(id);
    }

}
