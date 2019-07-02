package com.itheima.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ExtCproduct;
import com.itheima.domain.cargo.ExtCproductExample;
import com.itheima.service.cargo.ExtCproductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class ExtCproductServiceImpl implements ExtCproductService {

    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ContractDao contractDao;


    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        //1.设置分页信息
        PageHelper.startPage(page,size);
        //2.使用条件查询
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(example);
        //3.返回
        return new PageInfo(extCproductList);
    }


    @Override
    public void save(ExtCproduct extCproduct) {
        //1.判断是否提供了数量和单价
        double amount = 0d;
        if(extCproduct.getCnumber()!=null && extCproduct.getPrice() !=null){
            amount = extCproduct.getPrice()*extCproduct.getCnumber();
        }
        //2.设置附件的总金额
        extCproduct.setAmount(amount);
        //3.查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //4.设置合同的总金额
        contract.setTotalAmount(contract.getTotalAmount()+amount);
        //5.设置合同的附件数
        contract.setExtNum(contract.getExtNum()+1);
        //6.保存附件
        extCproduct.setId(UtilFuns.generateId());
        extCproductDao.insertSelective(extCproduct);
        //7.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);

    }

    @Override
    public void update(ExtCproduct extCproduct) {
        //使用附件对象中的id，查询数据库中的附件
        ExtCproduct dbExtCproduct = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        //得到数据库中原有的总金额
        double oldAmount = dbExtCproduct.getAmount();
        //1.判断是否提供了数量和单价
        double amount = 0d;
        if(extCproduct.getCnumber()!=null && extCproduct.getPrice() !=null){
            amount = extCproduct.getPrice()*extCproduct.getCnumber();
        }
        //2.设置附件的总金额
        extCproduct.setAmount(amount);
        //3.查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //4.设置合同的总金额
        contract.setTotalAmount(contract.getTotalAmount()-oldAmount+amount);
        //6.更新附件
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
        //7.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        //1.根据id查询附件
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        //2.取出附件的总金额
        double amount = extCproduct.getAmount();
        //3.根据附件中的合同id，查询购销合同
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //4.删除附件
        extCproductDao.deleteByPrimaryKey(id);
        //5.设置合同总金额
        contract.setTotalAmount(contract.getTotalAmount()-amount);
        //6.设置附件数
        contract.setExtNum(contract.getExtNum()-1);
        //7.更新购销合同
        contractDao.updateByPrimaryKeySelective(contract);
    }


}
