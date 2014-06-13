package com.gitonway.fineday.domain.model;

import java.util.List;

public class WeaterModel {
	/**
	 * 时间
	 */
	private long time;
	/**
	 * 地区
	 */
	private List<AreaModel> area;
	/**
	 * 天气情况
	 */
	private List<WeaterObjModel> weather;
	/**
	 * 当前天气情况
	 */
	private CurrentWeaterModel currentWeater;
	
	/**
	 * PM2.5
	 */
	private int pm25;
	/**
	 * 是否为离线数据
	 */
	private boolean isOffline;
	
	
	
	public boolean isOffline() {
		return isOffline;
	}
	public void setOffline(boolean isOffline) {
		this.isOffline = isOffline;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public List<AreaModel> getArea() {
		return area;
	}
	public void setArea(List<AreaModel> area) {
		this.area = area;
	}
	public List<WeaterObjModel> getWeather() {
		return weather;
	}
	public void setWeather(List<WeaterObjModel> weather) {
		this.weather = weather;
	}
	public CurrentWeaterModel getCurrentWeater() {
		return currentWeater;
	}
	public void setCurrentWeater(CurrentWeaterModel currentWeater) {
		this.currentWeater = currentWeater;
	}
	public int getPm25() {
		return pm25;
	}
	public void setPm25(int pm25) {
		this.pm25 = pm25;
	}
}
