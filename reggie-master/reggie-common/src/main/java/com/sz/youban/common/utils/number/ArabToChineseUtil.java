package com.sz.youban.common.utils.number;

/**
 * 阿拉伯数字转中文工具类
 * @author Administrator
 *
 */
public class ArabToChineseUtil {

	static String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿" };
	static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int num = 245000006;
		String numStr = foematInteger(num);
		print("num= " + num + ", convert result: " + numStr);
		double decimal = 25;
		print("============================================================");
		String decStr = formatDecimal(decimal);
		print("decimal= " + decimal + ", decStr: " + decStr);
	}
	
	public static String foematInteger(int num) {
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.valueOf(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
					// not need process if the last digital bits is 0
					continue;
				} else {
					// no unit for 0
					sb.append(numArray[n]);
				}
			} else {
				sb.append(numArray[n]);
				sb.append(unit);
			}
		}
		return sb.toString();
	}

	public static String formatDecimal(double decimal) {
		String decimals = String.valueOf(decimal);
		int decIndex = decimals.indexOf(".");
		int integ = Integer.valueOf(decimals.substring(0, decIndex));
		int dec = Integer.valueOf(decimals.substring(decIndex + 1));
		String result = foematInteger(integ) + "." + formatFractionalPart(dec);
		return result;
	}

	public static String formatFractionalPart(int decimal) {
		char[] val = String.valueOf(decimal).toCharArray();
		int len = val.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int n = Integer.valueOf(val[i] + "");
			sb.append(numArray[n]);
		}
		return sb.toString();
	}

	private static void print(Object arg0) {
		System.out.println(arg0);
	}
}
