package com.sz.youban.service.sys;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.sz.youban.common.utils.redis.SysConfigRedis;
import com.sz.youban.entity.SysConfig;
import com.sz.youban.myinterface.sys.CloudStorageConfig;
import com.sz.youban.myinterface.sys.SysConfigService;
import com.sz.youban.service.sys.mapper.SysConfigDao;



/**
 * <p>
 * 系统配置信息表 服务实现类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {
	
	@Autowired
	private SysConfigDao sysConfigDao;
	@Autowired
	private SysConfigRedis sysConfigRedis;
	
	public Page<SysConfig> selectSysConfigList(Page<SysConfig> page, Map<String, Object> map) {
	    page.setRecords(sysConfigDao.selectSysConfigList(page, map));
	    return page;
	}

	@Override
	@Transactional
	public void updateValueByKey(String key, String value) {
		sysConfigDao.updateValueByKey(key, value);
		sysConfigRedis.delete(key);
	}
	
	@Override
	public String getValue(String key) {
		SysConfig config = (SysConfig) sysConfigRedis.get(key,SysConfig.class);
		if(config == null){
			config = sysConfigDao.queryByKey(key);
			sysConfigRedis.saveOrUpdate(key,config);
		}

		return config == null ? null : config.getValue();
	}

	@Override
	@Transactional
	public CloudStorageConfig getConfigObject(String key, Class<CloudStorageConfig> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("获取参数失败");
		}
	}
	
}
