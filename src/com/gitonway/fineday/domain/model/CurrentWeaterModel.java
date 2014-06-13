package com.gitonway.fineday.domain.model;

public class CurrentWeaterModel {
	/**
	 * 更新时间戳
	 */
	private long dataUptime;
	/**
	 * 时间
	 */
	private String date;
	/**
	 * 湿度
	 */
	private String humidity;
	/**
	 * 风向
	 */
	private String direct;
	/**
	 * 风力
	 */
	private String power;
	/**
	 * 当前温度
	 */
	private String temperature;
	/**
	 * 当前天气情况
	 */
	private String info;
	/**
	 * 天气图标
	 */
	private String img;
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getDataUptime() {
		return dataUptime;
	}
	public void setDataUptime(long dataUptime) {
		this.dataUptime = dataUptime;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getDirect() {
		return direct;
	}
	public void setDirect(String direct) {
		this.direct = direct;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

}
