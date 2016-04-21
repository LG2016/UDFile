package com.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.BaseConnection;

public class BaseDao {
	//*************************************
    public boolean insert(Object obj)//���ݶ��������ݿ������������//###################����һ
	{
		boolean mark=false;//����ֵ
		Connection conn=BaseConnection.getConnection();//������ݿ�����
		PreparedStatement ps=null;
		Class c1=obj.getClass();//�������ʱ��
		Field[] fields=c1.getDeclaredFields();//���������
		StringBuffer sb=new StringBuffer();
		//insert into FileBean(fid,fname,context)values(?,?,?);
		sb.append("insert into "+c1.getSimpleName()+" (");
		for(int i=1;i<fields.length;i++)
		{
			Field field=fields[i];
			field.setAccessible(true);
			sb.append(field.getName());
			if(i!=fields.length-1)sb.append(",");
			
		}
		sb.append(")values(");
		for(int i=1;i<fields.length;i++)
		{
			sb.append("?");
			if(i!=fields.length-1)sb.append(",");
			
		}
	sb.append(")");
		try {
			ps=conn.prepareStatement(sb.toString());//Ԥ����sql���
			for(int i=1;i<fields.length;i++)
			{
				Field field=fields[i];
				ps.setObject(i, field.get(obj));
			}
			int a=ps.executeUpdate();
			if(a>0)mark=true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			BaseConnection.closeRes(conn, ps);
		}
		return mark;
	}
    public ArrayList getListBySome(Class c1,String name,Object value)//����ĳһ������ѯ//#######���ܶ�
    {
    	ArrayList al=new ArrayList();
    	Connection conn=BaseConnection.getConnection();
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	String sql="select * from "+c1.getSimpleName()+" where "+name+" =  '"+value+"'";
    	Field[] fields=c1.getDeclaredFields();
    	try {
    		ps=conn.prepareStatement(sql);
    		rs=ps.executeQuery();
    		while(rs.next())
    		{
    			Object obj=c1.newInstance();
    			/*for(int i=0;i<fields.length;i++)
    			{
    				Field field=fields[i];
    				field.setAccessible(true);
    				field.set(obj, rs.getObject(field.getName()));
    				System.out.println("aaaaaa");
    			}*/
    			for(Field field:fields)
    			{
    				field.setAccessible(true);
    				//�쳣�׳�������һ�����
    				field.set(obj,rs.getObject(field.getName()));
    				//�쳣�׳�������һ�����
    			}
    		//**********************
    			al.add(obj);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}finally
    	{
    		BaseConnection.closeRes(conn, ps, rs);
    	}
    	return al;
    }

    //*************************************
public ArrayList getList(Class c1)//���ݱ�����ַ��ر����
{
	ArrayList al=new ArrayList();
	Connection conn=BaseConnection.getConnection();
	PreparedStatement ps=null;
	ResultSet rs=null;
	String sql="select * from "+c1.getSimpleName();
	try {
		ps=conn.prepareStatement(sql);
		rs=ps.executeQuery();
		Field[] fields=c1.getDeclaredFields();
		while(rs.next())
		{
			Object obj=c1.newInstance();
		    for(int i=0;i<fields.length-1;i++)
			{
		    Field field=fields[i];
			field.setAccessible(true);
			field.set(obj, rs.getObject(field.getName()));
			}
			al.add(obj);
		}
	} catch (Exception e) {
	e.printStackTrace();
	}finally
	{
		BaseConnection.closeRes(conn, ps, rs);
	}
	 return al;
	
}
public Object getObById(Class c1,int id)//���ݱ�����ֺ�����id���ض�Ӧ��¼�Ķ���
{
	Object obj=null;
	Connection conn=BaseConnection.getConnection();
	PreparedStatement  ps=null;
	ResultSet rs=null;
	Field[] fields=c1.getDeclaredFields();
	String sql="select * from "+c1.getSimpleName()+" where "+fields[0].getName()+"="+id;
	try {
		ps=conn.prepareStatement(sql);
		rs=ps.executeQuery();
		while(rs.next())
		{
			obj=c1.newInstance();
			for(int i=0;i<fields.length-1;i++)
			{
				Field field=fields[i];
				field.setAccessible(true);
				field.set(obj,rs.getObject(field.getName()));
			}
			
		}
	
	} catch (SQLException | InstantiationException | IllegalAccessException e) {
		e.printStackTrace();
	}finally
	{
		BaseConnection.closeRes(conn, ps, rs);
	}
	return obj;
}

public boolean updata(Object obj )//���ݶ����޸����ݿ������
{
	boolean mark=false;
	Connection conn=BaseConnection.getConnection();
	PreparedStatement ps=null;
  //update file set fid=?,name=?,fname=?,context=? where id=?;
	StringBuffer sb=new StringBuffer();
	Class c1=obj.getClass();
	sb.append("update ");
	sb.append(c1.getSimpleName());
	sb.append(" set ");
	Field[] fields=c1.getDeclaredFields();
	for(int i=1;i<fields.length-1;i++)
	{
		Field field=fields[i];
		sb.append(field.getName());
		sb.append(" =? ");
		if(i!=fields.length-2)sb.append(" , ");	
	}
	sb.append(" where ");
	sb.append(fields[0].getName());
	sb.append("=?");
	try {
		ps=conn.prepareStatement(sb.toString());
		for(int i=1;i<fields.length-1;i++)
		{
			Field field=fields[i];
			field.setAccessible(true);
			ps.setObject(i,field.get(obj) );	
		}
		   fields[0].setAccessible(true);
		   ps.setObject(fields.length-1, fields[0].get(obj));
		int a=ps.executeUpdate();
		if(a>0)mark=true;
	} catch (Exception e) {
		e.printStackTrace();
	}finally
	{
		BaseConnection.closeRes(conn, ps);
	}
	return mark;
}
public boolean delete(Class c1,int id)//���ݱ������ɾ�����ݿ�����
{
	boolean mark=false;
	Connection conn=BaseConnection.getConnection();
	PreparedStatement ps=null;
	Field[] fields=c1.getDeclaredFields();
	String sql="delete from "+c1.getSimpleName()+" where "+fields[0].getName()+" =?";
	try {
		ps=conn.prepareStatement(sql);
		ps.setObject(1, id);
		int a=ps.executeUpdate();
		if(a>0)mark=true;
	} catch (SQLException e) {
		e.printStackTrace();
	}finally
	{
		BaseConnection.closeRes(conn, ps);
	}
	return mark;
}
}
