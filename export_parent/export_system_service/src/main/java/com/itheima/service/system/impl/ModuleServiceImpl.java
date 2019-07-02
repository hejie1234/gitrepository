package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.ModuleDao;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class ModuleServiceImpl implements ModuleService{

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo findAll(int page, int size) {
        //1.设置分页信息
        PageHelper.startPage(page,size);
        //2.查询所有模块
        List<Module> moduleList = moduleDao.findAll();
        //3.创建返回值并返回
        return new PageInfo(moduleList);
    }

    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    @Override
    public void save(Module module) {
        //1.设置id
        module.setId(UtilFuns.generateId());
        //2.保存
        moduleDao.save(module);
    }

    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    @Override
    public void delete(String id) {
        moduleDao.delete(id);
    }

    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    @Override
    public List<Module> findModuleByUserId(User user) {
        //1.判断当前用户是saas管理员还是企业管理员还是企业普通用户
        if(user.getDegree() == 0){
            //saas管理员
            return moduleDao.findByBelong(0);
        }else if(user.getDegree() == 1){
            //企业管理员
            return moduleDao.findByBelong(1);
        }else {
            //企业普通用户
            return moduleDao.findModuleByUserId(user.getId());
        }
    }
}
