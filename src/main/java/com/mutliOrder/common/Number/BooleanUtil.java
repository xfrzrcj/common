/**
 * 
 */
package com.mutliOrder.common.Number;


/**
 * 代表了一个两价逻辑值（或者假）。
 * 用0，代表假或否；用1，代表真或是
 * @author wjg
 * create 2017年9月17日
 */
public class BooleanUtil {
	
	public static boolean checkBoolean(String str) {
		if ("1".equals(str)||"0".equals(str)){
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String s = "是";
		
		System.out.println(checkBoolean(s));
	}
}
