package com.socket;

import java.util.Scanner;

public class Client {

	@SuppressWarnings("static-access")
	public void showMenu() throws InstantiationException, IllegalAccessException {
		boolean flage=false;
		 String command=null;
		for(;;)
		{
		System.out.println("欢迎使用Socket上传下载器");
		System.out.println("\n\n");
		System.out.println("**************");
		System.out.println();
		System.out.println("1  上传文件");
		System.out.println("2  下载文件");
		System.out.println("0   退 出");
		System.out.println();
		System.out.println("***************");
		System.out.print("请选择:");
		Scanner sca=new Scanner(System.in);
		int mark=sca.nextInt();
		switch(mark)
		{
		case 1:command="loadfile";
		       ClientEntity.class.newInstance().loadFile(command);break;
		case 2:command="downfile";
			ClientEntity.class.newInstance().dowanFile(command);break;
		case 0:flage=true;break;
		default: System.out.println("输入错误，请重新选择！");
		}
		if(flage)break;
		}
		System.out.println("****************");
	    System.out.println("  谢  谢   使     用！ ");
	    System.out.println("*****************");
	}

}
