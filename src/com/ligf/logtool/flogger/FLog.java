package com.ligf.logtool.flogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ligf.logtool.app.AppConfig;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

/**
 * Log工具类
 * 
 * @author ligangfan
 *
 */
public class FLog {

	/** 是否打印Log日志 */
	private static boolean mLogcatEnable = false;
	/** 是否把日志保存到文件中 */
	private static boolean mLogToFileEnable = false;
	/** Log日志文件的保存路径 */
	private static String mLogFilePath = AppConfig.LOG_SDCARD_PATH + File.separator + "flog.txt";
	private static SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

	public static void i(String tag, String msg) {
		String[] array = getWrappedTagMsg(tag, msg);
		if (mLogcatEnable) {
			Log.i(array[0], array[1]);
		}
		if (mLogToFileEnable) {
			byte[] outbytes = ("[" + array[0] + "]" + array[1]).getBytes();
			logToFile(outbytes, outbytes.length);
		}
	}
	
	public static void d(String tag, String msg) {
		String[] array = getWrappedTagMsg(tag, msg);
		if (mLogcatEnable) {
			Log.d(array[0], array[1]);
		}
		if (mLogToFileEnable) {
			byte[] outbytes = ("[" + array[0] + "]" + array[1]).getBytes();
			logToFile(outbytes, outbytes.length);
		}
	}
	
	public static void e(String tag, String msg) {
		String[] array = getWrappedTagMsg(tag, msg);
		if (mLogcatEnable) {
			Log.e(array[0], array[1]);
		}
		if (mLogToFileEnable) {
			byte[] outbytes = ("[" + array[0] + "]" + array[1]).getBytes();
			logToFile(outbytes, outbytes.length);
		}
	}
	
	public static void v(String tag, String msg) {
		String[] array = getWrappedTagMsg(tag, msg);
		if (mLogcatEnable) {
			Log.v(array[0], array[1]);
		}
		if (mLogToFileEnable) {
			byte[] outbytes = ("[" + array[0] + "]" + array[1]).getBytes();
			logToFile(outbytes, outbytes.length);
		}
	}
	
	public static void w(String tag, String msg) {
		String[] array = getWrappedTagMsg(tag, msg);
		if (mLogcatEnable) {
			Log.w(array[0], array[1]);
		}
		if (mLogToFileEnable) {
			byte[] outbytes = ("[" + array[0] + "]" + array[1]).getBytes();
			logToFile(outbytes, outbytes.length);
		}
	}

	/**
	 * 设置Log文件保存路径（包括文件名称）
	 * 
	 * @param path
	 */
	public static void setLogFilePath(String path) {
		mLogFilePath = path;
	}
	
	/**
	 * 设置是否打印日志
	 * @param logcatEnble
	 */
	public static void setLogcatEnable(boolean logcatEnble){
		mLogcatEnable = logcatEnble;
	}
	
	/**
	 * 设置是否把日志保存到文件中
	 * @param logToFileEnable
	 */
	public static void setLogToFileEnable(boolean logToFileEnable){
		mLogToFileEnable = logToFileEnable;
	}

	/**
	 * 对打印的信息进行包装，添加调用该方法的类，及函数名称，行数
	 * @param tag
	 * @param msg
	 * @return
	 */
	private static String[] getWrappedTagMsg(String tag, String msg) {
		String[] arrayString = new String[2];
		StackTraceElement stackTraceElement = getTargetStackTraceElement();
		arrayString[0] = TextUtils.isEmpty(tag) ? stackTraceElement.getClassName() : tag;
		arrayString[1] = msg + String.format("@%s:%s(%d)", new Object[] { stackTraceElement.getClassName(),
				stackTraceElement.getMethodName(), stackTraceElement.getLineNumber() });
		return arrayString;
	}

	/**
	 * 找到调用Log方法对应方法的StackTraceElement
	 * 
	 * @return
	 */
	private static StackTraceElement getTargetStackTraceElement() {
		StackTraceElement targetStackTraceElement = null;
		boolean shouldTrace = false;
		StackTraceElement[] arrayElement = Thread.currentThread().getStackTrace();
		for (int i = 0; i < arrayElement.length; i++) {
			boolean isLogMethod = arrayElement[i].getClassName().equals(FLog.class.getName());
			if (shouldTrace && !isLogMethod) {
				targetStackTraceElement = arrayElement[i];
				break;
			}
			shouldTrace = isLogMethod;
		}
		return targetStackTraceElement;
	}

	/**
	 * 把日志写到文件中
	 * @param buffer
	 * @param length
	 */
	private static void logToFile(byte[] buffer, int length) {
		synchronized (mLogFilePath) {
			if (!TextUtils.isEmpty(mLogFilePath)) {

				try {
					File logFile = new File(mLogFilePath);
					if (!logFile.exists()) {
						logFile.createNewFile();
					}
					FileOutputStream fileOutputStream = new FileOutputStream(logFile);
					fileOutputStream.write(mDateFormat.format(new Date()).getBytes());
					fileOutputStream.write(buffer, 0, length);
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

}
