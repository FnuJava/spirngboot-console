package com.sz.youban.myinterface.oss;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sz.youban.entity.SysOss;

/**
 * 文件上传
 * 
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017-03-25 12:13:26
 */
public interface SysOssService extends IService<SysOss> {
			
	void deleteBatch(Long[] ids);

	Page<SysOss> queryPageList(Page<SysOss> page, Map<String, Object> map);
}
