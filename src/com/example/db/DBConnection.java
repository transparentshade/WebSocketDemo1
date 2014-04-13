package com.example.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static  Connection con;
	private static String url = "jdbc:mysql://localhost:3306/canvasdb";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String username = "root";
	private static String password = "root";
	private static String dbName = "canvas";
	public static synchronized Connection  getConnection() {
		if(con==null) {
			try {
				Class.forName(driver).newInstance();
				con = DriverManager.getConnection(url, username, password);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return con;
	}
}