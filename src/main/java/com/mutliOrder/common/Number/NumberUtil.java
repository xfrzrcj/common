package com.mutliOrder.common.Number;

import java.util.HashSet;
import java.util.Set;

import com.mutliOrder.common.String.StringUtils;

/**
 * 有正负符号的整数和小数两种： 1、整数，标明最大的位数。 2、小数，标明p精度（位数）和s等级（小数点后位数）。 如：(3,2)表明[-9.99,
 * +9.99]。
 * 
 * @author wjg create 2017年9月16日
 */
public class NumberUtil {
	private static final char[] NUM_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static final char DOT_CHAR = '.';
	private static final Set<Character> NUM_SET = new HashSet<Character>();
	static {
		for (char num : NUM_CHAR) {
			NUM_SET.add(num);
		}
	}
	/**
	 * 判断str是否是合法的数字
	 *@author 戚鹏飞
	 * 2017年9月17日  下午5:41:44
	 * @param str
	 * @param inter 整数位个数，不能超过此值
	 * @param decimal 小数位个数，不能超过此值
	 * @return
	 */
	public static boolean isNumber(String str, int inter, int decimal) {
		if (!StringUtils.filedIsNull(str) && inter > 0 && decimal >= 0) {
			char[] chars = str.toCharArray();
			//对0开头的数据过滤
			if (chars[0] == NUM_CHAR[0] && chars.length > 1 && DOT_CHAR != chars[1]) {
				return false;
			}else {
				int big = 0;
				int small = 0;
				boolean flag = false;
				for(char c:chars) {
					if(NUM_SET.contains(c)) {
						if(flag) {
							small++;
							if(small>decimal) {
								return false;
							}
						}else {
							big++;
							if(big>inter) {
								return false;
							}
						}
						//跳转到小数位统计
					}else if(DOT_CHAR==c){
						flag = true;
					}else {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
}
