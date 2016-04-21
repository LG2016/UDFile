package com.bean;

public class File {//FileBean
	private int fid;
	private String fname;
	byte[] context;
	public File(){}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public byte[] getContext() {
		return context;
	}
	public void setContext(byte[] context) {
		this.context = context;
	};
	
}
