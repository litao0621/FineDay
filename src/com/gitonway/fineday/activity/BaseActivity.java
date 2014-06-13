package com.gitonway.fineday.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.gitonway.fineday.utils.DialogUtils;



public class BaseActivity extends Activity {
	/**
	 * 自定义进度加载框
	 * */
	protected Dialog progressDialog=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 无标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //设置全屏模式
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
	}
	
	/**
     * 显示LongToast
     */
    public void showLongToast(String pMsg) {
    	Toast.makeText(this, pMsg, Toast.LENGTH_LONG).show();
    }
    

    /**
     * 显示ShortToast
     */
    public void showShortToast(String pMsg) {
    	Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 更具类打开acitvity
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }
    public void showProgressDialog() {
		if (progressDialog==null) {
			
			DialogUtils dialog=DialogUtils.Instance();
			
			progressDialog =dialog.createLoadingDialog(this);
			
		}
		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
			
			
		
	}

	public void dismissProgressDialog() {
		if (progressDialog!=null) {
			progressDialog.dismiss();
		}
		
	}

    /**
     * 更具类打开acitvity,并携带参数
     */
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }
}
