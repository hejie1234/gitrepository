package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ContractProductDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domain.cargo.*;
import com.itheima.service.cargo.ContractProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {
        //1.设置分页信息
        PageHelper.startPage(page,size);
        //2.根据条件查询
        List<ContractProduct> contractProductList = contractProductDao.selectByExample(example);
        //3.创建返回值并返回
        return new PageInfo(contractProductList);
    }


    @Override
    public void save(ContractProduct contractProduct) {
        //1.判断是否输入了单价和数量
        double amount = 0d;
        if(contractProduct.getCnumber() !=null && contractProduct.getPrice()!=null){
            amount = contractProduct.getPrice()*contractProduct.getCnumber();
        }
        //2.设置货物的总金额
        contractProduct.setAmount(amount);
        //3.设置货物的id
        contractProduct.setId(UtilFuns.generateId());
        //4.根据合同id查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //5.设置合同的总金额
        contract.setTotalAmount(contract.getTotalAmount() + amount);
        //6.设置合同的货物款数
        contract.setProNum(contract.getProNum()+1);
        //7.保存货物
        contractProductDao.insertSelective(contractProduct);
        //8.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void update(ContractProduct contractProduct) {
        //使用货物对象中的id，查询数据库中的货物
        ContractProduct dbContractProduct = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        //取出数据库中原有的总金额
        double oldAmount = dbContractProduct.getAmount();
        //1.判断是否输入了单价和数量
        double amount = 0d;
        if(contractProduct.getCnumber() !=null && contractProduct.getPrice()!=null){
            amount = contractProduct.getPrice()*contractProduct.getCnumber();
        }
        //2.设置货物的总金额
        contractProduct.setAmount(amount);
        //3.根据合同id查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //4.设置合同的总金额，它是合同的总金额-原有货物的总金额+现有货物的总金额
        contract.setTotalAmount(contract.getTotalAmount()-oldAmount + amount);
        //5.更新货物
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
        //6.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        //1.根据id查询出货物
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        //获取货物的总金额
        double productAmount = contractProduct.getAmount();
        //2.创建附件的查询条件对象
        ExtCproductExample extCproductExample = new ExtCproductExample();
        //3.设置查询条件
        extCproductExample.createCriteria().andContractProductIdEqualTo(id);
        //4.查询出来所有附件
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
        //5.把货物下的所有附件都要删掉
        double extcAmount = 0d;
        for(ExtCproduct extCproduct : extCproducts){
            extcAmount+=extCproduct.getAmount();
            //删除附件
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
        }
        //删除货物
        contractProductDao.deleteByPrimaryKey(id);

        //6.根据id查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //7.设置购销合同的总金额
        contract.setTotalAmount(contract.getTotalAmount() - extcAmount - productAmount);
        //8.设置货物数-1
        contract.setProNum(contract.getProNum()-1);
        //9.设置附件数减去集合的长度
        contract.setExtNum(contract.getExtNum()-extCproducts.size());
        //10.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }




}
