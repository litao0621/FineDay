package com.gitonway.fineday.widget;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import com.gitonway.fineday.R;
import com.gitonway.fineday.domain.model.CityModel;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

@EViewGroup(R.layout.item_city)
public class CityItem extends LinearLayout {
	
	@ViewById(R.id.city_name)
	TextView mTextViewCityName;

	public CityItem(Context context) {
		super(context);
	}
	public void bind(CityModel model) {
		mTextViewCityName.setText(model.getCityName());
	}

}
