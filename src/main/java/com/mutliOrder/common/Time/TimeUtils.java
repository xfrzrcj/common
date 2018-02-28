package com.mutliOrder.common.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;
/**
 * 瀛楃宸ュ叿绫�
 *
 */
public class TimeUtils {
	public static SimpleDateFormat DATE_FORMAT_YYYYMMDDHHMMSS;
	public static SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_HH_MM_SS;
	public static SimpleDateFormat DATE_FORMAT_YYYYMMDD_HHMMSS;
	public static SimpleDateFormat DATE_FORMAT_YYYYMMDD;
	public static SimpleDateFormat DATE_FORMAT_YYYYMMDDHH;
	public static SimpleDateFormat DATE_FORMAT_YYYYMMDD00;
	public static SimpleDateFormat DATE_FORMAT_US_MMDDYYYYHHMMSS;
	static{
		DATE_FORMAT_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
		DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		DATE_FORMAT_YYYYMMDD_HHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DATE_FORMAT_YYYYMMDDHH = new SimpleDateFormat("yyyyMMddHH");
		DATE_FORMAT_YYYYMMDD00 = new SimpleDateFormat("yyyyMMdd00");
		DATE_FORMAT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	}
	/**
	 * 鑾峰彇string鏍煎紡褰撳墠鏃堕棿
	 * @return
	 */
	public static String getTime() {
		long time = new Date().getTime();
		return String.valueOf(time);	
	}
	/**
	 * 鑾峰彇褰撳墠yyyyMMddHHmmss鏍煎紡鏃堕棿
	 * @return
	 */
	public static String getNowYMDHMS(){
		return DATE_FORMAT_YYYYMMDDHHMMSS.format(new Date());
	}
	/**
	 * 鑾峰彇褰撳墠yyyy-MM-dd-HH-mm-ss鏍煎紡鏃堕棿
	 * @return
	 */
	public static String getNowY_M_D_H_M_S(){
		return DATE_FORMAT_YYYY_MM_DD_HH_MM_SS.format(new Date());
	}
	
	public static int getYYYYMMDD(){
		String date = DATE_FORMAT_YYYYMMDD.format(new Date());
		int time = Integer.valueOf(date);
		return time;
	}
	
	public static String getNowYMD_HMS(){
		return DATE_FORMAT_YYYYMMDD_HHMMSS.format(new Date());
	}
	
	public static boolean startWithNumber(String str,int num){
		return Pattern.compile("^\\d{"+num+"}.*").matcher(str).matches();
	}
	
	public static String getTimestamp() {
		Calendar calender = Calendar.getInstance();
		
		return String.valueOf(calender.getTimeInMillis());
	}
	public static String getStringNotNull(String str,String defaultStr){
		if(str==null||"".equals(str.trim())){
			return defaultStr;
		}else{
			return str;
		}
	}
	public static String getYYYYMMDD_hhmmss(Date date) {
		return DATE_FORMAT_YYYYMMDD_HHMMSS.format(date);
	}

	public static Date getDateFromYYYYMMDD_hhmmss(String YYYYMMDD_hhmmss) {
		Date date = null;
		try {
			date = DATE_FORMAT_YYYYMMDD_HHMMSS.parse(YYYYMMDD_hhmmss);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getDayBefor(int day){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, day);
		return DATE_FORMAT_YYYYMMDD_HHMMSS.format(cal.getTime());
	}
	public static String getYYYYMMDDHHFromMillis(long milliseconds){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(milliseconds);
		return DATE_FORMAT_YYYYMMDDHH.format(cal.getTime());
	}
	
	public static String getYYYYMMDD00FromMillis(long milliseconds){
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(milliseconds);
		return DATE_FORMAT_YYYYMMDD00.format(cal.getTime());
	}
	
	public static String formatUSDate(String dateStr){
		DATE_FORMAT_US_MMDDYYYYHHMMSS = new SimpleDateFormat("MMMM d, yyyy hh:mm:ss a",Locale.US);
		String str = null;
		try {
			str = DATE_FORMAT_YYYYMMDD_HHMMSS.format(DATE_FORMAT_US_MMDDYYYYHHMMSS.parse(dateStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	public static void main(String[] args) {
		;
		System.out.println(formatUSDate("Oct 5, 2015 6:08:00 AM"));
	}
}
