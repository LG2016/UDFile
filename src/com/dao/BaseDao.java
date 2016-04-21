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
    public boolean insert(Object obj)//根据对象向数据库里面插入数据//###################功能一
	{
		boolean mark=false;//返回值
		Connection conn=BaseConnection.getConnection();//获得数据库连接
		PreparedStatement ps=null;
		Class c1=obj.getClass();//获得运行时类
		Field[] fields=c1.getDeclaredFields();//获得属性名
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
			ps=conn.prepareStatement(sb.toString());//预编译sql语句
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
    public ArrayList getListBySome(Class c1,String name,Object value)//根据某一条件查询//#######功能二
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
    				//异常抛出是下面一行语句
    				field.set(obj,rs.getObject(field.getName()));
    				//异常抛出是上面一条语句
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
public ArrayList getList(Class c1)//根据表的名字返回表对象
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
public Object getObById(Class c1,int id)//根据表的名字和主键id返回对应记录的对象
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

public boolean updata(Object obj )//根据对象修改数据库的数据
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
public boolean delete(Class c1,int id)//根据表的主键删除数据库数据
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
