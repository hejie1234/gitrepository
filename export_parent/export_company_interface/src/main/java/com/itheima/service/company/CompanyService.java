package com.itheima.service.company;

import com.github.pagehelper.PageInfo;
import com.itheima.common.entity.PageResult;
import com.itheima.domain.system.Company;

import java.util.List;

/**
 * 企业的业务层接口
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public interface CompanyService {

    /**
     * 查询所有
     * @return
     */
    List<Company> findAll();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Company findById(String id);

    /**
     * 保存
     * @param company
     */
    void save(Company company);

    /**
     * 更新
     * @param company
     */
    void update(Company company);

    /**
     * 删除
     * @param id
     */
    void delete(String id);

    /**
     * 查询带有分页的结果集
     * @param page  当前页
     * @param size  每页显示的条数
     * @return
     */
    PageResult findPage(int page, int size);

    /**
     * 查询带有分页的结果集，使用mybatis的pagehelper插件
     * @param page
     * @param size
     */
    PageInfo findByPageHelper(int page, int size);
}
