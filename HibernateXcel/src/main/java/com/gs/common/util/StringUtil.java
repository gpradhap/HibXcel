package com.gs.common.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {


	/**
	 * 
	 * this method is to form reflection methodnames
	 * 
	 * @param fieldName
	 * @param methodPrefix
	 * @return String
	 * 
	 * StringUtil.formFieldToMethodName("salary","get") returns: getSalary
	 * StringUtil.formFieldToMethodName("docName","get") returns: getDocName
	 * StringUtil.formFieldToMethodName("DocId","get") returns: getDocId
	 * 
	 */
	public static String formFieldToMethodName(String fieldName,String methodPrefix){

		if (StringUtils.isBlank(fieldName) || StringUtils.isBlank(methodPrefix)) {
			return null;
		}

		String methodName = null;
		String fieldFirstChar = null;
		String fieldXceptFirstChar = null;
		try {
			StringBuilder strBuild = new StringBuilder(methodPrefix);
			fieldFirstChar = fieldName.substring(0, 1);
			fieldXceptFirstChar = fieldName.substring(1);
			strBuild.append(fieldFirstChar.toUpperCase());
			strBuild.append(fieldXceptFirstChar);

			methodName = strBuild.toString();
		} catch (Exception e) {
			System.out.println("methodPrefix: " + methodPrefix
					+ ",fieldFirstChar: " + fieldFirstChar
					+ ",fieldXceptFirstChar: " + fieldXceptFirstChar
					+ ",exception: " + e.getMessage());
			e.printStackTrace();
		}

		return methodName;
	}
	
	
	/**
	 * 
	 * @param String: inputStr
	 * @param int lastChars
	 * @return String
	 * 
	 * StringUtil.getSubString("1234567890", 10) - return 1234567890;
	 * StringUtil.getSubString("+1234567890", 10) - return 1234567890;
	 * StringUtil.getSubString("+0091234567890", 10) - return 1234567890;
	 * StringUtil.getSubString("12345678", 10) - return 12345678;
	 */	
	public static String getSubString(String inputStr, int lastChars) {
		if(inputStr.length()<=10) return inputStr;
		int beginIndex = inputStr.length() - lastChars;
		int endIndex = inputStr.length();

		return inputStr.substring(beginIndex, endIndex);
	}

	/**
	 * 
	 * @param strVarArg
	 * @return boolean
	 * 
	 *  StringUtil.isNoBlankInStrArr() - return false;
	 *  StringUtil.isNoBlankInStrArr("") - return false;
	 *  StringUtil.isNoBlankInStrArr(null) - return false;
	 *  StringUtil.isNoBlankInStrArr("A","") - return false;
	 *  StringUtil.isNoBlankInStrArr("A",null) - return false;
	 *  StringUtil.isNoBlankInStrArr("A","B") - return true;
	 */
	public static boolean isNotBlankInArray(String... strVarArg) {
		if (null == strVarArg || strVarArg.length == 0) {
			return false;
		}
		for (int index = 0; index < strVarArg.length; index++) {
			try {

				if (StringUtils.isNotBlank(strVarArg[index]))
					continue;
				else
					return false;

			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/*
	 * @param String
	 * return String
	 * 
	 * stripNonDigits("(+1)X(123)Y456Z0789...") returns 1123456789
	 */
	public static String stripNonDigits(String str){
		return str.replaceAll("[^0-9?]","");
	}
	
	public static void testStripNonDigits(){
		String str1 = "+123456789";
		String str2 = "123 456 7890";
		String str3 = "(123) 456-7890";
		String str4 = "123-456-0789";
		String str5 = " 123  456-0789";
		String str6 = "+1(123)-456 0789";
		String str7 = "..+1..(123).456.0789...";
		String str8 = "(+1)X(123)Y456Z0789...";
		
		System.out.println(str1 +" -->" + stripNonDigits(str1));
		System.out.println(str2 +" -->" + stripNonDigits(str2));
		System.out.println(str3 +" -->" + stripNonDigits(str3));
		System.out.println(str4 +" -->" + stripNonDigits(str4));
		System.out.println(str5 +" -->" + stripNonDigits(str5));
		System.out.println(str6 +" -->" + stripNonDigits(str6));
		System.out.println(str7 +" -->" + stripNonDigits(str7));
		System.out.println(str8 +" -->" + stripNonDigits(str8));
	}

	public static void testGetSubString(){
		
		String mdn1 = "X1234567890";
		String mdn2 = "1234567890";
		String mdn3 = "123456789";
		String mdn4 = "XY1234567890";
		
		 System.out.println(mdn1+"=>"+getSubString(mdn1, 10));
		 System.out.println(mdn2+"=>"+getSubString(mdn2, 10));
		 System.out.println(mdn3+"=>"+getSubString(mdn3, 10));
		 System.out.println(mdn4+"=>"+getSubString(mdn4, 10));
	}

	private static void testIsNotBlankInArray() {
		System.out.println(" " + isNotBlankInArray());
		System.out.println(" " + isNotBlankInArray(""));
		System.out.println(" " + isNotBlankInArray(null));
		System.out.println(" " + isNotBlankInArray("A", ""));
		System.out.println(" " + isNotBlankInArray("A", null));
		System.out.println(" " + isNotBlankInArray("A", "B"));
	}

	private static void testFormFieldToMethodName(){
		System.out.println("StringUtil.formFieldToMethodName(\"salary\",\"get\") returns: "+formFieldToMethodName("salary","get"));
		System.out.println("StringUtil.formFieldToMethodName(\"docName\",\"get\") returns: "+formFieldToMethodName("docName","get"));
		System.out.println("StringUtil.formFieldToMethodName(\"DocId\",\"get\") returns: "+formFieldToMethodName("DocId","get"));
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// testIsNotBlankInArray();
		// testGetSubString();
		testFormFieldToMethodName();
	}
	

}
