package com.gitonway.fineday.domain;

import org.androidannotations.annotations.EBean;

import android.content.Context;

import com.gitonway.fineday.Constants;
import com.gitonway.fineday.domain.model.WeaterModel;
import com.gitonway.fineday.utils.ACache;
import com.gitonway.fineday.utils.LogUtils;
import com.gitonway.fineday.utils.http.HttpUtils;
import com.gitonway.fineday.utils.json.WeaterJsonUtils;

@EBean
public class WeaterLogic extends BaseLogic {
	
	
	private static WeaterLogic _Instance = null;

    public static WeaterLogic Instance() {
        if (_Instance == null)
            _Instance = new WeaterLogic();
        return _Instance;
    }
    
    public WeaterModel getWeaterList(Context context,String cityid) throws Exception {
    	WeaterModel modle = null;
    	String result=HttpUtils.getByHttpClient(context, 
    			Constants.WEATER_INFO_URL, 
    			value("code", cityid),
    			value("app", "tq360"),
    			value("_jsonp", "renderData"));
    	//-1为无网络连接
    	if (result.equals("-1")||result==null){
    		result=ACache.get(context).getAsString(cityid);
    		LogUtils.v("加载离线数据:(城市ID"+cityid+")"+result);
    		//若没有历史数据则进入
    		if (result==null) {
    			modle=new WeaterModel();
        		modle.setOffline(true);
        		return modle;
			}
    		WeaterJsonUtils toObj=new WeaterJsonUtils(context);
        	modle=toObj.readJson(result);
        	modle.setOffline(true);
    		return modle;
    	}
    	ACache.get(context).put(cityid, result);
    	WeaterJsonUtils toObj=new WeaterJsonUtils(context);
    	modle=toObj.readJson(result);
    	
    	return modle;
		
	}
}
