package com.sz.youban.common.utils.Sting;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.feilong.core.Validator;

/**
 * 字符串简易工具类
 * 
 * @author arron
 * @date 2016年4月8日 下午6:31:03
 * @version 1.0
 */
public class StringUtil {

	/**
	 * 通过正则表达式获取内容
	 * 
	 * @param regex
	 *            正则表达式
	 * @param from
	 *            原字符串
	 * @return
	 */
	public static String[] regex(String regex, String from) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(from);
		List<String> results = new ArrayList<String>();
		while (matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				results.add(matcher.group(i + 1));
			}
		}
		return results.toArray(new String[] {});
	}

	/**
	 * 字符串解密
	 * @param str 字符串
	 * @return String 返回加密字符串
	 */
	public static String decrypt(String str) {
		try {
			if(str==null || str==""){
				return "";
			}
			String name = new String();
			java.util.StringTokenizer st = new java.util.StringTokenizer(str, "%");
			while (st.hasMoreElements()) {
				int asc = Integer.parseInt((String) st.nextElement()) - 19;
				name = name + (char) asc;
			}

			return name;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串简单加密密
	 * @param str 字符串
	 * @return String 返回加密字符串
	 */
	public static String encrypt(String str) {
		try {
			byte[] _ssoToken = str.getBytes("ISO-8859-1");
			String name = new String();
			// char[] _ssoToken = ssoToken.toCharArray();
			for (int i = 0; i < _ssoToken.length; i++) {
				int asc = _ssoToken[i];
				_ssoToken[i] = (byte) (asc + 19);
				name = name + (asc + 19) + "%";
			}
			return name;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 判断对象或对象集合 是否有一个为空
	 * @param arr
	 * @return
	 */
	public static boolean nullOrEmpty(Object... arr){
		if(Validator.isNullOrEmpty(arr)) return true;
		for(Object obj :arr){
			if(Validator.isNullOrEmpty(obj)) return true;
		}
		return false;
	}
	
	public static String join(String... arr){
		if(Validator.isNullOrEmpty(arr)) return "";
		StringBuffer sbfBuffer = new StringBuffer("");
		for(String str :arr){
			if(Validator.isNotNullOrEmpty(str)){
				sbfBuffer.append(str);
			}
		}
		return sbfBuffer.toString();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(encrypt("360428196403090010"));
	}
	/**
	 * 获取百分号数值
	 * @param data
	 * @param newScale 保留小数位数,去掉没有用的0
	 * @return
	 */
	public static String getBaifenhaoData(BigDecimal data,int newScale){
		return getBaiBei(data,newScale)+ "%";
	}
	/**
	 * 获取指定数值的百倍
	 * @param data
	 * @param newScale
	 * @return
	 */
	public static String getBaiBei(BigDecimal data,int newScale){
		NumberFormat nf = NumberFormat.getInstance();
		return nf.format(data.multiply(new BigDecimal(100)).setScale(newScale, BigDecimal.ROUND_HALF_UP));

	}
}
