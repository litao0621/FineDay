package com.gitonway.fineday.adapter;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gitonway.fineday.domain.model.CityModel;
import com.gitonway.fineday.widget.CityItem;
import com.gitonway.fineday.widget.CityItem_;
import com.gitonway.fineday.widget.WeaterItem;
import com.gitonway.fineday.widget.WeaterItem_;

@EBean
public class CityAdapter extends BaseAdapter {
	
	@RootContext
	Context context;
	
	List<CityModel> mList = new ArrayList<CityModel>() ;

	public void appendToList(List<CityModel> lists) {
		if (lists == null) {
			return;
		}
		mList.clear();
		mList.addAll(lists);
		try {
			notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CityModel> getData() {
		return mList;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void clear() {
		mList.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CityModel model=mList.get(position);
		CityItem item;
		if (convertView == null) {
			item=CityItem_.build(context);
        } else {
        	item = (CityItem) convertView;
        }
		item.bind(model);
		return item;
		
	}


}
