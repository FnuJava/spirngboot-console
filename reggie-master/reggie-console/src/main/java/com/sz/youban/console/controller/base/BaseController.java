package com.sz.youban.console.controller.base;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sz.youban.entity.SysUser;

/**
 * Controller公共组件
 * 
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017年10月9日 下午9:42:26
 */
public abstract class BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUser getUser() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected Long getDeptId() {
		return getUser().getDeptId();
	}
	
}
