package com.gitonway.fineday.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.R.bool;
import android.os.SystemClock;
import android.provider.Settings.System;
import android.text.GetChars;

public class TimeUtils {

	/**
	 * 获取当前时间（小时）
	 */
	public static int getHour() {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		return now.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * 获取当前为周几
	 */
	public static String getWeek(String pTime) {

		String Week = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week += "日";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week += "一";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week += "二";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week += "三";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week += "四";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week += "五";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week += "六";
		}

		return "星期"+Week;
	}

	public static String getMonthDay(long time) {
		SimpleDateFormat formatMD = new SimpleDateFormat("MM/dd",Locale.CHINA);
		String date = formatMD.format(new Date(time*1000));
		return date;
	}
	
	public static boolean isSun(){
		int currentHour=getHour();
		if (currentHour>6&&currentHour<19) {
			return true;
		}
		return false;
	}
	
}
