package com.nteport.admin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * getUUID(int length,String UUID_TYPE)
 * getUUID(int length)
 * getUUID(int start_length,String start_UUID_TYPE,String timeFormat,int end_length,String end_UUID_TYPE)
 * 
 * @author User
 *
 */
public class GUtilUuid {
	/**
	 * 表示仅数字
	 */
	public static final String UUID_TYPE_ONLY_NUMBER="0";
	/**
	 * 表示仅字母
	 */
	public static final String UUID_TYPE_ONLY_LETTER="1";
	/**
	 * 生成UUID随机数
	 * @param length 长度
	 * @return
	 */
	public static String getUUID(int length,String UUID_TYPE){
		String str="";
		String src="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String src_number="1234567890";
		String src_letter="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if ("0".equals(UUID_TYPE)) {
			src=src_number;
		}else if("1".equals(UUID_TYPE)) {
			src=src_letter;
		}
		for (int i = 0; i <length; i++) {
			int index=(int)Math.floor(Math.random()*src.length());
			str+=src.substring(index,index+1);
		}
		return str;
	}
	/**
	 * 生成UUID随机数
	 * @param length 长度
	 * @return
	 */
	public static String getUUID(int length){
		return getUUID(length,null);
	}
	/**
	 * 随机数+时间字符串+随机数
	 * @param timeFormat 时间格式字符串
	 * @return
	 */
	public static String getUUID(int start_length,String start_UUID_TYPE,String timeFormat,int end_length,String end_UUID_TYPE){
		SimpleDateFormat sdf=new SimpleDateFormat(timeFormat);
		return getUUID(start_length,start_UUID_TYPE)+sdf.format(new Date())+getUUID(end_length,end_UUID_TYPE);
	}
	public static void main(String[] args) {
		System.out.println(getUUID(32));
		System.out.println(getUUID(32,GUtilUuid.UUID_TYPE_ONLY_NUMBER));
		System.out.println(getUUID(32,GUtilUuid.UUID_TYPE_ONLY_LETTER));
		System.out.println(getUUID(5,null,"yyyyMMddHHmmssSSS",10,null));
		System.out.println(getUUID(0,null,"yyyyMMddHHmmssSSS",10,null));
	}
}
