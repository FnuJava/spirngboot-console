package com.sz.youban.common.utils.debx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.feilong.core.DatePattern;
import com.feilong.core.Validator;
import com.feilong.core.date.DateExtensionUtil;
import com.feilong.core.date.DateUtil;

/**
 * <p>Title: 等额本息还款工具类</p>
 *
 */
public class CPMUtils{
	

    /** 
     * <p>Description: 每月还款总额。〔贷款本金×月利率×（1＋月利率）＾还款月数〕÷〔（1＋月利率）＾还款月数－1〕</p>
     * @param principal 贷款本金
     * @param monthlyInterestRate 月利率
     * @param amount 期数
     * @return 进一
     */
    public static BigDecimal monthlyRepayment(BigDecimal principal, BigDecimal monthlyInterestRate, int amount){
        //（1＋月利率）＾还款月数
        BigDecimal temp = monthlyInterestRate.add(MoneyUtils.ONE).pow(amount);
        return principal.multiply(monthlyInterestRate)
                        .multiply(temp)
                        .divide(temp.subtract(MoneyUtils.ONE),2, BigDecimal.ROUND_UP);
    }
    
    /**
     * <p>Description: 月还款利息。（贷款本金×月利率-月还款额）*（1+月利率)^（当前期数-1）+月还款额</p>
     * @param principal 贷款本金
     * @param monthlyInterestRate 月利率
     * @param monthlyRepayment 月还款额
     * @param number 当前期数
     * @return 进一
     */
    public static BigDecimal monthlyInterest(BigDecimal principal, BigDecimal monthlyInterestRate, BigDecimal monthlyRepayment, int number){
        //（1+月利率)^（当前期数-1）
        BigDecimal temp = monthlyInterestRate.add(MoneyUtils.ONE).pow(number - 1);
        return principal.multiply(monthlyInterestRate)
                        .subtract(monthlyRepayment)
                        .multiply(temp).add(monthlyRepayment).setScale(2,BigDecimal.ROUND_UP);
    }
    
    /**
     * <p>Description: 还款总利息。每月还款总额*期数－贷款本金 </p>
     * @param principal 贷款本金
     * @param monthlyInterestRate 月利率
     * @param months 还款期数
     * @return
     */
    public static BigDecimal interest(BigDecimal principal, BigDecimal monthlyInterestRate, int months){
        return CPMUtils.monthlyRepayment(principal,monthlyInterestRate,months).
        		multiply(new BigDecimal(months)).subtract(principal);
    }
    
    /**
     * <p>Description: 月还款本金。已经精确到分位，未做单位换算</p>
     * @param principal 贷款本金
     * @param monthlyInterestRate 月利率
     * @param monthlyRepayment 月还款额
     * @param number 当前期数
     * @return
     */
    public static BigDecimal monthlyPrincipal(BigDecimal principal, BigDecimal monthlyInterestRate, BigDecimal monthlyRepayment, int number){
        BigDecimal monthInterest = monthlyInterest(principal, monthlyInterestRate, monthlyRepayment, number);
        //月还款额-月还款利息
        return monthlyRepayment.subtract(monthInterest);
    }
    
    /**
     * <p>Description: 剩余本金计算。已经精确到分位，未做单位换算</p>
     * 贷款额*(1+月利率）^已还月数 - 每月还本付息金额*[(1+月利率)^已还月数 - 1]/月利率
     * @param principal 借款金额
     * @param monthRate 月利率
     * @param totalNums 总借款期数
     * @param dayRate 日利息
     * @param alreadyRepayNum 已还月数
     * @return 进一法
     */
    public static BigDecimal remainPrincipal(BigDecimal principal, BigDecimal monthRate, int totalNums,BigDecimal dayRate,int alreadyRepayNum){
    	BigDecimal monthInterest = monthlyRepayment(principal, monthRate, totalNums);
    	BigDecimal temp1 = monthRate.add(MoneyUtils.ONE).pow(alreadyRepayNum);
    	return principal.multiply(temp1).subtract(monthInterest.multiply(temp1.subtract(MoneyUtils.ONE)).divide(monthRate)).setScale(2, BigDecimal.ROUND_DOWN);
    }
    
