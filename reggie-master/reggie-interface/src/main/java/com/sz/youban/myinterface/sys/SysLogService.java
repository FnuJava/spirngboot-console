package com.sz.youban.myinterface.sys;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sz.youban.entity.SysLog;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysLogService extends IService<SysLog> {
	public Page<SysLog> selectPageList(Page<SysLog> page, Map<String, Object> map);
}
