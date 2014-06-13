package com.gitonway.fineday;

import org.androidannotations.annotations.EApplication;

import android.app.Application;

@EApplication
public class MyApp extends Application {
	/**
	 * 是否首次进入
	 */
	public boolean isFirst;

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
}
