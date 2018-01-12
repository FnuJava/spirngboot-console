package com.sz.youban.console.controller.sys;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.sz.youban.common.bean.R;
import com.sz.youban.common.bean.page.Query;
import com.sz.youban.console.aop.Log;
import com.sz.youban.console.controller.base.BaseController;
import com.sz.youban.entity.SysConfig;
import com.sz.youban.myinterface.sys.SysConfigService;




/**
 * <p>
 * 系统配置信息表 前端控制器
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends BaseController {
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		Page<SysConfig> pageUtil = new Page<SysConfig>(query.getPage(), query.getLimit());
		
		Page<SysConfig> page = sysConfigService.selectSysConfigList(pageUtil,query);
		
		//PageUtils pageUtil = new PageUtils(configList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", page);
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public R info(@PathVariable("id") Long id){
		SysConfig config = sysConfigService.selectById(id);
		
		return R.ok().put("config", config);
	}
	
	/**
	 * 保存配置
	 */
	@Log("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public R save(@RequestBody SysConfig config){
		//ValidatorUtils.validateEntity(config);

		sysConfigService.insert(config);
		
		return R.ok();
	}
	
	/**
	 * 修改配置
	 */
	@Log("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public R update(@RequestBody SysConfig config){
		//ValidatorUtils.validateEntity(config);
		
		sysConfigService.updateById(config);
		
		return R.ok();
	}
	
	/**
	 * 删除配置
	 */
	@Log("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public R delete(@RequestBody Long[] ids){
		List<Long> idsList = Arrays.stream(ids).collect(Collectors.toList());  
		
		logger.debug(idsList.toString());
		
		sysConfigService.deleteBatchIds(idsList);
		
		return R.ok();
	}

	
}
