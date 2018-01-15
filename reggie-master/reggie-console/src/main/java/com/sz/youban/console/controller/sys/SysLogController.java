package com.sz.youban.console.controller.sys;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.sz.youban.common.bean.R;
import com.sz.youban.common.bean.page.Query;
import com.sz.youban.console.controller.base.BaseController;
import com.sz.youban.entity.SysLog;
import com.sz.youban.myinterface.sys.SysLogService;


/**
 * 系统日志
 * 
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017-03-08 10:40:56
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		Page<SysLog> pageUtil = new Page<SysLog>(query.getPage(), query.getLimit());
		Page<SysLog> page = sysLogService.selectPageList(pageUtil,query);
		
		return okPage(page);
	}
	
}
