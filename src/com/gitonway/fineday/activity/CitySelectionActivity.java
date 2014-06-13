package com.gitonway.fineday.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.androidannotations.annotations.res.StringRes;

import android.animation.LayoutTransition;
import android.animation.LayoutTransition.TransitionListener;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gitonway.fineday.Constants;
import com.gitonway.fineday.MyApp;
import com.gitonway.fineday.R;
import com.gitonway.fineday.adapter.CityAdapter;
import com.gitonway.fineday.adapter.MyCityAapter;
import com.gitonway.fineday.domain.CityLogic;
import com.gitonway.fineday.domain.model.CityModel;
import com.gitonway.fineday.utils.ACache;
import com.gitonway.fineday.utils.LogUtils;
import com.gitonway.fineday.utils.PreferencesUtils;
import com.gitonway.fineday.utils.ToJsonStrUtils;
import com.gitonway.fineday.utils.http.HttpUtils;
import com.gitonway.fineday.widget.crouton.Crouton;
import com.gitonway.fineday.widget.crouton.Style;
import com.gitonway.fineday.widget.jazzylistview.JazzyHelper;
import com.gitonway.fineday.widget.jazzylistview.JazzyListView;
import com.gitonway.fineday.widget.shimmer.Shimmer;
import com.gitonway.fineday.widget.shimmer.ShimmerTextView;
import com.gitonway.fineday.widget.swipelistview.SwipeListView;
import com.nineoldandroids.animation.ObjectAnimator;
import com.umeng.analytics.MobclickAgent;

@Fullscreen
@EActivity(R.layout.activity_city_selection)
public class CitySelectionActivity extends BaseActivity {
	/** 动画执行时间 */
	private final int duration = 1 * 500;
	
	@App
	MyApp app;
	
	@ViewById(R.id.cityLayout)
	LinearLayout mLayoutCity;

	@ViewById(R.id.selectCity)
	LinearLayout mLayoutSelectCity;

	@ViewById(R.id.cityTitle)
	LinearLayout mLayoutCityTitle;

	@ViewById(R.id.cityContent)
	LinearLayout mLayoutCityContent;

	/**
	 * 标题
	 */
	@ViewById(R.id.currentCityTxt)
	ShimmerTextView mTextViewTitle;
	/**
	 * 添加城市按钮
	 */
	@ViewById(R.id.addCity)
	ImageButton mButtonAddCity;
	/**
	 * 我的城市列表
	 */
	@ViewById(R.id.currentCityList)
	ListView mListViewCurrentCity;
	/**
	 * 城市列表-省
	 */
	@ViewById(R.id.listProvince)
	JazzyListView mListViewProvince;
	/**
	 * 城市列表-市
	 */
	@ViewById(R.id.listCity)
	JazzyListView mListViewCity;
	/**
	 * 城市列表-县
	 */
	@ViewById(R.id.listTown)
	JazzyListView mListViewTown;

	/**
	 * 我的城市列表
	 */
	@ViewById(R.id.currentCityList)
	SwipeListView mListViewMyCity;
	/**
	 * 我的城市中的数据
	 */
	List<CityModel> citylist;

	/**
	 * 城市适配器
	 */
	@Bean
	CityAdapter adapterProvince;
	@Bean
	CityAdapter adapterCity;
	@Bean
	CityAdapter adapterTown;
	@Bean
	MyCityAapter adapterMyCity;

	/**
	 * 省市县名称
	 */
	@ViewById
	TextView c1;
	@ViewById
	TextView c2;
	@ViewById
	TextView c3;

	@StringRes
	String selectCity;
	@StringRes
	String myCity;
	@AnimationRes
	Animation fadeIn;

	/** 内容动画 */
	private LayoutTransition mTransitioner;
	/** 判断动画执行状态状态 */
	private boolean animIsStart = false;
	/**
	 * 城市个数
	 */
	private int numberOfCity;
	/**
	 * 当前选择城市信息
	 */
	private CityModel currentModel;

