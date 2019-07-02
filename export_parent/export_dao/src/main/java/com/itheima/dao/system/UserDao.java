package com.itheima.dao.system;

import com.itheima.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

	//根据企业id查询全部
	List<User> findAll(String companyId);

	//根据id查询
    User findById(String userId);

	//根据id删除
	int delete(String userId);

	//保存
	int save(User user);

	//更新
	int update(User user);
	//查询当前用户具备的角色id集合
	List<String> findUserRole(String id);

	//删除用户所具备的角色
	void deleteUserRole(String id);

	//保存用户角色到中间表
	void saveUserRole(@Param("userId") String userId,@Param("roleId") String roleId);

	//根据邮箱查询用户
	User findByEmail(String email);
}