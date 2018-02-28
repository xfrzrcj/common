package com.mutliOrder.common.String;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 字符工具类
 *
 */
public class StringUtils {
	private static final char[] numChars = {'0','1','2','3','4','5','6','7','8','9'};
	private static final char[] letterChars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private static final Set<Character> numChar = new HashSet<Character>();
	private static final Set<Character> letterChar = new HashSet<Character>();
	static {
		for(char c:numChars) {
			numChar.add(c);
		}
		for(Character c:letterChars) {
			letterChar.add(c);
			letterChar.add(Character.toUpperCase(c));
		}
	}
	/**
	 * 将首尾的一个','去掉
	 * @param str
	 * @return
	 */
	public static String trimComma(String str) {
		if(str.startsWith(",")) {
			str = str.substring(1);
		}
		if(str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	/**
	 * 判断是否为数字
	 * @param str
	 * @return
	 */
	public static Boolean isNumType(String str) {
		
		Pattern pattern = Pattern.compile("[0-9]*");
		if(null == str)
			return false;
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches()){
			return false;
		}
		return true;
	}
	/**
	 * 检查string是否为空，若null,或lenth为0，或字符为null等都判断为空
	 * @param field
	 * @return
	 */
	public static boolean filedIsNull(String field) {
		if(null == field)
			return true;
		if(field.length() == 0)
			return true;
//		if(field.equalsIgnoreCase("null"))
//			return true;
		
		return false;	
	}
	/**
	 * 将\"转化为\\\\\"，专用于solr精确查询语句中含有"的清洗，避免异常
	 * @param name
	 * @return
	 */
	public static String nameClean(String name){
		if(null!=name){
			return name.replaceAll("\"", "\\\\\"").replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
		}
		return "";
	}
	
	public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 
    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
 
    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }
 
    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByName(String str) {
        if (str == null) {
            return false;
        }
        // 大小写不同：\\p 表示包含，\\P 表示不包含
        // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
        String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str.trim()).find();
    }
    
    public static boolean isNum(char c) {
		return numChar.contains(c);
    }
    
    public static boolean isLetter(char c) {
		return letterChar.contains(c);
    }
}