    /**
     * <p>Description: 月还款本金</p>
     * @param monthRepayment 月还款总额
     * @param monthInterest 月还款利息
     * @return
     */
    public static BigDecimal monthPrincipal(BigDecimal monthRepayment, BigDecimal monthInterest){
        //月还款总额-月还款利息
        return monthRepayment.subtract(monthInterest);
    }
    /**
     * 获取N天的利息(可以计算逾期利息)  进一
     * @param principal 本金	
     * @param dayRate	日利率
     * @param dayNum 天数
     * @return
     */
    public static BigDecimal partInterest(BigDecimal principal, BigDecimal dayRate,int dayNum){
        //本金*日利率*天数
        return principal.multiply(dayRate).setScale(2, BigDecimal.ROUND_UP).multiply(new BigDecimal(dayNum));
    }
    
	/**
	 * 获取用户的还款日期数据
	 * @param months 借款期限
	 * @param repayDay 还款日 没有的话为空
	 * @return
	 */
	public static Map<String,Object> getRepaymentDay(int months,Integer repayDay,boolean firstRelease,Date releaseDay){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> comMap = getCommonData(repayDay,releaseDay);
		boolean isSip = (boolean) comMap.get("isSip");
		Integer partDay = (Integer) comMap.get("partDay");
		Date zeroDate = (Date) comMap.get("zeroDate");
		List<String> repayDate = getDayList(isSip,zeroDate,months);
		map.put("partDay",partDay);//第一期不完整天数
		map.put("repayDate",repayDate);//还款日list
		return map;
	}
	
