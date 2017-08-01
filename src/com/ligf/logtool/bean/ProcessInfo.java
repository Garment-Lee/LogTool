package com.ligf.logtool.bean;

public class ProcessInfo {

	public String mUser = null;
	public String mPid = null;
	public String mPPid = null;
	public String mName = null;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "user = " + mUser + " pid = " + mPid + " ppid = " + mPPid + " name = " + mName;
	}
}
