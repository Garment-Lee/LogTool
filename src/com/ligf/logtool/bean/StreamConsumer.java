package com.ligf.logtool.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 读取InputStream的工具类
 * @author ligangfan
 *
 */
public class StreamConsumer extends Thread{

	private List<String> mList = null;
	private InputStream mInputStream = null;
	
	public StreamConsumer(InputStream inputStream, List<String> list){
		this.mList = list;
		mInputStream = inputStream;
	}
	
	public StreamConsumer(InputStream inputStream) {
		// TODO Auto-generated constructor stub
		mInputStream = inputStream;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		InputStreamReader inputStreamReader = new InputStreamReader(mInputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = null;
		if (mList != null) {
			try {
				if ((line = bufferedReader.readLine()) != null) {
					mList.add(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
