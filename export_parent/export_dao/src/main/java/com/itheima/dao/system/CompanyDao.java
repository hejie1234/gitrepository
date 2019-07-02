package com.itheima.dao.system;

import com.itheima.domain.system.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 企业的持久层接口
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public interface CompanyDao {

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
     * 查询总记录条数
     * @return
     */
    int findTotal();

    /**
     * 查询带有分页的结果集
     * @param page
     * @param size
     * @return
     * 它就相当于创建了一个Map
     * Map  map = new HashMap();
     * map.put("page",page);
     * map.put("size",size);
     */
    List<Company> findPage(@Param("page") int page,@Param("size") int size);
}
