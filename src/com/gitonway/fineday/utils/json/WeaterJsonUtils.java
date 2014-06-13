package com.gitonway.fineday.utils.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gitonway.fineday.domain.model.AreaModel;
import com.gitonway.fineday.domain.model.CurrentWeaterModel;
import com.gitonway.fineday.domain.model.WeaterModel;
import com.gitonway.fineday.domain.model.WeaterObjModel;

import android.content.Context;
import android.text.TextUtils;

public class WeaterJsonUtils extends JsonPacket {

	private WeaterModel model = new WeaterModel();

	public WeaterJsonUtils(Context context) {
		super(context);
	}

	public WeaterModel readJson(String jsonStr) throws Exception {
		if (TextUtils.isEmpty(jsonStr))
			return null;
		jsonStr=toJson(jsonStr);
		JSONObject obj = new JSONObject(jsonStr);
		
		
		// 获取时间
		model.setTime(getLong("dataUptime", obj.getJSONObject("realtime")));
		// 获取地区
		model.setArea(getAreaList(obj.getJSONArray("area")));
		// 获取天气详情
		model.setWeather(getWeaterObjList(obj.getJSONArray("weather")));
		//获取实时天气情况
		model.setCurrentWeater(getCurrentWeater(obj.getJSONObject("realtime")));
		// 获取PM2.5信息
		model.setPm25(getPM25Info(obj.getJSONObject("pm25")));
		
		

		return model;
	}
	
	
	
	/**
	 * 获取地区集合
	 * 
	 * @param array 地区集合
	 * @return
	 * @throws JSONException
	 */

	private List<AreaModel> getAreaList(JSONArray array) throws JSONException {
		List<AreaModel> areaList = new ArrayList<AreaModel>();

		for (int i = 0; i < array.length(); i++) {
			AreaModel model = new AreaModel();
			JSONArray subArray=array.getJSONArray(i);
			//获取地区名称
			model.setAreaname(subArray.getString(0));
			//获取地区ID
			model.setAreaid(subArray.getString(1));
			
			areaList.add(model);
			
		}

		return areaList;
	}
	/**
	 * 获取天气详细列表
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	
	private List<WeaterObjModel> getWeaterObjList(JSONArray array) throws JSONException{
		List<WeaterObjModel> weaterList=new ArrayList<WeaterObjModel>();
		
		for (int i = 0; i < array.length(); i++) {
			WeaterObjModel model=new WeaterObjModel();
			JSONObject obj=array.getJSONObject(i);
			JSONObject subObj=obj.getJSONObject("info");
			//白天天气信息
			JSONArray sunArray=subObj.getJSONArray("day");
			//夜间天气信息
			JSONArray moonArray=subObj.getJSONArray("night");
			
			
			//设置时间
			model.setDate(getString("date", obj));
			//白天天气类型
			model.setTypeSun(sunArray.getString(0));
			//夜间天气类型
			model.setTypeMoon(moonArray.getString(0));
			//白天天气情况
			model.setWcSun(sunArray.getString(1));
			//夜间天气情况
			model.setWcMoon(moonArray.getString(1));
			//最高温度
			model.setTempH(sunArray.getString(2)+"℃");
			//最低温度
			model.setTempL(moonArray.getString(2)+"℃");
			//白天风向
			model.setWindSun(sunArray.getString(3));
			//夜间风向
			model.setWindMoon(moonArray.getString(3));
			//白天风力
			model.setWindPowerSun(sunArray.getString(4));
			//夜间风力
			model.setWindPowerMoon(moonArray.getString(4));
			
			
			weaterList.add(model);
			
		}
		
		return weaterList;
	}
	private CurrentWeaterModel getCurrentWeater(JSONObject obj) throws JSONException{
		CurrentWeaterModel model=new CurrentWeaterModel();
		model.setDataUptime(getLong("dataUptime", obj));
		model.setDate(getString("date", obj));
		model.setDirect(getString("direct", obj.getJSONObject("wind")));
		model.setHumidity(getString("humidity", obj.getJSONObject("weather")));
		model.setInfo(getString("info", obj.getJSONObject("weather")));
		model.setPower(getString("power", obj.getJSONObject("wind")));
		model.setTemperature(getString("temperature", obj.getJSONObject("weather")));
		model.setImg(getString("img", obj.getJSONObject("weather")));
		return model;
	}
	
	/**
	 * 获取PM2.5
	 * 
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	private int getPM25Info(JSONObject obj) throws JSONException{
		return getInt("aqi", obj);
	}

}
