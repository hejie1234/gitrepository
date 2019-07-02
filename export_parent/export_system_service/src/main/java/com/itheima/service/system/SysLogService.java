package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.SysLog;

/**
 * 系统日志的业务层接口
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public interface SysLogService {

    //查询全部
    PageInfo findAll(String companyId,int page,int size);

    //添加
    void save(SysLog log);
}
