package com.sz.youban.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 定时任务日志
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@TableName("schedule_job_log")
public class ScheduleJobLog extends Model<ScheduleJobLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 任务日志id
     */
	@TableId(value="log_id", type= IdType.AUTO)
	private Long logId;
    /**
     * 任务id
     */
	@TableField("job_id")
	private Long jobId;
    /**
     * spring bean名称
     */
	@TableField("bean_name")
	private String beanName;
    /**
     * 方法名
     */
	@TableField("method_name")
	private String methodName;
    /**
     * 参数
     */
	private String params;
    /**
     * 任务状态    0：成功    1：失败
     */
	private Integer status;
    /**
     * 失败信息
     */
	private String error;
    /**
     * 耗时(单位：毫秒)
     */
	private Integer times;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;


	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.logId;
	}

	@Override
	public String toString() {
		return "ScheduleJobLog{" +
			", logId=" + logId +
			", jobId=" + jobId +
			", beanName=" + beanName +
			", methodName=" + methodName +
			", params=" + params +
			", status=" + status +
			", error=" + error +
			", times=" + times +
			", createTime=" + createTime +
			"}";
	}
}
