package com.sz.youban.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author Reggie
 * @since 2017-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dept")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;

	@TableId(value="dept_id", type= IdType.AUTO)
	private Long deptId;
    /**
     * 上级部门ID，一级部门为0
     */
	@TableField("parent_id")
	private Long parentId;
    /**
     * 部门名称
     */
	private String name;
    /**
     * 排序
     */
	@TableField("order_num")
	private Integer orderNum;
    /**
     * 是否删除  -1：已删除  0：正常
     */
	@TableField("del_flag")
	private Integer delFlag;
	
	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;


	@Override
	protected Serializable pkVal() {
		return this.deptId;
	}

}
