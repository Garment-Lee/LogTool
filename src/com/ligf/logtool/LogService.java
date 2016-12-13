package com.ligf.logtool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ligf.logtool.task.LogcatTabInfoTask;
import com.ligf.logtool.task.RecordCpuInfoTask;
import com.ligf.logtool.task.RecordMemInfoTask;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

public class LogService extends Service {

	private static final String TAG = "LogService";
	/** 保存文件的sd卡的目录 */
	private String SERVICE_LOG_FILE_NAME = "logservice.log";
	private String MEMORY_LOG_FILE_NAME = "memory.log";
	private OutputStreamWriter mServiceLogWriter = null;
	private OutputStreamWriter mMemoryInfoWriter = null;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	private SimpleDateFormat mFileDateFormate = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
	private Process mLogProcess = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
//		new RecordMemInfoTask().start();
		new RecordCpuInfoTask().start();
		new LogcatTabInfoTask("Garment").start();
	}

	private void init() {
		createLogDir();
	}

	/**
	 * 创建存储Log信息的文件目录
	 */
	private void createLogDir() {
		Log.i("Garment1213", "LogService init path:" + AppConfig.LOG_SDCARD_PATH);
		File file = new File(AppConfig.LOG_SDCARD_PATH);
		if (!file.isDirectory()) {
			boolean ok = file.mkdirs();
			if (!ok) {
				ok = file.mkdirs();
			}
		}
	}

	/**
	 * 清除前面的Log信息
	 */
