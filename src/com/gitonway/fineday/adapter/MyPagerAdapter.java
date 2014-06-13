package com.gitonway.fineday.adapter;

import java.util.List;

import com.gitonway.fineday.widget.TodayWeaterInfo;
import com.gitonway.fineday.widget.jazzyviewpager.JazzyViewPager;
import com.gitonway.fineday.widget.jazzyviewpager.OutlineContainer;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class MyPagerAdapter  extends PagerAdapter  {
	private final List<TodayWeaterInfo> mListViews;
	private JazzyViewPager mJazzy;
	
	public MyPagerAdapter(List<TodayWeaterInfo> mListViews,JazzyViewPager mJazzy) {
		this.mListViews = mListViews;
		this.mJazzy=mJazzy;
	}
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		container.addView(mListViews.get(position), LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mJazzy.setObjectForPosition(mListViews.get(position), position);
		return mListViews.get(position);
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object obj) {
		container.removeView(mJazzy.findViewFromObject(position));
	}
	@Override
	public int getCount() {
		return mListViews.size();
	}
	@Override
	public boolean isViewFromObject(View view, Object obj) {
		if (view instanceof OutlineContainer) {
			return ((OutlineContainer) view).getChildAt(0) == obj;
		} else {
			return view == obj;
		}
	}
}
