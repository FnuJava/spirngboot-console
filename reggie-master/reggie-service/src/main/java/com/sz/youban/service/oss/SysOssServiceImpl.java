package com.sz.youban.service.oss;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sz.youban.entity.SysOss;
import com.sz.youban.myinterface.oss.SysOssService;
import com.sz.youban.service.oss.mapper.SysOssDao;



@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOss> implements SysOssService {
	@Autowired
	private SysOssDao sysOssDao;
	
	@Override
	public Page<SysOss> queryPageList(Page<SysOss> page, Map<String, Object> map) {
		page.setRecords(sysOssDao.queryPageList(page, map));
		
		return page;
	}
		
	@Override
	public void deleteBatch(Long[] ids){
		sysOssDao.deleteBatch(ids);
	}

	
	
}
