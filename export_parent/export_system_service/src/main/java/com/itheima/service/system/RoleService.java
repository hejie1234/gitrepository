package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Role;

import java.util.List;
import java.util.Map;

/**
 * 角色的业务层接口
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public interface RoleService {

    /**
     * 查询所有 带分页
     * @param companyId  查询角色的条件，获取的角色结果集是指定企业下的角色
     * @param page  当前页
     * @param size  页大小
     * @return
     */
    PageInfo findAll(String companyId, int page, int size);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Role findById(String id);

    /**
     * 保存
     * @param role
     */
    void save(Role role);

    /**
     * 更新
     * @param role
     */
    void update(Role role);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);

    /**
     * 查询模块树形结构的数据列表
     * @param id
     * @return
     */
    List<Map> findRoleModule(String id);

    /**
     * 实现角色的模块分配
     * @param id
     * @param moduleIds
     */
    void updateRoleModule(String id,String moduleIds);

    /**
     *
     * @param companyId
     * @return
     */
    List<Role> findAll(String companyId);

}
