package com.sz.youban.console.controller.sys;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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
import com.sz.youban.entity.SysUser;
import com.sz.youban.myinterface.sys.SysUserRoleService;
import com.sz.youban.myinterface.sys.SysUserService;




/**
 * 系统用户
 * 
 * @author theodo
 * @email 36780272@qq.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		Page<SysUser> pageUtil = new Page<SysUser>(query.getPage(), query.getLimit());
		Page<SysUser> page = sysUserService.queryPageList(pageUtil,query);
		return R.ok().put("page", page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@Log("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		//Assert.isBlank(newPassword, "新密码不为能空");

		//sha256加密
		password = new Sha256Hash(password, getUser().getSalt()).toHex();
		//sha256加密
		newPassword = new Sha256Hash(newPassword, getUser().getSalt()).toHex();
		
		SysUser user = new SysUser();
		user.setUserId(getUserId());
		user.setPassword(newPassword);
		//更新密码
		boolean bFlag = sysUserService.updateById(user);
		if(!bFlag){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUser user = sysUserService.selectById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@Log("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUser user){
	//	ValidatorUtils.validateEntity(user, AddGroup.class);
		
		user.setCreateTime(new Date());
		user.setCreateUserId(getUserId());
		sysUserService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@Log("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUser user){
	//	ValidatorUtils.validateEntity(user, UpdateGroup.class);
		
		user.setCreateUserId(getUserId());
		sysUserService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@Log("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
	
	/**
	 * 导出用户
	 * @throws IOException 
	 */
	@Log("导出用户")
	@RequestMapping("/exportExcel")
	@RequiresPermissions("sys:user:exportExcel")
	public void exportExcel(HttpServletResponse response) throws IOException{
		Map<String, Object> params = new HashMap<String, Object>();
		List<SysUser> userList = (List<SysUser>)sysUserService.queryList(params);
		OutputStream os = response.getOutputStream();
        
		Map<String, String> map = new HashMap<String, String>();
        map.put("title", "用户信息表");
        map.put("total", userList.size()+" 条");
    //    map.put("date", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        
        //响应信息，弹出文件下载窗口
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition",  "attachment; filename="+ URLEncoder.encode("用户信息表.xls", "UTF-8"));  

        //TODO
/*        ExcelTemplate et = ExcelUtil.getInstance().handlerObj2Excel("web-info-template.xls", userList, SysUser.class, true);
        et.replaceFinalData(map);
        et.wirteToStream(os);*/
        os.flush();
        os.close();
	}
}
