package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.UserDao;
import com.itheima.domain.system.User;
import com.itheima.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo findAll(String companyId, int page, int size) {
        //1.设置PageHelper的参数
        PageHelper.startPage(page,size);
        //2.查询结果集
        List<User> userList = userDao.findAll(companyId);
        //3.创建返回值并返回
        return  new PageInfo(userList);
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public void save(User user) {
        //1.设置user的id
        user.setId(UtilFuns.generateId());
        //2.保存
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    @Override
    public List<String> findUserRole(String id) {
        return userDao.findUserRole(id);
    }

    @Override
    public void changeUserRole(String id, String[] roleIds) {
        //1.根据用户id删除当前用户所具备的角色
        userDao.deleteUserRole(id);
        //2.遍历角色的id数组
        for(String roleId : roleIds) {
            //3.保存用户角色到中间表
            userDao.saveUserRole(id,roleId);
        }
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
