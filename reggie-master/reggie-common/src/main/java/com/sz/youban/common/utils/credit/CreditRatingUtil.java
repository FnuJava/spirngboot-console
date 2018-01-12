package com.sz.youban.common.utils.credit;

import java.util.HashMap;
import java.util.Map;

/**
 * 代理层级关系工具类
 * @author Administrator
 *
 */
public class CreditRatingUtil {
	public static enum CreditRating {
		AAA(95,"特优信用农户"), AA(85,"优秀信用农户"), A(70,"较好信用农户"), B(60,"一般信用农户"), NO(-1000,"不得授信农户");//-1000到60分为不得授信农户
	    private int value;
	    private String desc;
	    private CreditRating(int value, String desc) {
	        this.value = value;
	        this.desc = desc;
	    }
	    public int value() {
	        return value;
	    }
	    public String desc() {
	    	return desc;
	    }
	}
	
	public static String getRatingName(int score){
		for(CreditRating enu :CreditRating.values()){
			if(score >= enu.value()){
				return enu.name();
			}
		}
		return null;
	}
	public static String getRatingDesc(int score){
		for(CreditRating enu :CreditRating.values()){
			if(score >= enu.value()){
				return enu.desc();
			}
		}
		return null;
	}
	
	public static Integer getRatingValue(String rating){
		for(CreditRating enu :CreditRating.values()){
			if(enu.name().equals(rating)){
				return enu.value();
			}
		}
		return null;
	}
	
	/**
	 * 获取指定层级的最大值(不包含) 最小值（包含）
	 * @param name
	 * @return
	 */
	public static Map<String,Integer> getPeriphery(String name){
		Map<String,Integer> map = new HashMap<String,Integer>();
		int max = 	1000;
		int min = 	-1000;
		boolean isMatch = false;
		for(CreditRating enu :CreditRating.values()){
			if(enu.name().equals(name)){
				isMatch = true;
				min = enu.value();
			}else{
				max = enu.value;
			}
			if(isMatch) break;
		}
		if(isMatch){
			map.put("max", max);
			map.put("min", min);
		}
		
		return map;
	}
	
	public static void main(String[] args) {
//		for(CreditRating enu :CreditRating.values()){
//			System.out.println(getRatingValue(enu.name()));
//			System.out.println(getRatingName(enu.value()));
//		}
		System.out.println(getPeriphery("NO").toString());
	}
}
