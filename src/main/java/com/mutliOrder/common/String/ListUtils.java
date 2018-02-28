package com.mutliOrder.common.String;

import java.util.List;

public class ListUtils {
	public static boolean isEmpty(@SuppressWarnings("rawtypes") List list) {
		if(list == null) {
			return true;
		}else{
			return list.isEmpty();
		}
	}
}
