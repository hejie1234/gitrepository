package com.itheima.dao.system;

import com.itheima.domain.system.Module;

import java.util.List;
import java.util.Map;

/**
 */
public interface ModuleDao {

    //根据id查询
    Module findById(String moduleId);

    //根据id删除
    int delete(String moduleId);

    //添加用户
    int save(Module module);

    //更新用户
    int update(Module module);

    //查询全部
    List<Module> findAll();

    //查询符合条件的树形结构json数据
    List<Map> findRoleModule(String roleId);

    //根据用户id获取用户所具备的模块
    List<Module> findModuleByUserId(String userId);

    //根据belong信息获取菜单
    List<Module> findByBelong(Integer belong);
}