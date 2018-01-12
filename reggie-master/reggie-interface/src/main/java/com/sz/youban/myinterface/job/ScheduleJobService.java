package com.sz.youban.myinterface.job;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sz.youban.entity.ScheduleJob;

/**
 * 定时任务
 * 
 * @author theodo
 * @email 36780272@qq.com
 * @date 2016年11月28日 上午9:55:32
 */
public interface ScheduleJobService extends IService<ScheduleJob> {

	Page<ScheduleJob> queryPageList(Page<ScheduleJob> pageUtil, Map<String, Object> map);
	
	/**
	 * 保存定时任务
	 */
	void save(ScheduleJob scheduleJob);
	
	/**
	 * 更新定时任务
	 */
	void update(ScheduleJob scheduleJob);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Long[] jobIds);
	
	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Long[] jobIds, int status);
	
	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pause(Long[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(Long[] jobIds);
}
