package com.itheima.dao.system;

import com.itheima.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {

    //根据id查询
    Role findById(String id);

    //查询全部用户
    List<Role> findAll(String companyId);

	//根据id删除
    int delete(String id);

	//添加
    int save(Role role);

	//更新
    int update(Role role);

    /**
     * 删除中间表中的数据，条件是中间表的role_id和参数相同
     * @param id
     */
    void deleteRoleModule(String id);

    /**
     * 保存角色模块中间表数据
     * @param roleId
     * @param moduleId
     */
    void saveRoleModule(@Param("roleId") String roleId, @Param("moduleId") String moduleId);
}