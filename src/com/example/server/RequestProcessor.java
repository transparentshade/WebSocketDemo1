package com.example.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.Session;

import org.json.simple.JSONObject;

import com.example.bean.Canvas;
import com.example.bean.User;
import com.example.db.UserHelper;
import com.example.excptn.CanvasException;
import com.example.util.Message;
import com.example.util.Point;

public class RequestProcessor {
	private static Message m ;
	private static HashMap<Integer, Canvas> onlineUsers;
	
	public static Message processArrivedMessage(String message,HashMap<Integer, Canvas> online,Session session) throws CanvasException, IOException {
		m = Message.decodeJson(message);
		
		if(m==null){
			throw new CanvasException("INVALID MESSAGE DISCARDED");
			
		}
		onlineUsers = online;
		String opCode = m.getOperationCode();
		System.out.println("Opecode : "+opCode);
		switch(opCode) {
			case "BROADDATA":
				System.out.println("BROADCAST MESSAGE REQUEST ARRIVED");
				Canvas  c = onlineUsers.get(Integer.parseInt(m.getGroupId()));
				if(c==null) {
					System.out.println("Session not found for canvas : "+m.getGroupId());
				}
				else {
					System.out.println(c);
					BroadCast.broadcastDataMessage(m, c);
				}
				break;
			case "JOINREQ":
				User u = UserHelper.getUserById(Integer.parseInt(m.getSenderId()));
				System.out.println("JOINREQ REQUEST RECEIVED FROM "+u.getUsername());
				 joinRequestProcess(m,online,session);
				 break;
				
			case "DISCREQ":
				User du = UserHelper.getUser(m.getSenderId());
				System.out.println("DISCREQ REQUEST RECEIVED FROM "+du.getUsername());
				break;
			case "PULLREQ":
				User pu = UserHelper.getUser(m.getSenderId());
				System.out.println("JOIN REQUEST RECEIVED FROM "+pu.getUsername());
				break;
			default:
				System.out.println("DISCARDING UNRECOGNISE MESSAGE RECEIVED FROM ");
		}
		return null;
	}
	
	
	public static void joinRequestProcess(Message message,HashMap<Integer, Canvas> online,Session session) {
		//get the canvas
		System.out.println("Message at joinRequstProcess: "+message);
		Canvas c = online.get(Integer.parseInt(m.getGroupId()));
		Message mToSend = new Message();
		
		System.out.println("canvas at JoinRequestProcess : "+c);
		if (c!=null && c.getPassword().trim().equals(message.getPassword().trim())){
			try {
				//authenticated.... add to gropu and set the session
				User u = UserHelper.getUserById(Integer.parseInt(message.getSenderId()));
				u.setSession(session);
				c.getGroup().getList().put(u.getId(), u);
				mToSend.setSenderId(""+1);
				mToSend.setGroupId(c.getGroup().getId()+"");
				mToSend.setOperationCode("JOINACC");
				mToSend.setPassword(c.getPassword());
				mToSend.getCurves().add(new ArrayList<Point>());//to create empty array
				String data = c.getData();
				String str = Message.encodeJson(mToSend,data);
				
				System.out.println("REQUESTPROCESSOR: going for decoding merged message  "+str);
				mToSend = Message.decodeJson(str);
				System.out.println("JOIN ACCEPTED AND RESPONSE : "+mToSend);
				System.out.println("******JOIN REQUEST ACCEPTED**************");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			//rejection message.
			//means invalid request...
			if(c==null) {
				System.err.println("Requested canvas is not available");
			}
			else {
				System.err.println("Password didnt matched");
			}
			mToSend.setSenderId(message.getSenderId());
			mToSend.setGroupId(message.getGroupId());
			mToSend.setOperationCode("JOINREJ");
			mToSend.getCurves().add(new ArrayList<Point>());
			mToSend.setPassword("dummmy");
			System.out.println("******JOIN REQUEST REJECTED**************");
			
		
		}
		try {
			session.getBasicRemote().sendText(Message.encodeJson(mToSend));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//find the user.
		//update the session.
		
	}
	
}
