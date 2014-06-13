package com.gitonway.fineday.widget;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gitonway.fineday.R;
import com.gitonway.fineday.activity.CitySelectionActivity;
import com.gitonway.fineday.domain.model.CityModel;


@EViewGroup(R.layout.item_mycity)
public class MyCityItem extends RelativeLayout{

	@ViewById(R.id.front)
	TextView mTextViewCity;
	
	private int position;
	
	public MyCityItem(Context context) {
		super(context);
	}
	
	public void bind(CityModel model,int position) {
		this.position=position;
		mTextViewCity.setText(model.getCityName());
	}
	
	@Click
	void deleteCityClicked(){
		try {
			((CitySelectionActivity)getContext()).deleteCity(position);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