	private Shimmer shimmer;
	/**
	 * 是否首次进入程序
	 */
	private boolean isFirst;
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@AfterViews
	void initActivity() {
		addContentAnim();
		viewInit();
		toggleCity();
		getCityList("province", "", "loadProvince");

	}

	@Background
	void getCityList(String type, String cityid, String jsonp) {

		List<CityModel> models = null;
		try {
			models = CityLogic.Instance()
					.getCityList(this, type, cityid, jsonp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateCityList(models, type);
	}

	@UiThread
	void updateCityList(List<CityModel> models, String type) {
		dismissProgressDialog();
		if (type.equals("province")) {
			adapterProvince.appendToList(models);
		} else if (type.equals("city")) {
			adapterCity.appendToList(models);
		} else if (type.equals("town")) {
			adapterTown.appendToList(models);
		}

	}

	/**
	 * 控件初始化
	 */
	private void viewInit() {
		shimmer = new Shimmer();
		shimmer.start(mTextViewTitle);
		isFirst=app.isFirst();
		
		mListViewProvince.setAdapter(adapterProvince);
		mListViewCity.setAdapter(adapterCity);
		mListViewTown.setAdapter(adapterTown);
		mListViewMyCity.setAdapter(adapterMyCity);
		mListViewProvince.setTransitionEffect(JazzyHelper.SLIDE_IN);
		mListViewCity.setTransitionEffect(JazzyHelper.SLIDE_IN);
		mListViewTown.setTransitionEffect(JazzyHelper.SLIDE_IN);

		swipeListviewSetting();
	}

	/**
	 * 侧滑Listview配置
	 */
	private void swipeListviewSetting() {

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int screenwidth = size.x;

		// 滑动模式为仅支持左滑
		mListViewMyCity.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
		mListViewMyCity.setSwipeActionLeft(0);
		mListViewMyCity.setSwipeActionRight(0);
		mListViewMyCity.setOffsetLeft(screenwidth / 4 * 3);
		mListViewMyCity.setOffsetRight(0);
		mListViewMyCity.setAnimationTime(0);
		// 支持长按自动滑动
		mListViewMyCity.setSwipeOpenOnLongPress(true);

	}

	/**
	 * 删除城市操作
	 * 
	 * @throws Exception
	 */
	public void deleteCity(int position) throws Exception {
		
		if (citylist.size()<=1) {
			showToast("好歹留一个呗!");
			
		}else {
			
			MobclickAgent.onEvent(this, Constants.SUCCESS_DELETE_CITY);
			ToJsonStrUtils.deleteCity(this, citylist.get(position));
			citylist = ToJsonStrUtils.getCurrentCity(this);
			adapterMyCity.appendToList(citylist);
			mListViewMyCity.closeOpenedItems();
			LogUtils.i("删除城市："+citylist.get(position).getCityName());
		}
		
		
		
	}

	/**
	 * 添加内容动画
	 */
	private void addContentAnim() {
		mTransitioner = new LayoutTransition();
		mTransitioner.setDuration(duration);
		mTransitioner.addTransitionListener(new TransitionListener() {

			@Override
			public void startTransition(LayoutTransition transition,
					ViewGroup container, View view, int transitionType) {
				animIsStart = true;
			}

			@Override
			public void endTransition(LayoutTransition transition,
					ViewGroup container, View view, int transitionType) {
				animIsStart = false;
			}
		});
		mLayoutCityContent.setLayoutTransition(mTransitioner);
		// mLayoutSelectCity.setLayoutTransition(mTransitioner);
	}

	/**
	 * 城市选择与当前城市的切换
	 */
	private void toggleCity() {
		if (mListViewCurrentCity.getVisibility() == View.GONE) {
			mListViewCurrentCity.setVisibility(View.VISIBLE);
			mLayoutSelectCity.setVisibility(View.GONE);
			mTextViewTitle.setText(myCity);
			showMyCity();
			mButtonAddCity.setVisibility(View.GONE);
			

		} else {
			mListViewCurrentCity.setVisibility(View.GONE);
			mLayoutSelectCity.setVisibility(View.VISIBLE);
			mTextViewTitle.setText(selectCity);
			
			if (c3.getText().toString().equals("")) {
				mButtonAddCity.setVisibility(View.GONE);
			}else {
				mButtonAddCity.setVisibility(View.VISIBLE);
			}

		}
	}

	/**
	 * 显示当前城市
	 */
	private void showMyCity() {
		if (adapterMyCity.getCount() < 1) {

			citylist = new ArrayList<CityModel>();
			try {
				citylist = ToJsonStrUtils.getCurrentCity(this);
				adapterMyCity.appendToList(citylist);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void allItemClick(int type) {
		if(!HttpUtils.isNetworkConnAvail(this)){
			showToast("＞﹏＜没有网络，稍后再试");
		}
		
		mButtonAddCity.setVisibility(View.GONE);
	}

	/**
	 * 显示ShortToast
	 */
	public void showToast(String pMsg) {
		showToast(pMsg,null);
	}
	public void showToast(String pMsg,Style style) {
		if (style==null) {
			style=Style.ALERT;
		}
		Crouton.makeText(this, pMsg, style, R.id.toast_cityconten).show();
	}
	

	@ItemClick
	void listProvinceItemClicked(CityModel model) {
		getCityList("city", model.getCityId(), "loadCity");
		showProgressDialog();
		allItemClick(1);
		mLayoutCityTitle.setVisibility(View.VISIBLE);
		c1.setText(model.getCityName());
		adapterTown.clear();
		c2.setVisibility(View.INVISIBLE);
		mListViewCity.setVisibility(View.VISIBLE);
		mListViewTown.setVisibility(View.GONE);
		c2.setText("");
		c3.setText("");
		
		if (isFirst) {
			showToast("点击标题可切换到我的城市",Style.INFO);
			isFirst=false;
			app.setFirst(isFirst);
		}
		
	}

	@ItemClick
	void listCityItemClicked(CityModel model) {
		getCityList("town", model.getCityId(), "loadTown");
		showProgressDialog();
		allItemClick(2);
		c2.setVisibility(View.VISIBLE);
		mListViewTown.setVisibility(View.VISIBLE);
		c2.setText(model.getCityName());
		c3.setText("");
	}

	@ItemClick
	void listTownItemClicked(CityModel model) {
		allItemClick(3);
		c3.setText(model.getCityName());
		mButtonAddCity.setVisibility(View.VISIBLE);
		currentModel = model;
	}

	@Click
	void addCityClicked() {
		if (!HttpUtils.isNetworkConnAvail(this)) {
			showToast("＞﹏＜没有网络，稍后再试");
			return;
		}
		
		

		int length = ToJsonStrUtils.getCityListLength(this);

		if (length >= 5) {
			showToast("最多添加5个城市");
			return;
		}

		ObjectAnimator.ofFloat(mButtonAddCity, "alpha", 1, 0, 1)
				.setDuration(duration).start();

		if (ToJsonStrUtils.isCityExist(this, currentModel.getCityId())) {
			showToast("城市已存在");
		} else {
//			showToast("添加成功");
			
			MobclickAgent.onEvent(this, Constants.SUCCESS_ADD_CITY);
			String oldstr = ACache.get(this).getAsString(
					PreferencesUtils.LIST_OF_CITY);
			ACache.get(this).put(PreferencesUtils.LIST_OF_CITY,
					ToJsonStrUtils.addCityToJson(oldstr, currentModel));
			this.setResult(1);
			this.finish();
		}

	}

	@Click
	void currentCityTxtClicked() {
		if (!animIsStart) {
			toggleCity();
		}

	}
}
