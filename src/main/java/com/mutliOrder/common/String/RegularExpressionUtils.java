package com.mutliOrder.common.String;

import java.util.regex.Pattern;

public class RegularExpressionUtils {
	public static final Pattern CAR_PATTERN = Pattern.compile("^[冀豫云辽黑湘皖鲁苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼渝京津沪新京军空海北沈兰济南广成使领][a-zA-Z](([DF](?![a-zA-Z0-9]*[IO])[0-9]{4})|([0-9]{5}[DF]))$|^[冀豫云辽黑湘皖鲁苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼渝京津沪新京军空海北沈兰济南广成使领A-Z]{1}[a-zA-Z0-9]{5}[a-zA-Z0-9挂学警港澳]{1}$"); 
	public static boolean isCarNumber(String carNo) {
		return CAR_PATTERN.matcher(carNo).matches();
	}
	
	public static void main(String[] args) {
		System.out.println(isCarNumber("川A8Q876"));;
	}
}
