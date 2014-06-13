package com.gitonway.fineday.utils;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import com.gitonway.fineday.domain.model.CityModel;
import com.gitonway.fineday.utils.json.CityJsonUtils;

public class ToJsonStrUtils {

	public static final String  start="city([";
	
	public static final String  end="]);";
	
	private static String k1="[";
	
	private static String k2="]";
	
	private static String c="\"";
	
	
	public static String addCityToJson(String oldstr,CityModel str) {
		String newstr = "";
	  if (TextUtils.isEmpty(oldstr)) {
		  newstr = cityToJson(str);
	  }	else {
		  newstr = cityToJson(str)+","+oldstr;
	  }
	  
		return newstr;
	}
	
	/**
	 * 将城市数据转化为json
	 * 
	 * 转化为如：
	 * 	[
	        "东丰",
	        "101060702"
    	]
	 * 
	 * @param city 城市数据
	 * @return
	 */
	
	public static String cityToJson(CityModel city) {
		String citystr=k1+c+city.getCityName()+c+","+c+city.getCityId()+c+k2;
		return citystr;
	}
	/**
	 * 删除城市
	 */
	public static void deleteCity(Context context,CityModel city) { 
		String dcity=cityToJson(city);
		String citystr = ACache.get(context).getAsString(
				PreferencesUtils.LIST_OF_CITY);
		String newstr="";
		if (citystr.contains(dcity+",")) {
			newstr=citystr.replace(dcity+",", "");
		}else {
			newstr=citystr.replace(","+dcity, "");
		}
		
		ACache.get(context).put(PreferencesUtils.LIST_OF_CITY, newstr);
		
		
	}
	
	/**
	 * 获得当前城市列表数据
	 * @throws Exception 
	 */
	public static List<CityModel> getCurrentCity(Context context) throws Exception {
		String citystr = ACache.get(context).getAsString(
				PreferencesUtils.LIST_OF_CITY);
		citystr = ToJsonStrUtils.start + citystr + ToJsonStrUtils.end;

		CityJsonUtils jsonUtils = new CityJsonUtils(context);
		return jsonUtils.readJson(citystr);
		
	}
	/**
	 * 获得当前城市列表大小
	 */
	public static int getCityListLength(Context context) {
		String citystr = ACache.get(context).getAsString(
				PreferencesUtils.LIST_OF_CITY);
		if (TextUtils.isEmpty(citystr)) {
			return 0;
		}
		
		return citystr.split(",").length/2;
		
	}
	/**
	 * 判断城市是否存在
	 */
	public static boolean isCityExist(Context context,String cityid) {
		String citystr = ACache.get(context).getAsString(
				PreferencesUtils.LIST_OF_CITY);
		if (TextUtils.isEmpty(citystr)) {
			return false;
		}
		return citystr.contains(cityid);
	}
	
}
