package com.sz.youban.service.sys.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sz.youban.entity.SysRoleDept;

/**
 * <p>
  * 角色与部门对应关系 Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysRoleDeptDao extends BaseMapper<SysRoleDept> {

	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long roleId);
	
	void save(Map<String, Object> map);
	
	void deleteByRoleId(Object roleId);
}