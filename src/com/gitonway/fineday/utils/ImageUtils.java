package com.gitonway.fineday.utils;

import com.gitonway.fineday.R;

public class ImageUtils {
	public static int getIcon(String type){
		boolean isSun=TimeUtils.isSun();
		if (type.equals("0")||type.equals("18")) {
			return isSun?R.drawable.w1_s:R.drawable.w1_m;
		}else if (type.equals("1")||type.equals("13")) {
			return isSun?R.drawable.w2_s:R.drawable.w2_m;
		}else if (type.equals("2")) {
			return R.drawable.w3;
		}else if (type.equals("3")||type.equals("7")||type.equals("21")) {
			return R.drawable.w4;
		}else if (type.equals("4")) {
			return R.drawable.w9;
		}else if (type.equals("8")||type.equals("9")||type.equals("22")||type.equals("23")) {
			return R.drawable.w5_6;
		}else if (type.equals("10")||type.equals("11")||type.equals("24")) {
			return R.drawable.w7;
		}else if (type.equals("12")||type.equals("25")) {
			return R.drawable.w8;
		}else if (type.equals("5")||type.equals("6")||type.equals("19")) {
			return R.drawable.w10;
		}else if (type.equals("14")) {
			return R.drawable.w11_12;
		}else if (type.equals("15")||type.equals("26")) {
			return R.drawable.w13_14;
		}else if (type.equals("16")||type.equals("27")) {
			return R.drawable.w15;
		}else if (type.equals("17")||type.equals("28")) {
			return R.drawable.w16;
		}else if (type.equals("20")||type.equals("29")||type.equals("30")||type.equals("31")) {
			return R.drawable.w17;
		}else if (type.equals("19")||type.equals("53")) {
			return R.drawable.w30;
		}else {
			return isSun?R.drawable.w2_s:R.drawable.w2_m;
		}
		
	} 

}
