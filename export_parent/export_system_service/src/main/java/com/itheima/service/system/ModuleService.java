package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;

import java.util.List;

/**
 * 模块的业务层接口
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public interface ModuleService {

    /**
     * 查询所有 带分页
     * @param page  当前页
     * @param size  页大小
     * @return
     */
    PageInfo findAll(int page, int size);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Module findById(String id);

    /**
     * 保存
     * @param module
     */
    void save(Module module);

    /**
     * 更新
     * @param module
     */
    void update(Module module);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);

    /**
     * 查询所有模块(为了添加和更新时选择父模块使用)
     * @return
     */
    List<Module> findAll();

    /**
     * 根据用户id查询模块列表，用于动态获取当前用户的菜单
     * @param user
     * @return
     */
    List<Module> findModuleByUserId(User user);
}
