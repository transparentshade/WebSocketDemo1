package com.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.websocket.Session;

import com.example.bean.Canvas;
import com.example.bean.User;
import com.example.util.CanvasListMessage;
import com.mysql.jdbc.Statement;

public class CanvasHelper {
	private static Connection con;
	public static int createCanvasReturnId(String cname,String password, int creatorId,int editorId,int groupId,
			Date dateCreated,Boolean isValid,String data){
		con = DBConnection.getConnection();
		try {
			/*PreparedStatement st = con.prepareStatement("insert into canvas ( name,password,creator_id,editor_id"
					+ ",date_created,is_valid,data )values ( ?, ?, ?, ?, ?, ?, ?, )");
			st.setString(1, cname);
			st.setString(2, password);
			st.setInt(3, creatorId);
			st.setInt(4, editorId);
			st.setDate(5, new java.sql.Date(dateCreated.getTime()));
			st.setBoolean(6, isValid);
			st.setString(7, data);*/
			StringBuilder sb = new StringBuilder();
			sb.append("insert into canvas ( name,password,creator_id,editor_id,group_id,date_created,is_valid,data )values ");
			sb.append("('"+cname+"',");
			sb.append("'"+password+"',");
			sb.append(""+creatorId+",");
			sb.append(""+editorId+",");
			sb.append(groupId+",");
			sb.append("null,");
			sb.append(isValid+",");
			sb.append("'"+data+"'");
			sb.append(")");
			System.out.println(sb.toString());
			java.sql.Statement st = con.createStatement();
			 st.executeUpdate(sb.toString(),Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);//to return created id
			}
			else {
				
				//something went wrong..
				return -1;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
		
	}
	public static int createCanvasReturnId(Canvas c){
		con = DBConnection.getConnection();
		try {
			/*PreparedStatement st = con.prepareStatement("insert into canvas ( name,password,creator_id,editor_id"
					+ ",date_created,is_valid,data )values ( ?, ?, ?, ?, ?, ?, ?, )");
			st.setString(1, cname);
			st.setString(2, password);
			st.setInt(3, creatorId);
			st.setInt(4, editorId);
			st.setDate(5, new java.sql.Date(dateCreated.getTime()));
			st.setBoolean(6, isValid);
			st.setString(7, data);*/
			StringBuilder sb = new StringBuilder();
			sb.append("insert into canvas ( name,password,creator_id,editor_id,group_id,date_created,is_valid,data )values ");
			sb.append("('"+c.getName()+"',");
			sb.append("'"+c.getPassword()+"',");
			sb.append(""+c.getCreator().getId()+",");
			sb.append(""+c.getEditor().getId()+",");
			sb.append(c.getGroup().getId()+",");
			sb.append("null,");
			sb.append(c.isValid()+",");
			sb.append("'"+c.getData()+"'");
			sb.append(")");
			System.out.println(sb.toString());
			java.sql.Statement st = con.createStatement();
			 st.executeUpdate(sb.toString(),Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);//to return created id
			}
			else {
				
				//something went wrong..
				return -1;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
		
	}
	public static Canvas getCanvas(int canvasId) {
		con = DBConnection.getConnection();
		try {
			PreparedStatement  stmt = con.prepareStatement("select id,name,password,creator_id,editor_id"
					+ ",date_created,is_valid,data from canvas where id = ?");
			stmt.setInt(1, canvasId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				Canvas c = new Canvas();
				/*c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setPassword(rs.getString("password"));
				c.setCreatorId(rs.getInt("creator_id"));
				c.setEditorId(rs.getInt("editor_id"));
				c.setDateOfCreation(rs.getDate("date_created"));
				c.setValid(rs.getBoolean("is_valid"));
				c.setData(rs.getString("data"));*/
				return c;
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	public static int addCanvasData(int id,String data) {
		con = DBConnection.getConnection();
		try {
			PreparedStatement  stmt = con.prepareStatement("update canvas set data = concat(data,?)");
			stmt.setString(1, data);
			return stmt.executeUpdate();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int joinCanvas(Canvas c,User u,String password) {
		con = DBConnection.getConnection();
		try {
			PreparedStatement st = con.prepareStatement("select id from canvas where id=? and password=?");
			st.setInt(1, c.getId());
			st.setString(2, password);
			 st.executeUpdate();
			 ResultSet resultSet = st.getResultSet();
			 if(resultSet.next()){
				 //means success.
				 GroupHelper.createGroupEntryReturnID(c, u);
				 return 1;
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static ArrayList<CanvasListMessage> getAllOnlineCanvasList() {
		con = DBConnection.getConnection();
		try {
			PreparedStatement st = con.prepareStatement("select id,name,creator_id,password from canvas where is_valid=?");
			st.setBoolean(1, true);
			st.execute();
			ResultSet rs = st.getResultSet();
			ArrayList<CanvasListMessage> list = new ArrayList<CanvasListMessage>();
			int index  = 0;
			while(rs.next()) {
				CanvasListMessage c = new CanvasListMessage();
				c.setCanvasId(rs.getInt("id")+"");
				c.setCreatorId(rs.getString("creator_id"));
				c.setCanvasName(rs.getString("name"));
				c.setCanvasPassword(rs.getString("password"));
				list.add(c);
			}
			st.close();
			for(int i=0;i<list.size();i++) {
				st = con.prepareStatement("select username from user where id=?");
				st.setInt(1, Integer.parseInt(list.get(i).getCreatorId()));
				st.execute();
				rs = st.getResultSet();
				if(rs.next()) {
					System.out.println("Username found with id : "+list.get(i).getCreatorId());
					list.get(i).setCreatorName(rs.getString("username"));
				}
				else {
					System.out.println("username not found with id : "+list.get(i).getCreatorId());
				}
			}
			return list;
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	
}
