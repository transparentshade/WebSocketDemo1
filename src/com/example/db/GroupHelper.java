package com.example.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.bean.Canvas;
import com.example.bean.Group;
import com.example.bean.User;

public class GroupHelper {
	private static Connection con;
	public static int createGroupEntryReturnID(Canvas c, User u){
		con = DBConnection.getConnection();
		Statement st;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("insert into canvasdb.group(id,subscriber_id) values (");
			sb.append(c.getId()+",");
			sb.append(u.getId()+")");
			System.out.println(sb.toString());
			st = con.createStatement();
			st.executeUpdate(sb.toString(), Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}		
	public static Group getGroup(int id) {
		con = DBConnection.getConnection();
		PreparedStatement st;
		try {
			st = con.prepareStatement("select subscriber_id from canvasdb.group where id = ?");
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			Group g = new Group();
			g.setId(id);
			ArrayList<Integer> list = new ArrayList<Integer>();
			while(rs.next()) {
				list.add(rs.getInt("subscriber_id"));
			}
			st.close();
			HashMap<Integer,User> list2 = new HashMap<Integer,User>();
			for(int i=0;i<list.size();i++) {
				User u = UserHelper.getUserById(list.get(i));
				list2.put(list.get(i), u);
			}
			g.setList(list2);
			return g;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return null;
	}
}
