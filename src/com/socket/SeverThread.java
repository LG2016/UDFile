package com.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.bean.File;
import com.bean.FileBean;
import com.bean.ObjectFile;
import com.bean.RRNFile;
import com.dao.BaseDao;

public class SeverThread extends Thread {
   Socket socket=null;
   private ObjectInputStream ois=null;//对象的输入流
   private ObjectOutputStream oos=null;//对象的输出流
   private FileBean fileBean=null;//文件业务对象
   private File file=null;//和数据库交互的类
   private ObjectFile objectFile=null;//文件传输对象
   private String command=null;//文件传输命令
   private String fname=null;//文件的名称
   private byte[] fcontext;//文件的字节内容
   private BaseDao basedao=null;//数据库的万能dao
   private RRNFile requestReplay=null;//请求响应的类
   private  ArrayList al=null;//根据文件名返回的File列表
   private ArrayList alfile=null;//将file的列表复制个filebean；
	public SeverThread(Socket socket) {
		this.socket=socket;
	}
	@Override
		public void run() {
			try {
			//获得对象输入流
		 ois=new ObjectInputStream(socket.getInputStream());//获得对象输入流
		 objectFile=(ObjectFile) ois.readObject();	
		 command=objectFile.getCommand();//获得命令
		 if(command.equals("loadfile"))//上传文件
		 {
	     fileBean=(FileBean) objectFile.getContent();//获得文件对象
	     fname=fileBean.getFname();//获得文件名称
	     fcontext=fileBean.getContext();//获得文件的字节内容  
	     file=new File();//欸，记住实例化啊
	     file.setFname(fname);//将filebean中的文件名称给file
	     file.setContext(fcontext);//将filebean中的文件内容给file
	     basedao=new BaseDao();//获取万能的Dao
	     requestReplay=new RRNFile();
	     requestReplay.setReplay(basedao.insert(file)) ;//文件插入sql数据库
	     oos=new ObjectOutputStream(socket.getOutputStream());//获得对象输出流
	     oos.writeObject(requestReplay);
	     oos.flush();
		 }else
		 {//下载文件
			 fileBean=(FileBean) objectFile.getContent();//获得文件对象
		     fname=fileBean.getFname();//获得文件名称
		     basedao=new BaseDao();//获取万能的Dao
		    al=basedao.getListBySome(File.class,"fname", fname);//从数据库里面根据文件名程序文件
		    int length=al.size();
		    requestReplay=new RRNFile();
		    if(length==0)requestReplay.setReplay(false);//没有查询到文件
		    requestReplay.setNumble(length);//设置文件的个数
            alfile=new ArrayList();
		    for(int i=0;i<length;i++)//file列表复制给filebean列表
		    {
		    	
		    	
		    	file=(File) al.get(i);//file对象——》fileBean;
		    	fileBean=new FileBean(file.getFid(), file.getFname(), file.getContext());
		    	alfile.add(fileBean);
		    }
		    requestReplay.setFile(alfile);
		    oos=new ObjectOutputStream(socket.getOutputStream());//获得对象输出流
		    oos.writeObject(requestReplay);
		    oos.flush();
		 }
	     
	     
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally{
				
					try {
				if (ois != null)ois.close();
				if(oos!=null)oos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
   
}
