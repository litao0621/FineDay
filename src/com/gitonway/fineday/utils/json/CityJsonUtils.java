package com.gitonway.fineday.utils.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.gitonway.fineday.domain.model.CityModel;

public class CityJsonUtils extends JsonPacket {
	
	List<CityModel> models = new ArrayList<CityModel>() ;

	public CityJsonUtils(Context context) {
		super(context);
	}
	
	public List<CityModel> readJson(String jsonStr) throws Exception {
		if (TextUtils.isEmpty(jsonStr))
			return null;
		jsonStr=toJson(jsonStr);
		JSONArray array = new JSONArray(jsonStr);
		
		for (int i = 0; i < array.length(); i++) {
			JSONArray ja=array.getJSONArray(i);
			CityModel model=new CityModel();
			model.setCityId(ja.getString(1));
			model.setCityName(ja.getString(0));
			models.add(model);
			
		}
		return models;
	}

}