	/**
	 * 获取起始放款时间、部分付息天数、isSip
	 * @param repayDay
	 * @return
	 */
	public static Map<String,Object> getCommonData(Integer repayDay,Date releaseDay){
		Map<String,Object> map = new HashMap<String,Object>();
		Date now = new Date();
		if(releaseDay != null){
			now = releaseDay;
		}
		Date zeroDate = new Date();//约定的当月还款日
		if(releaseDay != null){
			zeroDate = releaseDay;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(zeroDate);
		if(Validator.isNotNullOrEmpty(repayDay)&&repayDay>0){
			calendar.set(Calendar.DAY_OF_MONTH,repayDay);
			zeroDate = calendar.getTime();
		}
		boolean isSip = false;//第一期是否跳过一个月
		int nowDayMonth = 0; //当前日的天数
		int partDay = 0;//部分利息天数
		nowDayMonth = DateUtil.getDayOfMonth(now);
		partDay = repayDay -nowDayMonth;
		if(partDay >= 0){//在约定还款日之前借的款
			
		}else{
			isSip = true;
			partDay = DateExtensionUtil.getIntervalDay(DateUtil.getFirstDateOfThisDay(DateUtil.addMonth(zeroDate,1)),DateUtil.getFirstDateOfThisDay(now));//（下个月约定还款日-当前时间） 
		}
		map.put("isSip", isSip);
		map.put("partDay",partDay);
		map.put("zeroDate",zeroDate);
		return map;
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
			repayDay = getUserReapyDay(releaseDay);
		}
		
		Map<String,Object> reapyDayMap = CPMUtils.getRepaymentDay(months,repayDay,firstRelease,releaseDay);//获取还款日和第一期不完整月天数
		List<String> repayDate = (List<String>) reapyDayMap.get("repayDate");
		int partDayNum = (int) reapyDayMap.get("partDay");
		
		
		//每月本息
		BigDecimal monthlyRepayment = CPMUtils.monthlyRepayment(principal,monthRate,months);
		//总利息
		BigDecimal interestTotal = CPMUtils.interest(principal,monthRate,months);
		BigDecimal eachMonthInterest = new BigDecimal(0);//每月利息
		BigDecimal eachMonthPrincipal = new BigDecimal(0);//每月本金
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//每期还款详情
		BigDecimal interestSum = BigDecimal.ZERO;
		BigDecimal principalSum = BigDecimal.ZERO;
		BigDecimal partInterest = BigDecimal.ZERO;
		for(int i=1;i<=months;i++){
			if(i < months){
				eachMonthInterest = CPMUtils.monthlyInterest(principal,monthRate,monthlyRepayment,i);
				eachMonthPrincipal = monthlyRepayment.subtract(eachMonthInterest);
			}else{//最后一期本金和利息用总的扣除前面的 避免息差
				eachMonthInterest = interestTotal.subtract(interestSum);
				eachMonthPrincipal = principal.subtract(principalSum);
			}
			interestSum = interestSum.add(eachMonthInterest);
			principalSum = principalSum.add(eachMonthPrincipal);
			Map<String,Object> map = new HashMap<String,Object>();
			if(i == 1){ //第一期有不完整月处理
				partInterest = CPMUtils.partInterest(principal,dayRate, partDayNum);//不完整月利息
				map.put("partDay",partDayNum);
				map.put("partInterest",partInterest);
				map.put("eachMonthInterest",eachMonthInterest.add(partInterest));
				map.put("benxi",monthlyRepayment.add(partInterest));
			}else{
				map.put("partDay",0);
				map.put("partInterest",BigDecimal.ZERO);
				map.put("eachMonthInterest",eachMonthInterest);
				map.put("benxi",monthlyRepayment);
			}
			map.put("eachMonthPrincipal",eachMonthPrincipal);
			map.put("repayDate",repayDate.get(i-1));
			map.put("periodNum",i);
			list.add(map);
		}
		interestTotal = interestTotal.add(partInterest);
		result.put("repayList", list);
		result.put("repayDay", repayDay);
		result.put("interestTotal",interestTotal);
		result.put("totalPay",interestTotal.add(principal));
		return result;
	}
	/**
	 * 计算用户的还款日 大于28为1号
	 * @return
	 */
	public static int getUserReapyDay(Date releaseDay){
		int dayMonth = 0;//还款日的天数
		Date zeroDate = new Date();;
		if(releaseDay != null){
			zeroDate = releaseDay;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(zeroDate);
		dayMonth = DateUtil.getDayOfMonth(zeroDate);
		if(dayMonth>28){//大于28号还款日定为1号
			dayMonth = 1;
		}
		return dayMonth;
	}
	
	public static List<String> getDayList(boolean isSip,Date zeroDate,int months){
		List<String> repayDate = new ArrayList<String>();
		for(int i=1;i<=months;i++){
			Date dateTemp = new Date();
			if(isSip){
				dateTemp = DateUtil.addMonth(zeroDate,1*i+1);
			}else{
				dateTemp = DateUtil.addMonth(zeroDate,1*i);
			}
			repayDate.add(DateUtil.toString(dateTemp, DatePattern.COMMON_DATE));
		}
		return repayDate;
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
		Map<String,Object> comMap = getCommonData(repayDay,null);
		Integer partDay = (Integer) comMap.get("partDay");
		return  interest(principal, monthRate, months).add(partInterest(principal, dayRate, partDay));
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
	
	/**
	 * 获取代理的不完整收益
	 * @param amount 本金
	 * @param dayNum  天数
	 * @param earningLevel 收益系数
	 * @return 收益= 本金*收益系数*天数/365                         （截取两位小数）
	 */
	public static BigDecimal getIncomePart(BigDecimal amount,int dayNum,BigDecimal earningLevel){
		return amount.multiply(earningLevel).multiply(new BigDecimal(dayNum)).divide(new BigDecimal(365),2,BigDecimal.ROUND_DOWN);
	}
	
	
	/**
	 * 获取代理的整月收益
	 * @param amount 本金
	 * @param dayNum  天数
	 * @param earningLevel 收益系数
	 * @return 收益= 本金*收益系数*月份/12                       （截取两位小数）
	 */
	public static BigDecimal getIncomeMonth(BigDecimal amount,int month,BigDecimal earningLevel){
		return amount.multiply(earningLevel).divide(new BigDecimal(12),2,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(month));
	}
	
	/**
	 * 获取总收益
	 * @param amount
	 * @param earningLevel
	 * @param month
	 * @param dayNum
	 * @return
	 */
	public static BigDecimal getIncomeTotal(BigDecimal amount,BigDecimal earningLevel,int month,int dayNum){
		
		return getIncomeMonth(amount, month, earningLevel).add(getIncomePart(amount, dayNum, earningLevel));
	}
	
	
	

	
	/**
	 * 获取年利息
	 * @param rate 利息
	 * @param type 利息类型
	 * @return
	 */
	public static BigDecimal getYearRate(BigDecimal rate,int type){
		if(rate == null) return null;
		if(MoneyUtils.DAY == type){
			return rate.multiply(new BigDecimal(365).setScale(6, BigDecimal.ROUND_UP));
		}else{
			return rate.multiply(new BigDecimal(12).setScale(6, BigDecimal.ROUND_UP));
		}
	}
	/**
	 * 获取月利息
	 * @param rate 利息
	 * @param type 利息类型
	 * @return
	 */
	public static BigDecimal getMonthRate(BigDecimal rate,int type){
		if(rate == null) return null;
		if(MoneyUtils.DAY == type){
			return getYearRate(rate,MoneyUtils.DAY).divide(new BigDecimal(12),4, BigDecimal.ROUND_UP);
		}else{
			return rate.divide(new BigDecimal(12),4, BigDecimal.ROUND_UP);
		}
	}
	/**
	 * 获取天利息
	 * @param rate 利息
	 * @param type 利息类型
	 * @return
	 */
	public static BigDecimal getDayRate(BigDecimal rate,int type){
		if(rate == null) return null;
		if(MoneyUtils.YEAR == type){
			return rate.divide(new BigDecimal(365),6, BigDecimal.ROUND_UP);
		}else{
			return getYearRate(rate,MoneyUtils.MONTH).divide(new BigDecimal(365),6, BigDecimal.ROUND_UP);
		}
	}
	
	/**
	 * 除以100
	 * @param rate
	 * @return
	 */
	public static BigDecimal divide100(BigDecimal rate){
		if(rate==null)return null;
		return rate.divide(new BigDecimal(100),6, BigDecimal.ROUND_UP);
	}
	
	/**
	 * 乘100
	 * @param rate
	 * @return
	 */
	public static BigDecimal mulity100(BigDecimal rate){
		if(rate==null)return null;
		return rate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP);
	}
	/**
	 * 查询借款总天数 月份+还款天数计算的部分天数
	 * @return
	 */
	public static int getLoanDay(Integer repayDay,Date releaseDay,int months){
		
		boolean firstRelease = false;
		if(Validator.isNullOrEmpty(repayDay) || repayDay==0){//第一次放款
			firstRelease = true;
			repayDay = getUserReapyDay(releaseDay);
		}
		Map<String,Object> reapyDayMap = CPMUtils.getRepaymentDay(months,repayDay,firstRelease,releaseDay);//获取还款日和第一期不完整月天数
		List<String> repayDate = (List<String>) reapyDayMap.get("repayDate");
		String loanRepayDate = repayDate.get(repayDate.size()-1);
		Date loanRepayTime = DateUtil.toDate(loanRepayDate, DatePattern.COMMON_DATE);
		return DateExtensionUtil.getIntervalDay(DateUtil.getFirstDateOfThisDay(releaseDay), DateUtil.getFirstDateOfThisDay(loanRepayTime));
	}
	
	public static void main(String[] args) {
		Date now = new Date();
		System.out.println(DateExtensionUtil.getIntervalDay(new Date(),new Date()));
	}
}