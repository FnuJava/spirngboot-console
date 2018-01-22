package com.sz.youban.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author Reggie
 * @since 2017-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

	@TableId(value="role_id", type= IdType.AUTO)
	private Long roleId;
    /**
     * 角色名称
     */
	@TableField("role_name")
	private String roleName;
    /**
     * 备注
     */
	private String remark;
    /**
     * 部门ID
     */
	@TableField("dept_id")
	private Long deptId;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;

	/**
	 * 部门名称
	 */
	@TableField(exist=false)
	private String deptName;
	@TableField(exist=false)
	private List<Long> menuIdList;
	@TableField(exist=false)
	private List<Long> deptIdList;

	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
