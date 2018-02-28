/**
 * 
 */
package com.mutliOrder.common.Time;

import java.text.SimpleDateFormat;

/**
 * 泛指日期（Date）和时间（Time），根据使用的情况可以仅包含日期部分，也可能包含小时、分钟等部分；格式为：年月日时分秒，YYYYMMDDHHMMSS，中间不用连接符号。
 * @author wjg
 * create 2017年9月17日
 */
public class DateUtils {
	 
	/**
	 * 验证yyyy-MM-dd格式日期
	 * @param 字符串类型的日期
	 * @return 字符串类型的日期是否符合要求
	 */
	public static boolean checkDate1(String str) {
		SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
		try {
			YYYY_MM_DD.setLenient(false);
			YYYY_MM_DD.parse(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 验证yyyy-MM-dd HH:mm:ss格式信息
	 * @param 字符串类型日期
	 * @return 字符串类型的日期是否符合要求
	 */
	public static boolean checkDate2(String str) {
		SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			YYYY_MM_DD_HH_MM_SS.setLenient(false);
			YYYY_MM_DD_HH_MM_SS.parse(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/*public static void main(String[] args) {
		String s = "2016-2-30";
		System.out.println(checkDate1(s));
		System.out.println(checkDate2(s));
	}*/

}
