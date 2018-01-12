package com.sz.youban.service.sys.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sz.youban.entity.SysUserToken;

/**
 * <p>
  * 系统用户Token Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysUserTokenDao extends BaseMapper<SysUserToken> {

	SysUserToken queryByToken(String token);
    
}