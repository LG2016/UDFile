package com.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class RRNFile implements Serializable {
	/*
	 * �����ļ�����ѯ�ļ������ظ��ͻ��˵��ļ��������ļ��ķ�װ��
	 */
	private static final long serialVersionUID = 1L;
	private int numble;//�ļ��ĸ���
	private ArrayList file;//�ļ�
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
