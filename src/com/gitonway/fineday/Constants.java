package com.gitonway.fineday;

public class Constants {
	/**
	 * 天气详情
	 */
	public static final String WEATER_INFO_URL="http://tq.360.cn/api/weatherquery/query";
	/**
	 * 城市列表
	 */
	public static final String CITY_URL="http://cdn.weather.hao.360.cn/sed_api_area_query.php";
	/**
	 * 城市列表-省
	 */
	public static final String PROVINCE="http://cdn.weather.hao.360.cn/sed_api_area_query.php?grade=province&_jsonp=loadProvince";
	/**
	 * 城市列表-市
	 */
	public static final String CITY="http://cdn.weather.hao.360.cn/sed_api_area_query.php?grade=city&_jsonp=loadCity&code=08";
	/**
	 * 城市列表-县
	 */
	public static final String TOWN="http://cdn.weather.hao.360.cn/sed_api_area_query.php?grade=town&_jsonp=loadTown&code=0804";
	
	
	
	//事件ID
	/**
	 * 成功添加城市
	 */
	public static final String SUCCESS_ADD_CITY="successaddcity";
	/**
	 * 删除城市
	 */
	public static final String SUCCESS_DELETE_CITY="successdeletecity";
}
