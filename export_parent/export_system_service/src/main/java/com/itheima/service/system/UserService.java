package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.User;

import java.util.List;

/**
 * 用户的业务层接口
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public interface UserService {

    /**
     * 查询所有 带分页
     * @param companyId  查询用户的条件，获取的用户结果集是指定企业下的用户
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
    User findById(String id);

    /**
     * 保存
     * @param user
     */
    void save(User user);

    /**
     * 更新
     * @param user
     */
    void update(User user);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);

    /**
     * 查询当前用户所具备的角色id列表
     * @param id
     * @return
     */
    List<String> findUserRole(String id);

    /**
     * 给用户分配角色
     * @param id
     * @param roleIds
     */
    void changeUserRole(String id,String[] roleIds);

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    User findByEmail(String email);
}
