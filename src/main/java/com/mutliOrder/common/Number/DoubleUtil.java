package com.mutliOrder.common.Number;

import java.text.NumberFormat;

public class DoubleUtil {
	
	public static String getNumberFormat(double value,int MinimumIntegerDigits){
		NumberFormat mf = NumberFormat.getInstance();
		mf.setMaximumFractionDigits(MinimumIntegerDigits);
		return mf.format(value);
	}
}
