package com.gitonway.fineday.widget;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.R.integer;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitonway.fineday.R;
import com.gitonway.fineday.domain.model.WeaterObjModel;
import com.gitonway.fineday.utils.ImageUtils;
import com.gitonway.fineday.utils.TimeUtils;

@EViewGroup(R.layout.item_weater)
public class WeaterItem extends LinearLayout  {
	
	
	@ViewById(R.id.item_weater_)
	public LinearLayout mLayoutWeater;
	/**
	 * 周几
	 */
	@ViewById(R.id.week_)
	public TextView mTextViewWeek;
	/**
	 * 天气图标
	 */
	@ViewById(R.id.wcicon_)
	public ImageView mTextViewIcon;
	/**
	 * 最高温
	 */
	@ViewById(R.id.tempH_)
	public TextView mTextViewTempH;
	/**
	 * 最低温
	 */
	@ViewById(R.id.tempL_)
	public TextView mTextViewTempL;
	
	public WeaterItem(Context context) {
		super(context);
	}
	
	public void bind(WeaterObjModel model,int position) {
		int color = 0;
		
		if (position==0) {
			color=getResources().getColor(R.color.item_first_color);
		}else {
			color=getResources().getColor(R.color.boxBg);
		}
		mLayoutWeater.setBackgroundColor(color);
		mTextViewWeek.setText(TimeUtils.getWeek(model.getDate()));
		mTextViewIcon.setImageResource(ImageUtils.getIcon(TimeUtils.isSun()?model.getTypeSun():model.getTypeMoon()));
		mTextViewTempH.setText(model.getTempH());
		mTextViewTempL.setText(model.getTempL());
    }
	
	
	

}
