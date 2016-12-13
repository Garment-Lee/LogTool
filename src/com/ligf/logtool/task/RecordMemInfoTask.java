package com.ligf.logtool.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ligf.logtool.AppConfig;

/**
 * 打印系统内存占用情况的线程类
 * @author ligangfan
 *
 *说明：dumpsys meminfo命令需要android.permission.DUMP权限，该权限需要系统应用才生效，即需要系统签名。
 *没有系统签名该命令无法获取到系统的内存占用信息。
 */

public class RecordMemInfoTask extends Thread{
	
	private static final String TAG = "RecordMemInfoTask";
	private Process process = null;
	private OutputStreamWriter mMemoryInfoWriter = null;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	
	public RecordMemInfoTask() {
		// TODO Auto-generated constructor stub
		if (mMemoryInfoWriter == null) {
			try {
				mMemoryInfoWriter = new OutputStreamWriter(new FileOutputStream(AppConfig.LOG_SDCARD_PATH + File.separator + AppConfig.MEMORY_LOG_FILE_NAME));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			while(true){
				process = Runtime.getRuntime().exec("dumpsys meminfo");
				InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String line = null;
				mMemoryInfoWriter.write(mDateFormat.format(new Date()));
				mMemoryInfoWriter.write("\n");
				while((line = bufferedReader.readLine()) != null){
					mMemoryInfoWriter.write(line);
					mMemoryInfoWriter.write("\n");
					mMemoryInfoWriter.flush();
				}
				Thread.sleep(5000);//每个五秒执行一次该命令
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
}
