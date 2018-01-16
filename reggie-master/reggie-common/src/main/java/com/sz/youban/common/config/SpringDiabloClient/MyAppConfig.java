package com.sz.youban.common.config.SpringDiabloClient;


import org.springframework.stereotype.Component;

/**
 * 配置的属性
 * @author Administrator
 *
 */
@Component
public class MyAppConfig implements DiabloConfig {

    private String activityNo;

    private Integer activityChannel;

    private Boolean activityStart;

    private Float activityRatio;

    private Long activityCount;

    private Double activityFee;
    
    private String test_config1;

	public String getActivityNo() {
		return activityNo;
	}

	public void setActivityNo(String activityNo) {
		this.activityNo = activityNo;
	}

	public Integer getActivityChannel() {
		return activityChannel;
	}

	public void setActivityChannel(Integer activityChannel) {
		this.activityChannel = activityChannel;
	}

	public Boolean getActivityStart() {
		return activityStart;
	}

	public void setActivityStart(Boolean activityStart) {
		this.activityStart = activityStart;
	}

	public Float getActivityRatio() {
		return activityRatio;
	}

	public void setActivityRatio(Float activityRatio) {
		this.activityRatio = activityRatio;
	}

	public Long getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(Long activityCount) {
		this.activityCount = activityCount;
	}

	public Double getActivityFee() {
		return activityFee;
	}

	public void setActivityFee(Double activityFee) {
		this.activityFee = activityFee;
	}

	public String getTest_config1() {
		return test_config1;
	}

	public void setTest_config1(String test_config1) {
		this.test_config1 = test_config1;
	}

}
