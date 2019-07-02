package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.DeptDao;
import com.itheima.domain.system.Dept;
import com.itheima.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;

    @Override
    public PageInfo findAll(String companyId, int page, int size) {
        //1.设置PageHelper的参数
        PageHelper.startPage(page,size);
        //2.查询结果集
        List<Dept> deptList = deptDao.findAll(companyId);
        //3.创建返回值并返回
        return  new PageInfo(deptList);
    }

    @Override
    public Dept findById(String id) {
        return deptDao.findById(id);
    }

    @Override
    public void save(Dept dept) {
        //1.设置dept的id
        dept.setId(UtilFuns.generateId());
        //2.保存
        deptDao.save(dept);
    }

    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    @Override
    public void delete(String id) {
        deptDao.delete(id);
    }

    @Override
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }
}
