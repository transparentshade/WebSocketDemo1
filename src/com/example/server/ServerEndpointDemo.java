package com.example.server;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.example.bean.Canvas;
import com.example.bean.User;
import com.sun.xml.xsom.impl.scd.Iterators.Map;

@ServerEndpoint("/serverEndpointDemo")
public class ServerEndpointDemo {
	private static final String SUCCESS = "SENTSUCCESS";
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	public static HashMap<Integer, Canvas> onlineCanvas = new HashMap<Integer,Canvas>();
	@OnOpen
	public void handleOpen(Session session) {
		System.out.println("Connectin created for client "+session.getId());
		clients.add(session);
		//onlineCanvas.put(key, value)
	}
	@OnMessage
	public String handleMessage(Session session, String message)  {
		try {
			message = message.trim();
			System.out.println(" Received <---- "+message);
			if(message==null || message.length()==0){
				System.out.println("EMPTY MESSAGE RECIEVED And DSICARDED");
			}
			else {
				
				RequestProcessor.processArrivedMessage(message,onlineCanvas,session);
			}
			System.out.println("Size of online canvas : "+onlineCanvas.size());
			if(onlineCanvas.entrySet().size()==0)
			{
				System.out.println("NO ACTIVE CANVAS");
				
			}
			else {
				
				System.out.println("************************printing all session:**************** ");
				for(Entry<Integer, Canvas> p : onlineCanvas.entrySet()){
					
					if(p!=null){
						System.out.println("Printing canvas : ");
						System.out.println(p.getValue());
						HashMap<Integer, User> ulist =  p.getValue().getGroup().getList();
						if(ulist==null ){
							System.out.println("List of user set is null");
						}
						else {
							System.out.println("printing usrs of above canvas");
							for(Entry<Integer, User> u: ulist.entrySet()){
								System.out.println(u.getValue().getId());
							}
						}
					}
					else {
						System.out.println("the session is null"+p.getKey());
					}
				}
				System.out.println("************************SessionPrint Ends*************************");
			}
			
			return SUCCESS;
		}
		catch(IOException e) {
			//unable to send the data client might be unreachable.
		}
		catch(Exception e) {
			System.out.println("While processing on message ");
			e.printStackTrace();
		}
		return null;
	}
	
	@OnClose
	public void handleClose() {
		
	}
	
	@OnError
	public void hanleError(Throwable t) {
		t.printStackTrace();
	}
	
}
