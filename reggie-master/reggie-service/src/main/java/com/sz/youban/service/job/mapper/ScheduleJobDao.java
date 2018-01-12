package com.sz.youban.service.job.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sz.youban.entity.ScheduleJob;

/**
 * 定时任务
 * 
 * @author theodo
 * @email 36780272@qq.com
 * @date 2016年12月1日 下午10:29:57
 */
public interface ScheduleJobDao extends BaseMapper<ScheduleJob> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
	
	int deleteBatch(Object[] id);

	List<ScheduleJob> queryPageList(Page<ScheduleJob> page, Map<String, Object> map);
}
