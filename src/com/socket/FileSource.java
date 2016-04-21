package com.socket;

import javax.swing.JFileChooser;

public class FileSource {
   public static String getSaveFilePath()//��������ȡ�ļ������λ��
   {
	   JFileChooser chooser = new javax.swing.JFileChooser();
	   chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
	   int returnVal = chooser.showOpenDialog(null);
	   String directory=null;
	   if(returnVal == JFileChooser.APPROVE_OPTION) {
	   directory= chooser.getSelectedFile().getPath();
   }  
	   return directory;
   }
	
	public static String getFilePath() {//��������ȡ�ļ��ľ���·��
		  JFileChooser fileChooser=new JFileChooser("D:\\");
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	       int rv= fileChooser.showOpenDialog(fileChooser);
	       String filePath=null;
	       if(rv==JFileChooser.APPROVE_OPTION)
	       {
	    	 filePath =fileChooser.getSelectedFile().getAbsolutePath();
	       }
		return filePath;
	}

	public static String getFileName(String filePath) {//��ȡ�ļ�������ļ���
		String pattern="\\\\";
		String[] fn=filePath.split(pattern);
		int n=fn.length-1;
		return fn[n];
   	}

}
