package com.sz.youban.common.utils.debx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feilong.core.DatePattern;
import com.feilong.core.Validator;
import com.feilong.core.date.DateUtil;

/**
 * 到期本息工具类
 * @author Administrator
 *
 */
public class DqbxUtils {
	
	/**
	 * 获取用户的还款日期数据
	 * @param months 借款期限
	 * @param repayDay 还款日 没有的话为空
	 * @return
	 */
	public static Map<String,Object> getRepaymentDay(int months,Integer repayDay,boolean firstRelease,Date releaseDay){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> comMap = CPMUtils.getCommonData(repayDay,releaseDay);
		boolean isSip = (boolean) comMap.get("isSip");
		Integer partDay = (Integer) comMap.get("partDay");
		Date zeroDate = (Date) comMap.get("zeroDate");
		List<String> repayDate = getDayList(isSip,zeroDate,months);
		map.put("partDay",partDay);//第一期不完整天数
		map.put("repayDate",repayDate);//还款日list
		return map;
	}
	
	public static List<String> getDayList(boolean isSip,Date zeroDate,int months){
		List<String> repayDate = new ArrayList<String>();
		Date dateTemp = new Date();
		if(isSip){
			dateTemp = DateUtil.addMonth(zeroDate,months+1);
		}else{
			dateTemp = DateUtil.addMonth(zeroDate,months);
		}
		repayDate.add(DateUtil.toString(dateTemp, DatePattern.COMMON_DATE));
		return repayDate;
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
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getRepaymentList(BigDecimal principal,BigDecimal yearRate,BigDecimal monthRate,
			BigDecimal dayRate,int months,Integer repayDay,Date releaseDay){
		Map<String,Object> result = new HashMap<String,Object>();
		boolean firstRelease = false;
		if(Validator.isNullOrEmpty(repayDay) || repayDay==0){//第一次放款
			firstRelease = true;
			repayDay = CPMUtils.getUserReapyDay(releaseDay);
		}
		
		Map<String,Object> reapyDayMap = getRepaymentDay(months,repayDay,firstRelease,releaseDay);//获取还款日和第一期不完整月天数
		List<String> repayDate = (List<String>)reapyDayMap.get("repayDate");
		int partDayNum = (int) reapyDayMap.get("partDay");
		//总利息
		BigDecimal monthlyRepayment = AyfxUtils.interest(principal,monthRate,months);
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//每期还款详情
		BigDecimal partInterest = BigDecimal.ZERO;//部分利息
		Map<String,Object> map = new HashMap<String,Object>();
		partInterest = CPMUtils.partInterest(principal,dayRate,partDayNum);//不完整月利息
		monthlyRepayment = monthlyRepayment.add(partInterest);
		map.put("partDay",partDayNum);
		map.put("partInterest",partInterest);
		map.put("eachMonthInterest",monthlyRepayment);
		map.put("benxi",monthlyRepayment.add(principal));
		map.put("eachMonthPrincipal",principal);
		map.put("repayDate",repayDate.get(0));
		map.put("periodNum",1);
		list.add(map);
		result.put("repayList", list);
		result.put("repayDay", repayDay);
		result.put("interestTotal",monthlyRepayment);
		result.put("totalPay",monthlyRepayment.add(principal));
		return result;
	
	}

    
    /**
     * 获取到期本息的还款时间
     * @param repayDay
     * @param months
     * @return
     */
    public static String getRepayDay(int repayDay,int months){
    	Map<String,Object> comMap = CPMUtils.getCommonData(repayDay,null);
    	boolean isSip = (boolean) comMap.get("isSip");
    	Date zeroDate = (Date) comMap.get("zeroDate");
    	Date dateTemp = new Date();
		if(isSip){
			months = months+1;
		}
		dateTemp = DateUtil.addMonth(zeroDate,months);
		return DateUtil.toString(dateTemp, DatePattern.CHINESE_COMMON_DATE);
    }

}
