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
 * ��ӡϵͳ�ڴ�ռ��������߳���
 * @author ligangfan
 *
 *˵����dumpsys meminfo������Ҫandroid.permission.DUMPȨ�ޣ���Ȩ����ҪϵͳӦ�ò���Ч������Ҫϵͳǩ����
 *û��ϵͳǩ���������޷���ȡ��ϵͳ���ڴ�ռ����Ϣ��
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
				Thread.sleep(5000);//ÿ������ִ��һ�θ�����
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
