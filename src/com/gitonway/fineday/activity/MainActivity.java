package com.gitonway.fineday.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gitonway.fineday.MyApp;
import com.gitonway.fineday.R;
import com.gitonway.fineday.adapter.MyPagerAdapter;
import com.gitonway.fineday.adapter.WeaterAdapter;
import com.gitonway.fineday.domain.WeaterLogic;
import com.gitonway.fineday.domain.model.CityModel;
import com.gitonway.fineday.domain.model.WeaterModel;
import com.gitonway.fineday.domain.model.WeaterObjModel;
import com.gitonway.fineday.utils.ACache;
import com.gitonway.fineday.utils.LogUtils;
import com.gitonway.fineday.utils.PreferencesUtils;
import com.gitonway.fineday.utils.ToJsonStrUtils;
import com.gitonway.fineday.widget.*;
import com.gitonway.fineday.widget.crouton.Crouton;
import com.gitonway.fineday.widget.crouton.Style;
import com.gitonway.fineday.widget.jazzyviewpager.JazzyViewPager;
import com.gitonway.fineday.widget.jazzyviewpager.JazzyViewPager.TransitionEffect;
import com.umeng.analytics.MobclickAgent;

@Fullscreen
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnRefreshListener,
		OnPageChangeListener {
	
	@App
	MyApp app; 

	@ViewById(R.id.main_bg)
	LinearLayout mMainLayout;
	/**
	 * 今日天气Viewpage
	 */
	@ViewById(R.id.jazzy_pager)
	JazzyViewPager mViewPager;
	/**
	 * 天气列表
	 */
	@ViewById(R.id.weater_list)
	ListView mListViewWeater;
	/**
	 * 下拉刷新
	 */
	@ViewById(R.id.refresh_container)
	SwipeRefreshLayout mSwipeRefreshLayout;
	/**
	 * 淡入动画
	 */
	@AnimationRes
	Animation fadeIn;
	/**
	 * 天气列表适配器
	 */
	@Bean
	WeaterAdapter adapter;

	@Bean
	WeaterLogic weaterLogic;
	/**
	 * viewpager视图集
	 */
	private List<TodayWeaterInfo> views;
	/**
	 * 当前所有天气信息
	 */
	private List<WeaterModel> currrentlist;
	/**
	 * 当前所有城市列表
	 */
	private List<CityModel> currrentcitylist;
	/**
	 * 今日天气信息
	 */
	private TodayWeaterInfo todayWeaterInfo;
	/**
	 * 当前所选城市ID
	 */
	private String currentCityId = "101010100";
	/**
	 * 城市个数(上一次的)
	 */
	private int numberOfCity;
	/**
	 * 城市个数(实时的)
	 */
	private int length;
	/**
	 * 当前中页数
	 */
	private int pageindex;
	
	/**
	 * 是否首次进去本activity
	 */
	private boolean isFirstEnter=true;
	/**
	 * 是否首次进入程序
	 */
	private boolean isFirst;

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		length = ToJsonStrUtils.getCityListLength(this);
		if (length == 0) {

			if (isFirstEnter) {
				isFirstEnter=false;
			}else {
				this.finish();
			}
			
		}

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@AfterViews
	void initActivity() {
		// setBackgroud(TimeUtils.getHour());
		
		//打印UMeng统计测试所需信息
//		System.out.println(UMengUtils.getDeviceInfo(this));
		
		
		
		isFirst=PreferencesUtils.getBoolean(this, PreferencesUtils.FIRST_ENTER,true);
		
		
		
		int length = ToJsonStrUtils.getCityListLength(this);
		if (length == 0) {
			CitySelectionActivity_.intent(this).startForResult(TodayWeaterInfo.REQUEST_CODE);
		} else {
			showProgressDialog();
			viewInit();
			getWeaterListData();
		}
		if (isFirst) {
			firstEnter();
		}

	}

	/**
	 * 加载所选城市列表所有城市
	 */
	@Background
	void getWeaterListData() {
		
		currrentlist = new ArrayList<WeaterModel>();

		try {

			for (int i = 0; i < currrentcitylist.size(); i++) {

				WeaterModel model = WeaterLogic.Instance().getWeaterList(this,
						currrentcitylist.get(i).getCityId());
				currrentlist.add(model);
			}

		} catch (Exception e) {
			e.printStackTrace();
			mSwipeRefreshLayout.setRefreshing(false);
		}
		updateList(currrentlist);
	}

	/**
	 * 下拉刷新当前显示城市天气信息
	 * 
	 * @param cityid
	 *            当前显示城市ID
	 */
	@Background
	void onRefreshData(String cityid) {
		WeaterModel model = null;
		try {

			model = WeaterLogic.Instance().getWeaterList(this, cityid);
		} catch (Exception e) {
			e.printStackTrace();
			mSwipeRefreshLayout.setRefreshing(false);
		}
		updateSingleList(model);
	}

	/**
	 * 更新所有列表
	 * 
	 * @param models
	 *            天气信息集合
	 */
	@UiThread
	void updateList(List<WeaterModel> models) {
		dismissProgressDialog();
		if (models == null || models.size() == 0)
			return;
		WeaterModel model = models.get(0);
		try {

			if (model.isOffline()) {
				showToast("＞﹏＜网络可能有问题！");
			}
			// 为listview添加淡入动画
			// mListViewWeater.startAnimation(fadeIn);
			List<WeaterObjModel> mList = new ArrayList<WeaterObjModel>();
			mList = model.getWeather();
			// 初始化头部信息（当天天气信息）
			viewpagerInit(models);
			adapter.appendToList(mList);
			// 将刷新状态标注为false
			mSwipeRefreshLayout.setRefreshing(false);

		} catch (Exception e) {
			e.printStackTrace();
			mSwipeRefreshLayout.setRefreshing(false);
			LogUtils.w("列表更新失败");
		}

	}

	/**
	 * 更新当前所显示天气信息
	 * 
	 * @param model
	 *            当前天气信息
	 */
	@UiThread
	void updateSingleList(WeaterModel model) {
		dismissProgressDialog();
		try {
			if (model.isOffline()) {
				showToast("＞﹏＜网络可能有问题！");
			}
			List<WeaterObjModel> mList = new ArrayList<WeaterObjModel>();
			mList = model.getWeather();
			adapter.appendToList(mList);

			views.get(pageindex).bind(model);
			// 将刷新状态标注为false
			mSwipeRefreshLayout.setRefreshing(false);
		} catch (Exception e) {
			e.printStackTrace();
			mSwipeRefreshLayout.setRefreshing(false);
			LogUtils.w("单个列表更新失败");
		}
	}

	/**
	 * 首次进入
	 */
	private void firstEnter(){
		CityModel model=new CityModel();
		model.setCityId("101231001");
		model.setCityName("钓鱼岛");
		ACache.get(this).put(PreferencesUtils.LIST_OF_CITY,
				ToJsonStrUtils.addCityToJson(null, model));
		
		app.setFirst(isFirst);
		openActivity(StartActivity_.class);
		PreferencesUtils.putBoolean(this, PreferencesUtils.FIRST_ENTER, false);
		
	}
	/**
	 * 控件初始化
	 */
	private void viewInit() {
		List<CityModel> citylist = new ArrayList<CityModel>();

		try {
			citylist = ToJsonStrUtils.getCurrentCity(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		currrentcitylist = citylist;

		numberOfCity = currrentcitylist.size();
		if (!currrentcitylist.isEmpty()) {
			currentCityId = currrentcitylist.get(0).getCityId();
		}
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setColorScheme(R.color.comm_1, R.color.comm_2,
				R.color.list_bg, R.color.top_box);
		mListViewWeater.setAdapter(adapter);

	}

	/**
	 * viewpager初始化
	 */
	private void viewpagerInit(List<WeaterModel> allmodel) {

		try {

			views = new ArrayList<TodayWeaterInfo>();
			for (int i = 0; i < currrentcitylist.size(); i++) {
				todayWeaterInfo = TodayWeaterInfo_.build(this);
				
				
				todayWeaterInfo.bind(allmodel.get(i));
				views.add(todayWeaterInfo);
			}
			mViewPager.setTransitionEffect(TransitionEffect.CubeOut);
			mViewPager.setOnPageChangeListener(this);
			mViewPager.setOffscreenPageLimit(1);
			mViewPager.setAdapter(new MyPagerAdapter(views, mViewPager));
			mViewPager.setPageMargin(3);

		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.w("今日天气情况加载失败");
		}
	}

	/**
	 * 更具时间设置背景
	 */
	private void setBackgroud(int currentHour) {

		if (currentHour > 7 && currentHour <= 11)
			// 早上
			mMainLayout.setBackgroundResource(R.drawable.main_bg_1);

		else if (currentHour > 11 && currentHour <= 13)
			// 中午
			mMainLayout.setBackgroundResource(R.drawable.main_bg_2);

		else if (currentHour > 13 && currentHour <= 17)
			// 下午
			mMainLayout.setBackgroundResource(R.drawable.main_bg_3);

		else if (currentHour > 17 && currentHour <= 20)
			// 傍晚
			mMainLayout.setBackgroundResource(R.drawable.main_bg_4);

		else
			// 夜晚
			mMainLayout.setBackgroundResource(R.drawable.main_bg_5);

	}

	/**
	 * 显示ShortToast
	 */
	public void showToast(String pMsg) {
		Crouton.makeText(this, pMsg, Style.ALERT, R.id.toast_conten).show();
	}

	@Override
	public void onRefresh() {
		if (mViewPager.getCurrentItem() < 1 || adapter.getData().size() < 1) {
			getWeaterListData();
		} else {
			onRefreshData(currentCityId);
		}

		// showShortToast("刷新成功");
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		try {

			pageindex = arg0;
			currentCityId = currrentcitylist.get(arg0).getCityId();
			List<WeaterObjModel> mList = currrentlist.get(arg0).getWeather();
			mListViewWeater.startAnimation(fadeIn);
			adapter.appendToList(mList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@OnActivityResult(TodayWeaterInfo.REQUEST_CODE)
	void onResult(int resultCode) {
			//如果城市添加成功或有删除的城市但没有添加，从新加载界面
			if (resultCode==1||length!=numberOfCity||isFirst==true) {
				numberOfCity = length;
				viewInit();
				getWeaterListData();
			}
	}

}
