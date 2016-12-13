package com.ligf.logtool;

import java.io.File;

import android.os.Environment;

public class AppConfig {

	public static final String LOG_SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LogInfo";
	public static final String MEMORY_LOG_FILE_NAME = "memory.log";
	public static final String CPU_LOG_FILE_NAME = "cpu.log";
	
}
