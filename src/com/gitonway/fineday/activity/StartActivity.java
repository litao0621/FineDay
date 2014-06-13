package com.gitonway.fineday.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import android.widget.TextView;

import com.gitonway.fineday.R;
import com.umeng.analytics.MobclickAgent;

@Fullscreen
@EActivity(R.layout.activity_start)
public class StartActivity extends BaseActivity{

	
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
		
	}
	
	@Click
	void enterMainClicked(){
		this.finish();
	}

}
