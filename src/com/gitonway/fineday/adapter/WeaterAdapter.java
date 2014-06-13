package com.gitonway.fineday.adapter;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import com.gitonway.fineday.R;
import com.gitonway.fineday.domain.model.WeaterModel;
import com.gitonway.fineday.domain.model.WeaterObjModel;
import com.gitonway.fineday.interfaces.IWeaterListFinder;
import com.gitonway.fineday.interfaces.impl.WeaterFinderImpl;
import com.gitonway.fineday.utils.ImageUtils;
import com.gitonway.fineday.utils.TimeUtils;
import com.gitonway.fineday.widget.WeaterItem;
import com.gitonway.fineday.widget.WeaterItem_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EBean
public class WeaterAdapter extends BaseAdapter {
	
	@RootContext
	Context context;
	
	List<WeaterObjModel> mList = new ArrayList<WeaterObjModel>() ;
	
	public void appendToList(List<WeaterObjModel> lists) {
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

	public List<WeaterObjModel> getData() {
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
		WeaterObjModel model=mList.get(position);
		WeaterItem item;
		if (convertView == null) {
			item=WeaterItem_.build(context);
        } else {
        	item = (WeaterItem) convertView;
        }
		item.bind(model,position);
		
		return item;
	}

	
	
	
}
