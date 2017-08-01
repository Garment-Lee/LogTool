package com.ligf.logtool.app;

import android.app.Application;
import android.content.Context;

public class LogApplication extends Application{

	private static Context mContext = null;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = getApplicationContext();
	}
	
	public static Context getContext(){
		return mContext;
	}
}
