package com.example.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.websocket.Session;
import javax.xml.crypto.Data;

import com.example.bean.Canvas;
import com.example.bean.Group;
import com.example.bean.User;
import com.example.db.CanvasHelper;
import com.example.db.GroupHelper;
import com.example.db.UserHelper;
import com.example.util.Message;
import com.example.util.Point;

public class BroadCast {
   private static	ArrayList<User> list;
   private static Canvas canvas;
   private static Message message;
	public static void broadcastDataMessage(Message m,Canvas c) throws IOException {
		System.out.println("FOR CANVAS : \n\t"+c);
		//append data to canvas data
	
		StringBuilder sb = new StringBuilder();
		for(Point p: m.getCurves().get(0)){
			sb.append("{\"X\":"+p.getX()+",\"Y\":"+p.getY()+"},");
		}
		sb.deleteCharAt(sb.length()-1);
		System.out.println("DATA EXTRACTED TO APPEND TO CANVAS : "+sb.toString());
		if(c.getData()==null || c.getData().trim().length()==0){
			c.setData(sb.toString());
		}
		else {
			c.setData(c.getData()+","+sb.toString());
		}
		
		System.out.println("DATA SAVED IN CANVAS AFTER BROADCAST "+c.getData());
		
		//send to others
		String message = Message.encodeJson(m);
		for (Entry<Integer, User> p : c.getGroup().getList().entrySet()){
			Session s = p.getValue().getSession();
			if (s.isOpen()) {
				s.getBasicRemote().sendText(message);
			} else {
				System.out.println("CLIENT NOT REACHABLE " + s.getId());
			}
		}

	}
	
	//returns a created canvas and a grouop for that.
	public static Group  createCanvasGroup(int creatorId,String canvasName,String canvasPassword) {
		//create a canvas and then a group in db too
		//then update group-id in canvas table.
		Canvas c = new Canvas();
		User u = UserHelper.getUserById(creatorId);
		Group g = new Group();
		g.setId(-1);
		c.setCreator(u);
		c.setData("");
		c.setEditor(u);
		c.setId(-1);
		c.setName(canvasName);
		c.setPassword(canvasPassword);
		int canvasId = CanvasHelper.createCanvasReturnId(canvasName, canvasPassword, creatorId, creatorId,-1, null, true, "");
		c.setId(canvasId);
		
		//create the group.
		int gid = GroupHelper.createGroupEntryReturnID(c, u);
		g.setId(gid);
		HashMap<Integer, User> list = new HashMap<Integer,User>();
		list.put(u.getId(),u);
		g.setList(list);
		c.setGroup(g);
		return g;
	}
	public static void joinGroup(Canvas c, User u,String password) {
		 int status = CanvasHelper.joinCanvas(c, u, password);
		 if(status==-1) {
			 //inform about error
		 }
		 else {
			 //move to canvas...
		 }
	}

}
