package com.socket;

import java.util.Scanner;

public class Client {

	@SuppressWarnings("static-access")
	public void showMenu() throws InstantiationException, IllegalAccessException {
		boolean flage=false;
		 String command=null;
		for(;;)
		{
		System.out.println("��ӭʹ��Socket�ϴ�������");
		System.out.println("\n\n");
		System.out.println("**************");
		System.out.println();
		System.out.println("1  �ϴ��ļ�");
		System.out.println("2  �����ļ�");
		System.out.println("0   �� ��");
		System.out.println();
		System.out.println("***************");
		System.out.print("��ѡ��:");
		Scanner sca=new Scanner(System.in);
		int mark=sca.nextInt();
		switch(mark)
		{
		case 1:command="loadfile";
		       ClientEntity.class.newInstance().loadFile(command);break;
		case 2:command="downfile";
			ClientEntity.class.newInstance().dowanFile(command);break;
		case 0:flage=true;break;
		default: System.out.println("�������������ѡ��");
		}
		if(flage)break;
		}
		System.out.println("****************");
	    System.out.println("  л  л   ʹ     �ã� ");
	    System.out.println("*****************");
	}

}
