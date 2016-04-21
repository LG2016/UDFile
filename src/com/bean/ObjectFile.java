package com.bean;

import java.io.Serializable;

public class ObjectFile implements Serializable {

	private static final long serialVersionUID = 1L;
     private String command;
     private Object content;
     
     
     
	public ObjectFile(String command, Object content) {
		this.command = command;
		this.content = content;
	}
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
     
     
}
