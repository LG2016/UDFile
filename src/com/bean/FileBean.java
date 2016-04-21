package com.bean;

import java.io.Serializable;


public class FileBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private int fid;
	private String fname;
	byte[] context;
	
	public FileBean(){};
	
	public FileBean( int fid , String fname, byte[] context) {
		this.fid=fid;

		this.fname = fname;
		this.context = context;
	}
	
	public FileBean(  String fname, byte[] context) {
		this.fname = fname;
		this.context = context;
	}
	
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
	}
	

}
