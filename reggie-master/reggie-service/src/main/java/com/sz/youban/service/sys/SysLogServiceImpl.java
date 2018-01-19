package com.sz.youban.service.sys;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sz.youban.entity.SysLog;
import com.sz.youban.myinterface.sys.SysLogService;
import com.sz.youban.service.sys.mapper.SysLogDao;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLog> implements SysLogService {

	@Autowired
	private SysLogDao sysLogDao;
	
	@Override
	public Page<SysLog> selectPageList(Page<SysLog> page, Map<String, Object> map) {
		page.setRecords(sysLogDao.selectPageList(page, map));
		return page;
	}
	
	public class  Inner{
		public  int  a = 3;
		
		
	}
	
}
