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
			//����socket�����ӵ��˿�
			 socket=new Socket("localhost",8888);
		
			 //��ȡ�ļ���·��
			try {
				filePath = FileSource.class.newInstance().getFilePath();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}//�ļ�·����
			String fileName=FileSource.getFileName(filePath);//�ļ���***************
			File file=new File(filePath);//�ļ�����****************
			//��װ�ļ����ݣ��õ�ObjectFile����
			 fis=new FileInputStream(file);
			byte[] fcontent = new byte[fis.available()];//����ļ��ֽ���
			fis.read(fcontent);//aaaaaaaaaaaaaaaaaaaa��ôû��д��һ��ѽ��������ô�ã�55555555
	        FileBean fileBean=new FileBean(fileName, fcontent); 
			ObjectFile objectFile=new ObjectFile(command,fileBean);
	       //��ObjectFile�����뵽�����
		    oos=new ObjectOutputStream(socket.getOutputStream());//��ȡ���������
			oos.writeObject(objectFile);
			oos.flush();
	        //�ӷ���˽��յ���Ӧ�������
			ois=new ObjectInputStream(socket.getInputStream());//��ȡ�������
			requestReplay=(RRNFile) ois.readObject();//�õ��������������Ļ�Ӧ����
			boolean result=requestReplay.isReplay();
			if(result)//���ϴ�������д�������û�
			{
				JOptionPane.showMessageDialog(null, "��ϲ�㣬�ļ��ɹ��ϴ������������ݿ�");
			}else{
				JOptionPane.showMessageDialog(null, "Sorry,�ļ��ϴ�ʧ�ܣ������˳�");
				System.exit(1);
			}	
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			//�ر���Դ
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
			dirPath= FileSource.class.newInstance().getSaveFilePath();//�ļ�����ĵط�
			String fname=JOptionPane.showInputDialog("���������ص��ļ���(demo.txt):");
			FileBean fileBean=new FileBean(fname, null); //��װ�ļ�����
			ObjectFile objectFile=new ObjectFile(command,fileBean);//��װ�������
			//��ObjectFile�����뵽�����
		    oos=new ObjectOutputStream(socket.getOutputStream());//��ȡ���������
			oos.writeObject(objectFile);
			oos.flush();
			
			 //�ӷ���˽��յ���Ӧ�������
			ois=new ObjectInputStream(socket.getInputStream());//��ȡ���������
			requestReplay=(RRNFile) ois.readObject();//�õ��������������Ļ�Ӧ����
			int size=requestReplay.getNumble();
			ArrayList<FileBean> al=requestReplay.getFile();
			
			String simpleFname=fname;
			String[] split=simpleFname.split("\\.");
			String header=split[0];//�ļ���ͷ
			String tear="."+split[1];//�ļ��ĺ�׺��
			dirPath=dirPath+"\\";
			fname=dirPath+fname;//����·���ļ���
			File dfile=new File(fname);
			int version=1;//����ļ�����
			if(size>0)//���ϴ�������д�������û�
			{
		   JOptionPane.showMessageDialog(null, "��ϲ�㣬�ӷ��������ݿ����ҵ�"+size+"���ļ���");
			for(int i=0;i<size;i++)
			{
					if (i == 0)	fos = new FileOutputStream(dfile);
					else fos=new FileOutputStream(dirPath+header+(version++)+tear);
		      FileBean filebean=al.get(i);
		      byte[] fcontext=filebean.getContext();
		      fos.write(fcontext);
			}
		   
			}else{
				JOptionPane.showMessageDialog(null, "Sorry,���������ݿ���û�����ҵ��ļ�"+simpleFname);
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
