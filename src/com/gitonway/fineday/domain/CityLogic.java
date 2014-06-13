package com.gitonway.fineday.domain;

import java.util.List;

import android.content.Context;

import com.gitonway.fineday.Constants;
import com.gitonway.fineday.domain.model.CityModel;
import com.gitonway.fineday.domain.model.WeaterModel;
import com.gitonway.fineday.utils.ACache;
import com.gitonway.fineday.utils.LogUtils;
import com.gitonway.fineday.utils.ResourceUtils;
import com.gitonway.fineday.utils.http.HttpUtils;
import com.gitonway.fineday.utils.json.CityJsonUtils;

public class CityLogic extends BaseLogic {
	
	private static CityLogic _Instance = null;
	public static CityLogic Instance() {
        if (_Instance == null)
            _Instance = new CityLogic();
        return _Instance;
    }
	public List<CityModel> getCityList(Context context,String type,String cityid,String jsonp) throws Exception {
		String result=null;
		if (type.equals("province")) {
			result=ResourceUtils.geFileFromAssets(context, "citylist.txt");
		}else {
			result=ACache.get(context).getAsString(type+cityid);
			LogUtils.v("加载离线数据:(城市"+type+":"+cityid+")"+result);
			//若没有历史数据则进入
    		if (result==null) {
    			result=HttpUtils.getByHttpClient(context, 
    	    			Constants.CITY_URL, 
    	    			value("grade", type),
    	    			value("code", cityid),
    	    			value("_jsonp", jsonp));
    			if (!result.equals("-1")||result!=null) {
    				ACache.get(context).put(type+cityid, result);
				}
    			
			}
			
		}
		
		
		
		
		CityJsonUtils jsonUtils=new CityJsonUtils(context);
		return jsonUtils.readJson(result);
		
	}
}
