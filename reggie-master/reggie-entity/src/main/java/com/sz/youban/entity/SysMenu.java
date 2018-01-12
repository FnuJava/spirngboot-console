package com.sz.youban.entity;

import java.io.Serializable;
import java.util.List;

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
 * 菜单管理
 * </p>
 *
 * @author Reggie
 * @since 2017-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

	@TableId(value="menu_id", type= IdType.AUTO)
	private Long menuId;
    /**
     * 父菜单ID，一级菜单为0
     */
	@TableField("parent_id")
	private Long parentId;
    /**
     * 菜单名称
     */
	private String name;
    /**
     * 菜单URL
     */
	private String url;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
	private String perms;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
	private Integer type;
    /**
     * 菜单图标
     */
	private String icon;
    /**
     * 排序
     */
	@TableField("order_num")
	private Integer orderNum;
	
	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;
	
	@TableField(exist=false)
	private List<?> list;


	@Override
	protected Serializable pkVal() {
		return this.menuId;
	}

}
