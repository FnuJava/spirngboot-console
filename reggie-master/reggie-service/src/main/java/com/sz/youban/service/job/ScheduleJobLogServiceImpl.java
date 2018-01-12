package com.sz.youban.service.job;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sz.youban.entity.ScheduleJobLog;
import com.sz.youban.myinterface.job.ScheduleJobLogService;
import com.sz.youban.service.job.mapper.ScheduleJobLogDao;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLog> implements ScheduleJobLogService {
	@Autowired
	private ScheduleJobLogDao scheduleJobLogDao;

	@Override
	public Page<ScheduleJobLog> queryPageList(Page<ScheduleJobLog> page, Map<String, Object> map) {
		page.setRecords(scheduleJobLogDao.queryPageList(page, map));
		return page;
	}
	
	
}
