package com.sz.youban.common.utils.debx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feilong.core.Validator;
import com.sz.youban.common.utils.Sting.StringUtil;

/**
 * 等本等息工具类
 * @author Administrator
 *
 */
public class DbdxUtils {

	
	/**获取用户每期的还款详情 和 系统还款日
	 * @param principal 本金
	 * @param yearRate 年利率
	 * @param monthRate 月利率
	 * @param dayRate 日利率
	 * @param months 期限
	 * @param repayDay 系统还款日（可为空,会自动生成）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getRepaymentList(BigDecimal principal,BigDecimal yearRate,BigDecimal monthRate,
			BigDecimal dayRate,int months,Integer repayDay,Date releaseDay){
		
		Map<String,Object> result = new HashMap<String,Object>();
		boolean firstRelease = false;
		if(Validator.isNullOrEmpty(repayDay) || repayDay==0){//第一次放款
			firstRelease = true;
			repayDay = CPMUtils.getUserReapyDay(releaseDay);
		}
		
		Map<String,Object> reapyDayMap = CPMUtils.getRepaymentDay(months,repayDay,firstRelease,releaseDay);//获取还款日和第一期不完整月天数
		List<String> repayDate = (List<String>)reapyDayMap.get("repayDate");
		int partDayNum = (int) reapyDayMap.get("partDay");
		//每月利息
		BigDecimal monthlyInterest = AyfxUtils.monthlyInterest(principal, monthRate);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//每期还款详情
		BigDecimal partInterest = BigDecimal.ZERO;//部分利息
		BigDecimal monthlyRepayment = BigDecimal.ZERO;//利息
		BigDecimal eachPrincipal = getEachPrincipal(principal,months);//本金进一
		for(int i=1;i<=months;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			if(i==1){
				partInterest = CPMUtils.partInterest(principal,dayRate,partDayNum);//不完整月利息
				monthlyRepayment = monthlyInterest.add(partInterest);
				map.put("partInterest",partInterest);
			}else{
				monthlyRepayment = monthlyInterest;
				map.put("partInterest",BigDecimal.ZERO);
			}
			map.put("partDay",partDayNum);
			map.put("eachMonthInterest",monthlyRepayment);
			map.put("benxi",monthlyRepayment.add(eachPrincipal));
			map.put("eachMonthPrincipal",eachPrincipal);
			map.put("repayDate",repayDate.get(i-1));
			map.put("periodNum",i);
			list.add(map);
		}
		BigDecimal interestTotal = AyfxUtils.interest(principal,monthRate,months).add(partInterest);
		result.put("repayList", list);
		result.put("repayDay", repayDay);
		result.put("interestTotal",interestTotal);
		result.put("totalPay",interestTotal.add(getPrincipalTotal(principal, months)));
		return result;
	}
	
	/**
	 * 获取总本息（周期利息+不完整天数利息）
	 * @param principal
	 * @param yearRate
	 * @param monthRate
	 * @param dayRate
	 * @param months
	 * @param repayDay
	 * @return
	 */
	public static BigDecimal getTotalPay(BigDecimal principal,BigDecimal yearRate,BigDecimal monthRate,
			BigDecimal dayRate,int months,Integer repayDay){
		return  getPrincipalTotal(principal,months).add(AyfxUtils.getTotalInterest(principal, yearRate, monthRate, dayRate, months, repayDay));
	}

    
    /**
     * 获取每期的本金
     * @param principal
     * @param months
     * @return
     */
    public static BigDecimal getEachPrincipal(BigDecimal principal,int months){
    	BigDecimal eachPrincipal = principal.divide(new BigDecimal(months+""),2, BigDecimal.ROUND_UP);//本金进一
    	return eachPrincipal;
    }
    
    /**
     * 获取总的本金
     * @param principal
     * @param months
     * @return
     */
    public static BigDecimal getPrincipalTotal(BigDecimal principal,int months){
    	BigDecimal principalTotal = getEachPrincipal(principal,months).multiply(new BigDecimal(months));
    	return principalTotal;
    }
    
    
    
    public static void  main(String args[]){
		System.out.println(StringUtil.decrypt("70%72%67%72%69%72%68%76%67%67%74%69%68%74%74%72%72%72%"));;
	}
}
