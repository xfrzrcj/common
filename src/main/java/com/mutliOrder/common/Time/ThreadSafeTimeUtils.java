package com.mutliOrder.common.Time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ThreadSafeTimeUtils {

	private static final ThreadLocal<DateFormat> YYYY_MM_DD_Thread = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//严格日期
			sdf.setLenient(false);
			return sdf ;
		}
	};
	
	private static final ThreadLocal<DateFormat> YYYY_MM_DD_HH_mm_SS_Thread = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			//严格日期
			sdf.setLenient(false);
			return sdf ;
		}
	};
	
	private static final ThreadLocal<DateFormat> YYYYMMDD_Thread = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//严格日期
			sdf.setLenient(false);
			return sdf ;
		}
	};
	
	private static final ThreadLocal<DateFormat> YYYY_MM_DD_HH_MM_SS_Thread = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//严格日期
			sdf.setLenient(false);
			return sdf;
		}
	};
	private static final ThreadLocal<DateFormat> YYYY_MM_DD_T_HH_MM_SS_Thread = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			//严格日期
			sdf.setLenient(false);
			return sdf;
		}
	};
	private static final ThreadLocal<DateFormat> YYYYMMDDHHMMSS_Thread = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			//严格日期
			sdf.setLenient(false);
			return sdf;
		}
	};
	
	private static final ThreadLocal<DateFormat> YYYYMMDDHHMMSSThread = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			//严格日期
			sdf.setLenient(false);
			return sdf;
		}
	};

	public static String formatYYYY_MM_DD_Thread(String str) throws ParseException {
		Date date = YYYY_MM_DD_HH_MM_SS_Thread.get().parse(str);
		return YYYY_MM_DD_Thread.get().format(date);
	}
	/**
	 * 判断是否是yyyy-MM-dd格式
	 * 
	 * @author 戚鹏飞 2017年9月17日 下午6:23:56
	 * @param str
	 * @return
	 */
	public static boolean isYYYYMMDD(String str) {
		if (str == null) {
			return false;
		}
		try {
			YYYY_MM_DD_Thread.get().parse(str);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	

	/**
	 * 判断是否是yyyy-MM-dd HH:mm:ss格式
	 * 
	 * @author 戚鹏飞 2017年9月17日 下午6:24:26
	 * @param str
	 * @return
	 */
	public static boolean isYYYYMMDDHHMMSS(String str) {
		if (str == null) {
			return false;
		}
		try {
			YYYY_MM_DD_HH_MM_SS_Thread.get().parse(str);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	/**
	 * 给指定的时间增加一年 安城格式
	 * @author wjg
	 * @return
	 * @throws ParseException 
	 */
	public static String increaTime(String str) throws ParseException{
		String newTime = null;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//严格日期
		sdf.setLenient(false);
		date = sdf.parse(str);
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.YEAR, 1);
		c.add(Calendar.SECOND, -1);
		date = c.getTime();
		newTime = YYYY_MM_DD_HH_MM_SS_Thread.get().format(date);
		return newTime;
	}
	/**
	 * 
	 * @author wjg
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static String increaTimeYYYY_MM_DD_Thread(String str) throws ParseException{
		String newTime = null;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//严格日期
		sdf.setLenient(false);
		date = sdf.parse(str);
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.YEAR, 1);
		c.add(Calendar.SECOND, -1);
		date = c.getTime();
		newTime = YYYY_MM_DD_Thread.get().format(date);
		return newTime;
	}
	public static void main(String[] args) throws ParseException {
		System.out.println(increaTimeYYYY_MM_DD_Thread("2017-2-18 00:00:00"));
		String time = formatYYYY_MM_DD_Thread("2018-02-17 23:59:59");
		System.out.println(time.substring(0, 10));
		System.out.println(getNowYYYYMMDDTHHMMSS());
	}
	
	
	/**
	 * 得到相应格式的日期
	 * @author wjg
	 * @return
	 */
	public static String getNowYYYYMMDDHHMMSS() {
		return YYYY_MM_DD_HH_MM_SS_Thread.get().format(Calendar.getInstance().getTime());
	}
	public static String getNowYYYYMMDDHHMMSS_NOGANG() {
		return YYYYMMDDHHMMSS_Thread.get().format(Calendar.getInstance().getTime());
	}
	public static String getNowYYYYMMDDTHHMMSS() {
		return YYYY_MM_DD_T_HH_MM_SS_Thread.get().format(Calendar.getInstance().getTime());
	}
	public static String getNowyyyyMMDDHHMMSS() {
		return YYYYMMDDHHMMSSThread.get().format(Calendar.getInstance().getTime());
	}
	
	public static String getNowYYYYMMDD() {
		return YYYY_MM_DD_Thread.get().format(Calendar.getInstance().getTime());
	}
	public static String getNowYYYYMMDD_NOGANG() {
		return YYYYMMDD_Thread.get().format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 得到Long类型时间
	 * @author wjg
	 * @param str
	 * @return
	 */
	public static Long paresYYYYMMDDHHMMSS(String str) {
		Long time = null;
		try {
			time = YYYY_MM_DD_HH_MM_SS_Thread.get().parse(str).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	public static Long parseYYYYMMDD(String str) {
		if (str == null) {
			return null;
		}
		try {
			return YYYY_MM_DD_Thread.get().parse(str).getTime();
		} catch (ParseException e) {
			return null;
		}
	}
	public static Long getNowTimeStamp() {
		return Calendar.getInstance().getTimeInMillis();
	}
	
	public static String getYYYY_MM_DD_Thread(String str) throws ParseException {
		Date date = YYYY_MM_DD_HH_MM_SS_Thread.get().parse(str);
		return YYYY_MM_DD_Thread.get().format(date);
	}
	
	public static String getNowYMDHMS() {
		return YYYY_MM_DD_HH_mm_SS_Thread.get().format(Calendar.getInstance().getTime());
	}
	
}
