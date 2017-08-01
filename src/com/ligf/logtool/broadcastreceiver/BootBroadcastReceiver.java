package com.ligf.logtool.broadcastreceiver;

import com.ligf.logtool.service.LogService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 开机广播接收器（静态注册）
 * @author ligangfan
 *
 */
public class BootBroadcastReceiver extends BroadcastReceiver{

	static final String ACTION = "android.intent.action.BOOT_COMPLETED";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
			Log.i("Garment0815", "boot ...");
			Intent service = new Intent(context, LogService.class);
			context.startService(service);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true){
						Log.i("Garment", "android.intent.action.BOOT_COMPLETED");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
}
