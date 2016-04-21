package com.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.bean.FileBean;
import com.bean.ObjectFile;
import com.bean.RRNFile;


public class ClientEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	static Socket socket=null;
	static FileInputStream fis=null;
	static FileOutputStream fos=null;
	static ObjectOutputStream oos=null;
	static ObjectInputStream ois=null;
	static String filePath=null;
	static String dirPath=null;
	 static FileChannel in=null;
	   static FileChannel out=null;
	private static RRNFile requestReplay=null;
	@SuppressWarnings("static-access")
	public static void loadFile(String command) {
		try {
			//创建socket，连接到端口
			 socket=new Socket("localhost",8888);
		
			 //获取文件的路径
			try {
				filePath = FileSource.class.newInstance().getFilePath();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}//文件路径名
			String fileName=FileSource.getFileName(filePath);//文件名***************
			File file=new File(filePath);//文件对象****************
			//封装文件内容，得到ObjectFile对象
			 fis=new FileInputStream(file);
			byte[] fcontent = new byte[fis.available()];//或得文件字节流
			fis.read(fcontent);//aaaaaaaaaaaaaaaaaaaa怎么没有写这一句呀，查了那么久，55555555
	        FileBean fileBean=new FileBean(fileName, fcontent); 
			ObjectFile objectFile=new ObjectFile(command,fileBean);
	       //将ObjectFile对象传入到服务端
		    oos=new ObjectOutputStream(socket.getOutputStream());//获取对象输出流
			oos.writeObject(objectFile);
			oos.flush();
	        //从服务端接收到回应请求对象
			ois=new ObjectInputStream(socket.getInputStream());//获取对象输出
			requestReplay=(RRNFile) ois.readObject();//得到服务器传过来的回应对象
			boolean result=requestReplay.isReplay();
			if(result)//对上传结果进行处理告诉用户
			{
				JOptionPane.showMessageDialog(null, "恭喜你，文件成功上传到服务器数据库");
			}else{
				JOptionPane.showMessageDialog(null, "Sorry,文件上传失败，程序退出");
				System.exit(1);
			}	
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			//关闭资源
			try {
				if(fis!=null)fis.close();
				if(oos!=null)oos.close();
				if(ois!=null)ois.close();
				if(socket!=null)socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	@SuppressWarnings("static-access")
	public static void dowanFile(String command) {
		 try {
			socket=new Socket("localhost",8888);
			dirPath= FileSource.class.newInstance().getSaveFilePath();//文件保存的地方
			String fname=JOptionPane.showInputDialog("请输入下载的文件名(demo.txt):");
			FileBean fileBean=new FileBean(fname, null); //封装文件对象
			ObjectFile objectFile=new ObjectFile(command,fileBean);//封装传输对象
			//将ObjectFile对象传入到服务端
		    oos=new ObjectOutputStream(socket.getOutputStream());//获取对象输出流
			oos.writeObject(objectFile);
			oos.flush();
			
			 //从服务端接收到回应请求对象
			ois=new ObjectInputStream(socket.getInputStream());//获取对象输出流
			requestReplay=(RRNFile) ois.readObject();//得到服务器传过来的回应对象
			int size=requestReplay.getNumble();
			ArrayList<FileBean> al=requestReplay.getFile();
			
			String simpleFname=fname;
			String[] split=simpleFname.split("\\.");
			String header=split[0];//文件的头
			String tear="."+split[1];//文件的后缀名
			dirPath=dirPath+"\\";
			fname=dirPath+fname;//绝对路径文件名
			File dfile=new File(fname);
			int version=1;//多个文件下载
			if(size>0)//对上传结果进行处理告诉用户
			{
		   JOptionPane.showMessageDialog(null, "恭喜你，从服务器数据库中找到"+size+"个文件！");
			for(int i=0;i<size;i++)
			{
					if (i == 0)	fos = new FileOutputStream(dfile);
					else fos=new FileOutputStream(dirPath+header+(version++)+tear);
		      FileBean filebean=al.get(i);
		      byte[] fcontext=filebean.getContext();
		      fos.write(fcontext);
			}
		   
			}else{
				JOptionPane.showMessageDialog(null, "Sorry,服务器数据库中没有你找的文件"+simpleFname);
			}	
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
				try {
					if(fos!=null)fos.close();
					if(ois!=null)ois.close();
					if(socket!=null)socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}

}
