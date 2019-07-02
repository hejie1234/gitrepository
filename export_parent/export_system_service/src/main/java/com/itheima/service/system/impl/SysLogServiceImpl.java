package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.SysLogDao;
import com.itheima.domain.system.SysLog;
import com.itheima.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public PageInfo findAll(String companyId,int page,int size) {
        //1.设置分页信息
        PageHelper.startPage(page,size);
        //2.查询所有
        List<SysLog> sysLogList = sysLogDao.findAll(companyId);
        //3.返回
        return new PageInfo(sysLogList);
    }

    @Override
    public void save(SysLog log) {
        //1.设置id
        log.setId(UtilFuns.generateId());
        //2.保存
        sysLogDao.save(log);
    }
}
