package com.sz.youban.common.utils.debx;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MoneyUtils {

	/**
	 * 标度（小数位数）
	 */
	public static final int SCALE = 10;

	/**
	 * 金钱显示标度（小数位数）
	 */
	public static final int MONEYSHOWSCALE = 2;

	/**
	 * 利率显示标度（小数位数）
	 */
	public static final int INTERESTRATESHOWSCALE = 4;

	/**
	 * 精度
	 */
	public static final int PRECISION = 30;

	/**
	 * 保存舍入规则
	 */
	public static final RoundingMode SAVEROUNDINGMODE = RoundingMode.HALF_UP;

	/**
	 * 是否舍去小数点最后的零
	 */

	public static boolean STRIPTRAILINGZEROS = true;

	/**
	 * 运算上下文（设置精度、舍入规则）
	 */

	public static final MathContext MATHCONTEXT = new MathContext(PRECISION, SAVEROUNDINGMODE);

	/**
	 * 每年天数
	 */

	public static final String YEARDAYS = "360";

	/**
	 * 每年月数
	 */

	public static final String YEARMOTHS = "12";

	/**
	 * 每月天数
	 */

	public static final String MOTHDAYS = "30";

	/**
	 * 数字“1”
	 */

	public static final BigDecimal ONE = new BigDecimal(1);

	/**
	 * 数字“100”
	 */

	public static final BigDecimal HUNDRED = new BigDecimal(100);

	/**
	 * 数字“0.01”
	 */

	public static final BigDecimal ONEHUNDREDTH = new BigDecimal(0.01);

	
	public static final int DAY=1;
	public static final int MONTH=2;
	public static final int YEAR=3;
	
	public static BigDecimal newBigDecimal(String str) {
		return (str == null || str.trim().isEmpty()) ? BigDecimal.ZERO : new BigDecimal(str);
	}

	/**
	 * <p>
	 * Description: 加法返回格式化结果数字
	 * </p>
	 * 
	 * @param addend
	 * 			@param augend @return
	 */

	public static BigDecimal add(BigDecimal addend, BigDecimal augend) {
		return formatMoney(addend.add(augend, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 加法返回格式化结果数字
	 * </p>
	 * 
	 * @param addend
	 * 			@param augend @return
	 */

	public static BigDecimal add(String addend, String augend) {
		BigDecimal decimalAddend = newBigDecimal(addend);
		BigDecimal decimalAugend = newBigDecimal(augend);
		return formatMoney(decimalAddend.add(decimalAugend, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 加法返回格式化结果字符串
	 * </p>
	 * 
	 * @param addend
	 * 			@param augend @return
	 */

	public static String addToString(BigDecimal addend, BigDecimal augend) {
		return formatToString(addend.add(augend, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 加法返回格式化结果字符串
	 * </p>
	 * 
	 * @param addend
	 * 			@param augend @return
	 */

	public static String addToString(String addend, String augend) {
		BigDecimal decimalAddend = newBigDecimal(addend);
		BigDecimal decimalAugend = newBigDecimal(augend);
		return formatToString(decimalAddend.add(decimalAugend, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 减法返回格式化结果数字
	 * </p>
	 * 
	 * @param minuend
	 * 			@param subtrahend @return
	 */

	public static BigDecimal subtract(BigDecimal minuend, BigDecimal subtrahend) {
		return formatMoney(minuend.subtract(subtrahend, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 减法返回格式化结果数字
	 * </p>
	 * 
	 * @param minuend
	 * 			@param subtrahend @return
	 */

	public static BigDecimal subtract(String minuend, String subtrahend) {
		BigDecimal decimalMinuend = newBigDecimal(minuend);
		BigDecimal decimalSubtrahend = newBigDecimal(subtrahend);
		return formatMoney(decimalMinuend.subtract(decimalSubtrahend, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 减法返回格式化结果字符串
	 * </p>
	 * 
	 * @param minuend
	 * 			@param subtrahend @return
	 */

	public static String subtractToString(BigDecimal minuend, BigDecimal subtrahend) {
		return formatToString(minuend.subtract(subtrahend, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 减法返回格式化结果字符串
	 * </p>
	 * 
	 * @param minuend
	 * 			@param subtrahend @return
	 */

	public static String subtractToString(String minuend, String subtrahend) {
		BigDecimal decimalMinuend = newBigDecimal(minuend);
		BigDecimal decimalSubtrahend = newBigDecimal(subtrahend);
		return formatToString(decimalMinuend.subtract(decimalSubtrahend, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 乘法返回格式化结果数字
	 * </p>
	 * 
	 * @param multiplier
	 * 			@param multiplicand @return
	 */

	public static BigDecimal multiply(BigDecimal multiplier, BigDecimal multiplicand) {
		return formatMoney(multiplier.multiply(multiplicand, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 乘法返回格式化结果数字
	 * </p>
	 * 
	 * @param multiplier
	 * 			@param multiplicand @return
	 */

	public static BigDecimal multiply(String multiplier, String multiplicand) {
		BigDecimal decimalMultiplier = newBigDecimal(multiplier);
		BigDecimal decimalMultiplicand = newBigDecimal(multiplicand);
		return formatMoney(decimalMultiplier.multiply(decimalMultiplicand, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 乘法返回格式化结果字符串
	 * </p>
	 * 
	 * @param multiplier
	 * 			@param multiplicand @return
	 */

	public static String multiplyToString(BigDecimal multiplier, BigDecimal multiplicand) {
		return formatToString(multiplier.multiply(multiplicand, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 乘法返回格式化结果字符串
	 * </p>
	 * 
	 * @param multiplier
	 * 			@param multiplicand @return
	 */

	public static String multiplyToString(String multiplier, String multiplicand) {
		BigDecimal decimalMultiplier = newBigDecimal(multiplier);
		BigDecimal decimalMultiplicand = newBigDecimal(multiplicand);
		return formatToString(decimalMultiplier.multiply(decimalMultiplicand, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 除法返回格式化结果数字
	 * </p>
	 * 
	 * @param dividend
	 * 			@param divisor @return
	 */

	public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
		return formatMoney(dividend.divide(divisor, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 除法返回格式化结果数字
	 * </p>
	 * 
	 * @param dividend
	 * 			@param divisor @return
	 */

	public static BigDecimal divide(String dividend, String divisor) {
		BigDecimal decimalDividend = newBigDecimal(dividend);
		BigDecimal decimalDivisor = newBigDecimal(divisor);
		return formatMoney(decimalDividend.divide(decimalDivisor, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 除法返回格式化结果字符串
	 * </p>
	 * 
	 * @param dividend
	 * 			@param divisor @return
	 */

	public static String divideToString(BigDecimal dividend, BigDecimal divisor) {
		return formatToString(dividend.divide(divisor, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 除法返回格式化结果字符串
	 * </p>
	 * 
	 * @param dividend
	 * 			@param divisor @return
	 */

	public static String divideToString(String dividend, String divisor) {
		BigDecimal decimalDividend = newBigDecimal(dividend);
		BigDecimal decimalDivisor = newBigDecimal(divisor);
		return formatToString(decimalDividend.divide(decimalDivisor, MATHCONTEXT));
	}

	/**
	 * <p>
	 * Description: 月利率计算
	 * </p>
	 * 
	 * @param yearInterestRate
	 * 			
	 * @return
	 */

	public static BigDecimal monthInterestRate(BigDecimal yearInterestRate) {
		BigDecimal dayInterestRate = MoneyUtils.divide(yearInterestRate,newBigDecimal(YEARDAYS)).setScale(5, RoundingMode.CEILING);
		System.err.println(dayInterestRate);
		BigDecimal monthInterestRate = dayInterestRate.multiply(newBigDecimal(MOTHDAYS));
		System.err.println(monthInterestRate);
		return monthInterestRate;
	}

	/**
	 * <p>
	 * Description: 按既定小数位数格式化金额保存
	 * </p>
	 * 
	 * @param result
	 * 			@return
	 */

	public static BigDecimal formatMoney(BigDecimal result) {
		return result.setScale(SCALE, SAVEROUNDINGMODE);
	}

	/**
	 * <p>
	 * Description: 按既定小数位数格式化金额显示
	 * </p>
	 * 
	 * @param resultStr
	 *            要格式化的数 @param multiple 乘以的倍数 @return
	 */

	public static String formatMoneyToShow(String resultStr, BigDecimal multiple) {
		BigDecimal result = newBigDecimal(resultStr);
		return MoneyUtils.formatToString(MoneyUtils.formatMoneyToShow(result, multiple));
	}

	/**
	 * <p>
	 * Description: 按既定小数位数格式化金额显示
	 * </p>
	 * 
	 * @param result
	 *            要格式化的数 @param multiple 乘以的倍数 @return
	 */

	public static BigDecimal formatMoneyToShow(BigDecimal result, BigDecimal multiple) {
		return result.multiply(multiple).setScale(MONEYSHOWSCALE, SAVEROUNDINGMODE);
	}

	/**
	 * <p>
	 * Description: 按既定小数位数格式化利率显示
	 * </p>
	 * 
	 * @param result
	 *            要格式化的数 @param multiple 乘以的倍数 @return
	 */

	public static BigDecimal formatInterestRateToShow(BigDecimal result, BigDecimal multiple) {
		return result.multiply(multiple).setScale(INTERESTRATESHOWSCALE, SAVEROUNDINGMODE);
	}

	/**
	 * <p>
	 * Description: 按既定小数位数格式化显示
	 * </p>
	 * 
	 * @param result
	 *            要格式化的数 @param scale 显示标度（小数位数） @return
	 */

	public static BigDecimal formatToShow(BigDecimal result, int scale) {
		return result.setScale(scale, SAVEROUNDINGMODE);
	}

	/**
	 * <p>
	 * Description: 格式化为字符串，进行去零不去零操作
	 * </p>
	 * 
	 * @param result
	 * 			@return
	 */

	public static String formatToString(BigDecimal result) {
		if (result == null) {
			return "";
		} else {
			return STRIPTRAILINGZEROS ? result.stripTrailingZeros().toPlainString() : result.toPlainString();
		}
	}

	/**
	 * <p>
	 * Description: 按既定小数位数格式化为货币格式
	 * </p>
	 * 
	 * @param result
	 * 			@return
	 */

	public static String formatToCurrency(BigDecimal result) {
		BigDecimal temp = result.divide(HUNDRED, SAVEROUNDINGMODE);
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		return numberFormat.format(STRIPTRAILINGZEROS ? temp.stripTrailingZeros() : temp);
	}

	public static String formatToPercent(BigDecimal result) {
		BigDecimal temp = result.divide(HUNDRED, SAVEROUNDINGMODE);
		NumberFormat numberFormat = NumberFormat.getPercentInstance();
		return numberFormat.format(STRIPTRAILINGZEROS ? temp.stripTrailingZeros() : temp);
	}

	/**
	 * <p>
	 * Description:格式化数字为千分位显示；
	 * </p>
	 * 
	 * @param text
	 * 			@return
	 */
	public static String fmtMicrometer(String text) {
		DecimalFormat df = null;
		if (text.indexOf(".") > 0) {
			if (text.length() - text.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (text.length() - text.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0.00");
		}
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			number = 0.0;
		}
		return df.format(number);
	}

}