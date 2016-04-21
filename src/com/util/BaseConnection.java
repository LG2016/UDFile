package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseConnection {
public static Connection getConnection()//获得数据库的连接
{
	Connection conn=null;
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/network","root","rootmysql");
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}
	return conn;
}
public static void closeRes(Connection conn,PreparedStatement ps)//关闭两个参数资源的方法
{
	try {
	if(conn!=null) conn.close();
	if(ps!=null)ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
public static void closeRes(Connection conn,PreparedStatement ps,ResultSet rs)//关闭三个参数资源的方法
{
	try {
	if(rs!=null)rs.close();
	if(conn!=null) conn.close();
	if(ps!=null)ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
}
