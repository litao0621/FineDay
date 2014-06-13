package com.gitonway.fineday.widget;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitonway.fineday.R;
import com.gitonway.fineday.activity.*;
import com.gitonway.fineday.domain.model.CurrentWeaterModel;
import com.gitonway.fineday.domain.model.WeaterModel;
import com.gitonway.fineday.domain.model.WeaterObjModel;
import com.gitonway.fineday.utils.ImageUtils;
import com.gitonway.fineday.utils.TimeUtils;
import com.gitonway.fineday.widget.shimmer.Shimmer;
import com.gitonway.fineday.widget.shimmer.ShimmerTextView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewPropertyAnimator;

@EViewGroup(R.layout.viewpager_today_info)
public class TodayWeaterInfo extends LinearLayout {
	
	public static final int  REQUEST_CODE=1000;
	
	
	
	@ViewById(R.id.layout_week_)
	LinearLayout mLayoutWeek;
	@ViewById(R.id.layout_another_info_)
	LinearLayout mLayoutInfo;
	
	/**
	 * 今天周几
	 */
	@ViewById(R.id.today_week)
	TextView mTextViewWeek;
	/**
	 * 今天的时间（MM/dd）
	 */
	@ViewById(R.id.today_date)
	TextView mTextViewDate;
	/**
	 * 地区
	 */
	@ViewById(R.id.area)
	ShimmerTextView mTextViewArea;
	/**
	 * 今天天气图标
	 */
	@ViewById(R.id.today_weater_icon)
	ImageView mImageViewIcon;
	/**
	 * 今天空气湿度
	 */
	@ViewById(R.id.temph)
	TextView mTextViewTempH;
	/**
	 * 风力风向
	 */
	@ViewById(R.id.wind)
	TextView mTextViewWind;
	/**
	 *  空气质量
	 */
	@ViewById(R.id.air)
	TextView mTextViewAir;
	/**
	 * 当前温度
	 */
	@ViewById(R.id.currentweater)
	TextView mTextViewCurrentWeater;

	/**
	 * 今天空气质量
	 */
	@ViewById(R.id.templ)
	TextView mTextViewTempL;
	/**
	 * 天气情况
	 */
	@ViewById(R.id.weaterinfo)
	TextView mTextViewWeaterInfo;
	
	private Shimmer shimmer;
	
	
	private int layoutX;
	
	
	public TodayWeaterInfo(Context context) {
		super(context);
	}
	public void bind(WeaterModel allmodel) {
		CurrentWeaterModel cModel = allmodel.getCurrentWeater();
		List<WeaterObjModel> mList = new ArrayList<WeaterObjModel>();
		mList = allmodel.getWeather();
		
		shimmer = new Shimmer();
        shimmer.start(mTextViewArea);

		mTextViewWeek.setText(TimeUtils.getWeek(mList.get(0).getDate()));
		mTextViewDate.setText(TimeUtils.getMonthDay(allmodel.getTime()));
		mTextViewArea.setText(allmodel.getArea().get(2).getAreaname());
		mImageViewIcon
				.setImageResource(ImageUtils.getIcon(cModel.getImg()));
		mTextViewTempH.setText(mList.get(0).getTempH());
		mTextViewTempL.setText(mList.get(0).getTempL());
		mTextViewWind.setText(cModel.getDirect() + " " + cModel.getPower());
		mTextViewAir.setText(setWeaterAQI(allmodel.getPm25()));
		mTextViewCurrentWeater.setText(cModel.getTemperature() + "°");
		mTextViewWeaterInfo.setText(cModel.getInfo());
//		setWeaterAQI(allmodel.getPm25());
    }
	/**
	 * 设置空气质量
	 */
	private String setWeaterAQI(int aqi) {
		String s = "";
		if (aqi >= 0 && aqi <= 50) {
			s = "空气优";
		} else if (aqi > 50 && aqi <= 100) {
			s = "空气良";
		} else if (aqi > 100 && aqi <= 150) {
			s = "轻度污染";
		} else if (aqi > 150 && aqi <= 200) {
			s = "中度污染";
		} else if (aqi > 200 && aqi <= 300) {
			s = "重度污染";
		} else if (aqi > 300) {
			s = "严重污染";
		}
//		mTextViewAir.setText(s);
		return s;
	}
	/**
	 * 根据天气情况设置字体颜色
	 */
	private void setWeaterTextColor(String iconnum) {
		int num = Integer.parseInt(iconnum);
		if (num >= 0 && num < 2)
			mTextViewWeaterInfo.setTextColor(getResources().getColor(
					R.color.comm_1));
		else {
			mTextViewWeaterInfo.setTextColor(getResources().getColor(
					R.color.comm_2));
		}
	}
	
	@Click
	void areaClicked(){
		
		CitySelectionActivity_.intent(getContext()).startForResult(REQUEST_CODE);
	}
	
	@Click
	void currentweaterClicked(){
		
		float alpha=mLayoutInfo.getAlpha();
		layoutX=mLayoutInfo.getWidth();
		//添加当前温度的点击效果
		ObjectAnimator.ofFloat(mTextViewCurrentWeater, "alpha", 1, 0.2f, 1).setDuration(2000).start();
		//控制风力风向，空气质量等信息的显示与隐藏
		if (alpha==0.0f) {
			ViewPropertyAnimator.animate(mLayoutInfo).alpha(1).x(0).setDuration(1500);
		}else if (alpha==1.0) {
			ViewPropertyAnimator.animate(mLayoutInfo).alpha(0).x(layoutX).setDuration(1500);
		}
		
	}

}
