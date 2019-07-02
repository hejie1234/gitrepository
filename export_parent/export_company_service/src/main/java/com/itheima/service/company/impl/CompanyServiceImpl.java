package com.itheima.service.company.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.entity.PageResult;
import com.itheima.dao.system.CompanyDao;
import com.itheima.domain.system.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service//千万别忘了把注解换成是dubbo的
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    @Override
    public void save(Company company) {
        //1.生成id
        String id = UUID.randomUUID().toString().replace("-","").toUpperCase();
        //2.给company赋值
        company.setId(id);
        //3.调用dao保存
        companyDao.save(company);
    }

    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }


    @Override
    public PageResult findPage(int page, int size) {
        //1.查询总记录条数
        int total = companyDao.findTotal();
        //2.查询带有分页的结果集
        List<Company> companies = companyDao.findPage((page-1)*size,size);
        //3.创建返回值对象
        PageResult pageResult = new PageResult( total, companies,  page,  size);
        //4.返回
        return pageResult;
    }

    @Override
    public PageInfo findByPageHelper(int page, int size) {
        //1.使用PageHelper类中提供的静态方法startPage
        PageHelper.startPage(page,size);
        //2.查询所有数据
        List<Company> companies = companyDao.findAll();//此次查询会被分页
        //3.创建返回值对象
        PageInfo pageInfo = new PageInfo(companies);
        //4.返回
        return pageInfo;
    }

}
