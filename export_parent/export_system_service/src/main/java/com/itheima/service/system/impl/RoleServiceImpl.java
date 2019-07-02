package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.ModuleDao;
import com.itheima.dao.system.RoleDao;
import com.itheima.domain.system.Role;
import com.itheima.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo findAll(String companyId, int page, int size) {
        //1.设置分页
        PageHelper.startPage(page,size);
        //2.查询所有
        List<Role> roleList = roleDao.findAll(companyId);
        //3.创建返回值并返回
        return new PageInfo(roleList);
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    @Override
    public void save(Role role) {
        //1.设置id
        role.setId(UtilFuns.generateId());
        //2.保存
        roleDao.save(role);
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }

    @Override
    public List<Map> findRoleModule(String id) {
        return moduleDao.findRoleModule(id);
    }

    @Override
    public void updateRoleModule(String id, String moduleIds) {
        //1.删除当前角色的所有模块
        roleDao.deleteRoleModule(id);
        //2.把模块和角色存入role_module的中间表中
        String[] moduleIdArray = moduleIds.split(",");
        for(String moduleId : moduleIdArray){
            roleDao.saveRoleModule(id,moduleId);
        }
    }

    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }
}
