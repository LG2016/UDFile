package com.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;





public class SocketSever {
	
public static void main(String[] args) {
	try {
		ServerSocket severSocket=new ServerSocket(8888);
		JOptionPane.showMessageDialog(null, "���������ڵȴ��ͻ�������");
		for(;;)
		{
		Socket socket=severSocket.accept();
		SeverThread severThread=new SeverThread(socket);
		severThread.start();
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}
