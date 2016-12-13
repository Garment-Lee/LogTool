package com.ligf.logtool.task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ligf.logtool.AppConfig;
import com.ligf.logtool.LogApplication;
import com.ligf.logtool.ProcessInfo;
import com.ligf.logtool.StreamConsumer;

import android.util.Log;

/**
 * 打印机器中的Logcat信息，过滤了相应的tab的信息
 * @author ligangfan
 *
 */
public class LogcatTabInfoTask extends Thread{

	/**过滤的TAG字符串*/
	private String mTagString = null;
	private SimpleDateFormat mFileDateFormate = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
	private Process process = null;
	
	public LogcatTabInfoTask(String tagString){
		this.mTagString = tagString;
	}
	
	public LogcatTabInfoTask() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		clearLogCache();
		List<ProcessInfo> processInfos = getAllProcessInfo(getAllProcess());
		killLogcatProcess(processInfos);
		startRecordingCommand();
		//add for test
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
	
	/**
	 * 清除logcat缓存
	 */
	private void clearLogCache(){
		Process process = null;
		List<String> commandList = new ArrayList<String>();
		commandList.add("logcat");
		commandList.add("-c");
		try {
			process = Runtime.getRuntime().exec(commandList.toArray(new String[commandList.size()]));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			process.destroy();
		}
	}
	
	/**
	 * 获取所有的进程的信息
	 * @return processList 保存进程信息的列表
	 */
	private List<String> getAllProcess(){
		List<String> processList = new ArrayList<String>();
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ps");
			StreamConsumer outStream = new StreamConsumer(process.getInputStream(), processList);
			outStream.start();
			if (process.waitFor() != 0) {
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processList;
	}
	
	/**
	 * 获取进程信息，封装到ProcessInfo类中
	 * @param list
	 * @return
	 */
	private List<ProcessInfo> getAllProcessInfo(List<String> list){
		List<ProcessInfo> processInfos = new ArrayList<ProcessInfo>();
		for(int i = 0; i < list.size(); i ++){
			String[] infoArr = list.get(i).split(" ");
			List<String> origInfos = new ArrayList<String>();
			for (String info : infoArr) {
				if (!info.equals("")) {
					origInfos.add(info);
				}
			}
			if (origInfos.size() == 9) {
				ProcessInfo processInfo = new ProcessInfo();
				processInfo.mUser = origInfos.get(0);
				processInfo.mPid = origInfos.get(1);
				processInfo.mPPid = origInfos.get(2);
				processInfo.mName = origInfos.get(8);
			}
		}
		return processInfos;
	}
	
	/**
	 * 杀掉Logcat相关进程，防止多个进程同时读写Log信息
	 * 
	 * @param processInfos
	 */
	private void killLogcatProcess(List<ProcessInfo> processInfos) {
		String packageName = LogApplication.getContext().getPackageName();
		String myUser = getAppUser(packageName, processInfos);
		for (ProcessInfo processInfo : processInfos) {
			if (processInfo.mName.toLowerCase().equals("logcat") && processInfo.mUser.equals(myUser)) {
				android.os.Process.killProcess(Integer.parseInt(processInfo.mPid));
			}
		}
	}
	
	/**
	 * 获取当前进程的User属性
	 * 
	 * @param packageName
	 * @param processInfos
	 * @return
	 */
	private String getAppUser(String packageName, List<ProcessInfo> processInfos) {
		for (ProcessInfo processInfo : processInfos) {
			if (packageName.equals(processInfo.mUser)) {
				return processInfo.mUser;
			}
		}
		return null;
	}
	
	/**
	 * 执行命令
	 */
	private void startRecordingCommand(){
		
		String logFileName = mFileDateFormate.format(new Date()) + ".log";
		List<String> commandList = new ArrayList<String>();
		commandList.add("logcat");
		commandList.add("-f");
		commandList.add(AppConfig.LOG_SDCARD_PATH + File.separator + logFileName);
		commandList.add("-v");
		commandList.add("time");
		if (mTagString != null) {
			commandList.add("-s");
			commandList.add(mTagString);
		}
		try {
			process = Runtime.getRuntime().exec(commandList.toArray(new String[commandList.size()]));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
