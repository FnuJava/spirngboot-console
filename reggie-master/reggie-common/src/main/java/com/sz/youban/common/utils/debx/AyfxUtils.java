package com.sz.youban.common.utils.debx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feilong.core.Validator;

/**
 * 按月付息工具类
 * @author Administrator
 *
 */
public class AyfxUtils {
	
	public static void main(String args[]){
		//System.out.println(ReleaseUtil.interest(new BigDecimal(10000), new BigDecimal(0.12), 3));
		
		BigDecimal bigDecimal = new BigDecimal("1.565");//1.565
		
		System.out.println(bigDecimal.setScale(2,BigDecimal.ROUND_UP));//进一
		System.out.println(bigDecimal.setScale(2,BigDecimal.ROUND_DOWN));//截取
		System.out.println(bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP));//四舍五入
		
	}

    /**
     * 每月利息： 贷款本金×月利率  四舍五入
     * @param principal
     * @param yearInterestRate
     * @param month
     * @return
     */
    public static BigDecimal monthlyInterest(BigDecimal principal, BigDecimal monthInterestRate){
    	 return principal.multiply(monthInterestRate).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * <p>Description: 还款总利息。（贷款本金×月利率）*期数 </p> 先四舍五入*期数
     * @param principal 贷款本金
     * @param monthInterestRate 月利率
     * @param month 还款期数
     * @return
     */
    public static BigDecimal interest(BigDecimal principal, BigDecimal monthInterestRate, int month){
    	
        return monthlyInterest(principal,monthInterestRate).multiply(new BigDecimal(month));
    }
    
    /**
	 * 获取总利息（周期利息+不完整天数利息）
	 * @param principal
	 * @param yearRate
	 * @param monthRate
	 * @param dayRate
	 * @param months
	 * @param repayDay
	 * @return
	 */
	public static BigDecimal getTotalInterest(BigDecimal principal,BigDecimal yearRate,BigDecimal monthRate,
			BigDecimal dayRate,int months,Integer repayDay){
		Map<String,Object> comMap = CPMUtils.getCommonData(repayDay,null);
		Integer partDay = (Integer) comMap.get("partDay");
		return  interest(principal, monthRate, months).add(CPMUtils.partInterest(principal, dayRate, partDay));
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
		return  principal.add(getTotalInterest(principal, yearRate, monthRate, dayRate, months, repayDay));
	}
	
	/**获取用户每期的还款详情 和 系统还款日
	 * @param principal 本金
	 * @param yearRate 年利率
	 * @param monthRate 月利率
	 * @param dayRate 日利率
	 * @param months 期限
	 * @param repayDay 系统还款日（可为空,会自动生成）
	 * @return
	 */
	public static Map<String,Object> getRepaymentList(BigDecimal principal,BigDecimal yearRate,BigDecimal monthRate,
			BigDecimal dayRate,int months,Integer repayDay,Date releaseDay){
		
		Map<String,Object> result = new HashMap<String,Object>();
		boolean firstRelease = false;
		if(Validator.isNullOrEmpty(repayDay) || repayDay==0){//第一次放款
			firstRelease = true;
			repayDay = CPMUtils.getUserReapyDay(releaseDay);
		}
		
		Map<String,Object> reapyDayMap = CPMUtils.getRepaymentDay(months,repayDay,firstRelease,releaseDay);//获取还款日和第一期不完整月天数
		@SuppressWarnings("unchecked")
		List<String> repayDate = (List<String>) reapyDayMap.get("repayDate");
		int partDayNum = (int) reapyDayMap.get("partDay");
		
		//每月利息
		BigDecimal monthlyInterest = AyfxUtils.monthlyInterest(principal, monthRate);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//每期还款详情
		BigDecimal eachPrincipal = BigDecimal.ZERO;
		BigDecimal partInterest = BigDecimal.ZERO;//部分利息
		BigDecimal monthlyRepayment = BigDecimal.ZERO;//每月利息
		for(int i=1;i<=months;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			if(i == months){ //最后一期本金
				eachPrincipal = principal;
			}
			if(i==1){
				partInterest = CPMUtils.partInterest(principal,dayRate,partDayNum);//不完整月利息
				monthlyRepayment = monthlyInterest.add(partInterest);
				map.put("partInterest",partInterest);
			}else{
				monthlyRepayment = monthlyInterest;
				map.put("partInterest",BigDecimal.ZERO);
			}
			
			map.put("partDay",partDayNum);
			map.put("partInterest",partInterest);
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
		result.put("totalPay",interestTotal.add(principal));
		return result;
	}
	

}
