package com.sz.youban.service.job.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sz.youban.entity.ScheduleJobLog;

/**
 * 定时任务日志
 * 
 * @author theodo
 * @email 36780272@qq.com
 * @date 2016年12月1日 下午10:30:02
 */
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLog> {

	List<ScheduleJobLog> queryPageList(Page<ScheduleJobLog> page, Map<String, Object> map);
	
}
