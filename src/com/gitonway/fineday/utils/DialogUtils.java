package com.gitonway.fineday.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gitonway.fineday.R;
import com.gitonway.fineday.domain.CityLogic;
import com.gitonway.fineday.widget.titanic.Titanic;
import com.gitonway.fineday.widget.titanic.TitanicTextView;


public class DialogUtils {
	
	private static  DialogUtils _Instance = null;
	private static Dialog dialog=null;
	private TitanicTextView mTextLoading;
	private Titanic mTitanic;
	
	public static  DialogUtils Instance() {
        if (_Instance == null)
            _Instance = new DialogUtils();
        return _Instance;
    }

	public   Dialog createLoadingDialog(Context context) {
		Dialog loadingDialog = null;
		if (dialog==null) {
		
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		mTextLoading=(TitanicTextView)v.findViewById(R.id.tipTextView);
		mTitanic=new Titanic();
		mTitanic.start(mTextLoading);
		loadingDialog = new Dialog(context, R.style.loading_dialog_tran);
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		
		}else {
			return dialog;
		}
		return loadingDialog;

	}

}
