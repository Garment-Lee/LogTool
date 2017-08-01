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

import com.ligf.logtool.app.AppConfig;

/**
 * 打印系统Cpu占用情况的线程类
 * @author ligangfan
 *
 */
public class RecordCpuInfoTask extends Thread{

	private static final String TAG = "RecordCpuInfoTask";
	private OutputStreamWriter mCpuInfoWriter = null;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	private Process mProcess = null;

	public RecordCpuInfoTask(){
		try {
			mCpuInfoWriter = new OutputStreamWriter(new FileOutputStream(AppConfig.LOG_SDCARD_PATH + File.separator + AppConfig.CPU_LOG_FILE_NAME));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			mProcess = Runtime.getRuntime().exec("top -m 5");  //命令只需执行一次
			InputStreamReader inputStreamReader = new InputStreamReader(mProcess.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			if ((line = bufferedReader.readLine()) != null) {
				mCpuInfoWriter.write(line);
				mCpuInfoWriter.write("\n");
				if (line.startsWith("User")) {
					mCpuInfoWriter.write(mDateFormat.format(new Date()));
					mCpuInfoWriter.write("\n");
				}
				mCpuInfoWriter.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
