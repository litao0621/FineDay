/*
 * 
 */

package com.gitonway.fineday.utils.json;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON解析抽象类
 * 
 * @author wang.wei
 */
public abstract class JsonPacket {

    private final Context mContext;

    /**
     * @param context
     */
    public JsonPacket(Context context) {
        mContext = context;
    }

    /**
     * @return
     */
    protected Context getContext() {
        return mContext;
    }

    /**
     * @param key
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public static String getString(String key, JSONObject jsonObject) throws JSONException {
        String res = "";
        if (jsonObject.has(key)) {
            if (key == null) {
                return "";
            }
            res = jsonObject.getString(key);
        }
        return res;
    }

    /**
     * @param key
     * @param jsonObject
     * @return
     * @throws JSONException 
     * @throws Exception
     */
    public static int getInt(String key, JSONObject jsonObject) throws JSONException  {
        int res = -1;
        if (jsonObject.has(key)) {
            res = jsonObject.getInt(key);
        }
        return res;
    }

    /**
     * @param key
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public static double getDouble(String key, JSONObject jsonObject) throws JSONException {
        double res = 0l;
        if (jsonObject.has(key)) {
            res = jsonObject.getDouble(key);
        }
        return res;
    }
    /**
     * @param key
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public static long getLong(String key, JSONObject jsonObject) throws JSONException {
        long res = 0l;
        if (jsonObject.has(key)) {
            res = jsonObject.getLong(key);
        }
        return res;
    }

    public static String toJson(String jsonStr){
    	
    	return jsonStr.substring(jsonStr.lastIndexOf("(")+1, jsonStr.lastIndexOf(")"));
    }
}
