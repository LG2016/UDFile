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
   private ObjectInputStream ois=null;//�����������
   private ObjectOutputStream oos=null;//����������
   private FileBean fileBean=null;//�ļ�ҵ�����
   private File file=null;//�����ݿ⽻������
   private ObjectFile objectFile=null;//�ļ��������
   private String command=null;//�ļ���������
   private String fname=null;//�ļ�������
   private byte[] fcontext;//�ļ����ֽ�����
   private BaseDao basedao=null;//���ݿ������dao
   private RRNFile requestReplay=null;//������Ӧ����
   private  ArrayList al=null;//�����ļ������ص�File�б�
   private ArrayList alfile=null;//��file���б��Ƹ�filebean��
	public SeverThread(Socket socket) {
		this.socket=socket;
	}
	@Override
		public void run() {
			try {
			//��ö���������
		 ois=new ObjectInputStream(socket.getInputStream());//��ö���������
		 objectFile=(ObjectFile) ois.readObject();	
		 command=objectFile.getCommand();//�������
		 if(command.equals("loadfile"))//�ϴ��ļ�
		 {
	     fileBean=(FileBean) objectFile.getContent();//����ļ�����
	     fname=fileBean.getFname();//����ļ�����
	     fcontext=fileBean.getContext();//����ļ����ֽ�����  
	     file=new File();//�G����סʵ������
	     file.setFname(fname);//��filebean�е��ļ����Ƹ�file
	     file.setContext(fcontext);//��filebean�е��ļ����ݸ�file
	     basedao=new BaseDao();//��ȡ���ܵ�Dao
	     requestReplay=new RRNFile();
	     requestReplay.setReplay(basedao.insert(file)) ;//�ļ�����sql���ݿ�
	     oos=new ObjectOutputStream(socket.getOutputStream());//��ö��������
	     oos.writeObject(requestReplay);
	     oos.flush();
		 }else
		 {//�����ļ�
			 fileBean=(FileBean) objectFile.getContent();//����ļ�����
		     fname=fileBean.getFname();//����ļ�����
		     basedao=new BaseDao();//��ȡ���ܵ�Dao
		    al=basedao.getListBySome(File.class,"fname", fname);//�����ݿ���������ļ��������ļ�
		    int length=al.size();
		    requestReplay=new RRNFile();
		    if(length==0)requestReplay.setReplay(false);//û�в�ѯ���ļ�
		    requestReplay.setNumble(length);//�����ļ��ĸ���
            alfile=new ArrayList();
		    for(int i=0;i<length;i++)//file�б��Ƹ�filebean�б�
		    {
		    	
		    	
		    	file=(File) al.get(i);//file���󡪡���fileBean;
		    	fileBean=new FileBean(file.getFid(), file.getFname(), file.getContext());
		    	alfile.add(fileBean);
		    }
		    requestReplay.setFile(alfile);
		    oos=new ObjectOutputStream(socket.getOutputStream());//��ö��������
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
