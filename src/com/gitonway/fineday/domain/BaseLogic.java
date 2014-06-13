package com.gitonway.fineday.domain;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class BaseLogic {

	public BaseLogic() {
		// TODO Auto-generated constructor stub
	}

	/**请求参数键值*/
	public NameValuePair value(String key,String values) {
		NameValuePair nameValuePair = new BasicNameValuePair(key, values);
		return nameValuePair;
	}
}