//	private void clearLogCache() {
//		Process process = null;
//		List<String> commandList = new ArrayList<String>();
//		commandList.add("logcat");
//		commandList.add("-c");
//		try {
//			process = Runtime.getRuntime().exec(commandList.toArray(new String[commandList.size()]));
//			StreamConsumer errorStream = new StreamConsumer(process.getErrorStream());
//			StreamConsumer outputStream = new StreamConsumer(process.getInputStream());
//			errorStream.start();
//			outputStream.start();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			recordServiceLog("fail to clear the log cache");
//		} finally {
//			process.destroy();
//		}
//	}
//
//	/**
//	 * 记录LogService中出现的错误
//	 * 
//	 * @param msg
//	 */
//	private void recordServiceLog(String msg) {
//		if (mServiceLogWriter != null) {
//			Date time = new Date();
//			try {
//				mServiceLogWriter.write(mDateFormat.format(time) + "" + msg);
//				mServiceLogWriter.write("\n");
//				mServiceLogWriter.flush();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				recordServiceLog("fail to record service log");
//			}
//		}
//	}
//
//	/**
//	 * 获取当前运行的所有的进程的信息
//	 * 
//	 * @return
//	 */
//	private List<String> getAllProcess() {
//		List<String> allProcessList = new ArrayList<String>();
//		Process process = null;
//		try {
//			process = Runtime.getRuntime().exec("ps");
//			StreamConsumer outputStream = new StreamConsumer(process.getInputStream(), allProcessList);
//			outputStream.start();
//			if (process.waitFor() != 0) {
//
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			recordServiceLog("fail to get all process");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			process.destroy();
//		}
//		Log.i("Garment0812", "getAllProcess allProcessList.size:" + allProcessList.size());
//
//		return allProcessList;
//	}
//
//	/**
//	 * 把进程信息封装成ProcessInfo对象
//	 * 
//	 * @param processList
//	 * @return
//	 */
//	private List<ProcessInfo> getAllProcessInfo(List<String> processList) {
//		List<ProcessInfo> processInfos = new ArrayList<ProcessInfo>();
//		for (int i = 0; i < processInfos.size(); i++) {
//			String processInfoStr = processList.get(i);
//			String[] processInfoArr = processInfoStr.split(" ");
//			List<String> origInfos = new ArrayList<String>();
//			for (String info : processInfoArr) {
//				if (!info.equals("")) {
//					origInfos.add(info);
//				}
//			}
//			if (origInfos.size() == 9) {
//				ProcessInfo processInfo = new ProcessInfo();
//				processInfo.mUser = origInfos.get(0);
//				processInfo.mPid = origInfos.get(1);
//				processInfo.mPPid = origInfos.get(2);
//				processInfo.mName = origInfos.get(8);
//			}
//		}
//		Log.i("Garment0812", "getAllProcessInfo processInfos.size:" + processInfos.size());
//		return processInfos;
//	}
//
//	int count = 0;
//
//	/**
//	 * 杀掉Logcat相关进程，防止多个进程同时读写Log信息
//	 * 
//	 * @param processInfos
//	 */
//	private void killLogcatProcess(List<ProcessInfo> processInfos) {
//		String packageName = this.getPackageName();
//		String myUser = getAppUser(packageName, processInfos);
//		for (ProcessInfo processInfo : processInfos) {
//			if (processInfo.mName.toLowerCase().equals("logcat") && processInfo.mUser.equals(myUser)) {
//				count++;
//				Log.i("Garment0812", "killLogcatProcess count:" + count);
//				android.os.Process.killProcess(Integer.parseInt(processInfo.mPid));
//			}
//		}
//	}
//
//	/**
//	 * 获取当前进程的User属性
//	 * 
//	 * @param packageName
//	 * @param processInfos
//	 * @return
//	 */
//	private String getAppUser(String packageName, List<ProcessInfo> processInfos) {
//		for (ProcessInfo processInfo : processInfos) {
//			if (packageName.equals(processInfo.mUser)) {
//				return processInfo.mUser;
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 开始进行Log信息的记录，记录的是logcat的相关信息
//	 */
//	private void startToRecordLogcatMsg() {
//		if (mLogProcess != null) {
//			mLogProcess.destroy();
//		}
//		String logFileName = mFileDateFormate.format(new Date()) + ".log";
//		List<String> commandList = new ArrayList<String>();
//		commandList.add("logcat");
//		commandList.add("-f");
//		commandList.add(AppConfig.LOG_SDCARD_PATH + File.separator + logFileName);
//		commandList.add("-v");
//		commandList.add("time");
//		commandList.add("-s");
//		commandList.add("GarmentLog");
//		// commandList.add("*:I");
//		try {
//			mLogProcess = Runtime.getRuntime().exec(commandList.toArray(new String[commandList.size()]));
//			recordServiceLog("start to record logcat message");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			recordServiceLog("record logcat message fail");
//		}
//	}
//
//	/**
//	 * 文件的复制
//	 * 
//	 * @param source
//	 * @param target
//	 * @return
//	 */
//	private boolean copyFile(File source, File target) {
//		FileInputStream fileInputStream = null;
//		FileOutputStream fileOutputStream = null;
//		try {
//			if (!target.exists()) {
//
//				boolean success = target.createNewFile();
//				if (!success) {
//					return false;
//				}
//			}
//			fileInputStream = new FileInputStream(source);
//			fileOutputStream = new FileOutputStream(target);
//			byte[] buffer = new byte[8 * 1024];  //8k
//			int length = 0;
//			while ((length = fileInputStream.read(buffer)) != -1) {
//				fileOutputStream.write(buffer, 0, length);
//			}
//			return true;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		} finally {
//			try {
//				if (fileInputStream != null) {
//					fileInputStream.close();
//				}
//				if (fileOutputStream != null) {
//					fileOutputStream.close();
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//
//	/**
//	 * 启动Log信息记录的线程（先杀掉Logcat相关进程，再进行记录）
//	 * 
//	 * @author ligangfan
//	 *
//	 */
//	class LogCollectThread extends Thread {
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			super.run();
//			clearLogCache();
//			List<ProcessInfo> processInfosList = getAllProcessInfo(getAllProcess());
//			killLogcatProcess(processInfosList);
//			startToRecordLogcatMsg();
//		}
//	}
//
//	/**
//	 * 读取InputStream的封装类，读取的信息保存到List<String>对象中
//	 * 
//	 * @author ligangfan
//	 *
//	 */
//	class StreamConsumer extends Thread {
//
//		List<String> list = null;
//		InputStream inStream = null;
//
//		public StreamConsumer(InputStream inputStream, List<String> list) {
//			// TODO Auto-generated constructor stub
//			this.list = list;
//			this.inStream = inputStream;
//		}
//
//		public StreamConsumer(InputStream inputStream) {
//			this.inStream = inputStream;
//		}
//
//		public void run() {
//			// TODO Auto-generated method stub
//			InputStreamReader inputStreamReader = new InputStreamReader(inStream);
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//			String line = null;
//			if (list != null) {
//				try {
//					while ((line = bufferedReader.readLine()) != null) {
//						Log.i("Garment0812", "line:" + line);
//						list.add(line);
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					recordServiceLog("fail to read the inputsream");
//				}
//			}
//		}
//	}
//
//	/**
//	 * 读取系统内存信息的线程，
//	 * 
//	 * @author ligangfan
//	 *
//	 */
//	class LogcatThread extends Thread {
//
//		Process process;
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			super.run();
//
//			try {
//				process = Runtime.getRuntime().exec("dumpsys meminfo");
//				InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
//				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//				while (true) {
//					String line = null;
//					mMemoryInfoWriter.write(mDateFormat.format(new Date()));
//					mMemoryInfoWriter.write("\n");
//					while ((line = bufferedReader.readLine()) != null) {
//						if (mMemoryInfoWriter != null) {
//							mMemoryInfoWriter.write(line);
//							mMemoryInfoWriter.write("\n");
//						}
//						mMemoryInfoWriter.write("===============================================================");
//						mMemoryInfoWriter.write("\n");
//						mMemoryInfoWriter.flush();
//						Thread.sleep(5000);
//					}
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				// TODO: handle exception
//			} finally {
//				process.destroy();
//			}
//		}
//	}

}
