package com.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.websocket.Session;

import com.example.bean.User;


public class UserHelper {
	private static Connection con;
	public static boolean createUser(String username,String password, String emailId,Session session){
		con = DBConnection.getConnection();
		try {
			PreparedStatement stmt = con.prepareStatement("insert into user(username,password,email_id,session) values (?,?,?,?)");
			stmt.setString(1,username);
			stmt.setString(2, password);
			stmt.setString(3, emailId);
			stmt.setString(4, null);
			return stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	public static User getUserById(int userid) {
		con = DBConnection.getConnection();
		try {
			PreparedStatement  stmt = con.prepareStatement("select id,username,fname,lname,password,email_id from user where id = ?");
			stmt.setInt(1, userid);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setPassword(rs.getString("password"));
				u.setEmailId(rs.getString("email_id"));
				return u;
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static User getUser(String username) {
		con = DBConnection.getConnection();
		try {
			PreparedStatement  stmt = con.prepareStatement("select id,username,fname,lname,password,email_id from user where username = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setFname(rs.getString("fname"));
				u.setLname(rs.getString("lname"));
				u.setPassword(rs.getString("password"));
				u.setEmailId(rs.getString("email_id"));
				return u;
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
