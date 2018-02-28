package com.mutliOrder.common.Object;

public class ObjectUtils {
	public static int AreNotNull(Object... objs) {
		if(objs==null) {
			return -2;
		}
		int len = objs.length;
		for(int i = 0;i<len;i++) {
			if (objs[i] ==null ) {
				return i;
			}else if(objs[i] instanceof String && "".equals(objs[i])){
				return i;
			}
		}
		return -1;
	}
	public static void main(String[] args) {
	}
}
