package com.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class RRNFile implements Serializable {
	/*
	 * 根据文件名查询文件，返回给客户端的文件个数和文件的封装类
	 */
	private static final long serialVersionUID = 1L;
	private int numble;//文件的个数
	private ArrayList file;//文件
	private boolean request;
	private boolean replay;
	
	
	
	
	public void setNumble(int numble)
	{
		this.numble=numble;
	}
	public int getNumble()
	{
		return numble;
	}
	public boolean isRequest() {
		return request;
	}
	public void setRequest(boolean request) {
		this.request = request;
	}
	public boolean isReplay() {
		return replay;
	}
	public void setReplay(boolean replay) {
		this.replay = replay;
	}
	public ArrayList getFile() {
		return file;
	}
	public void setFile(ArrayList al) {
		this.file = al;
	}

}
