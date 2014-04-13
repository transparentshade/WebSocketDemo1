package com.example.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.stream.JsonParser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Message {
	private String senderId;
	private String groupId;
	private String operationCode;
	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String password;
	private ArrayList<ArrayList<Point>> curves = new ArrayList<ArrayList<Point>>();

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public ArrayList<ArrayList<Point>> getCurves() {
		return curves;
	}

	public void setCurves(ArrayList<ArrayList<Point>> curves) {
		this.curves = curves;
	}

	@SuppressWarnings("unchecked")
	public static String encodeJson(Message message,String data) throws IOException {
		
		// json object
		// stores all the lines
		String encMsg = Message.encodeJson(message);
		if(data==null || data.trim().length()==0){
			System.out.println("MESSAGE ENCODING : empty data found to merge with original message doing simple encoding");
			return encMsg;
		}
		else {
			System.out.println("MESSAGE ENCODING : proper data found and doing merging of message and current data set\n data : "+data);
		}
		StringBuilder sb = new StringBuilder(encMsg);
		StringBuilder res = new StringBuilder();
		int startIndx = sb.indexOf("data\":{\"0\":[");
		System.out.println("Start index : "+startIndx);
		res.append(sb.substring(0, startIndx+12));
		res.append(data+",{\"X\":0,\"Y\":0}");//x=0=y to keep track of new lines
		res.append(",");
		res.append(sb.substring(startIndx+12, sb.length()));
		
		System.out.println("MESSAGE ENCODING : final data after merging with original message : "+res.toString());
		return res.toString();
	}
	
	public static String encodeJson(Message message) throws IOException {
		// json object
		System.out.println(message.getSenderId() + " " + message.getGroupId()
				+ "  " + message.getCurves().size());

		JSONObject jsonObject = new JSONObject();

		StringWriter out = new StringWriter();

		jsonObject.put("senderId", message.getSenderId());
		jsonObject.put("groupId", message.getGroupId());
		jsonObject.put("operationCode", message.getOperationCode());
		jsonObject.put("password", message.getPassword());
		// stores all the lines
		Map lineList = new LinkedHashMap();
		// create object for each line
		for (int i = 0; i < message.getCurves().size(); i++) {
			List line = new LinkedList();
			ArrayList<Point> list = message.getCurves().get(i);
			for (int j = 0; j < list.size(); j++) {
				Map pointMap = new LinkedHashMap();
				pointMap.put("X", list.get(j).getX());
				pointMap.put("Y", list.get(j).getY());
				line.add(pointMap);
			}
			lineList.put("" + i, line);
		}
		jsonObject.put("data", lineList);
		jsonObject.writeJSONString(out);

		return "" + jsonObject.toJSONString();
	}

	
	
	
	
	public static Message decodeJson(String jsonString) {
		try {
			Message m = new Message();
			JSONParser parser = new JSONParser();
			JSONObject requestJson = (JSONObject) parser.parse(jsonString.trim());
			/*System.out.println(requestJson.g);
			
			et("senderId").toString() + " "
					+ requestJson.get("groupId").toString() + " data "
					+ requestJson.get("Data")
			*/
			
			if(requestJson.get("senderId")!=null){
				m.setSenderId(requestJson.get("senderId").toString());
			}
			else {
				System.out.println("MESSAGE DECODEING : SORRY SENDER ID IS NOT FOUND ");
			}
			if(requestJson.get("groupId")!=null){
				m.setGroupId(requestJson.get("groupId").toString());
			}
			else {
				System.out.println("MESSAGE DECODEING : SORRY Group ID IS NOT FOUND ");
			}
			if(requestJson.get("operationCode")!=null){
				m.setOperationCode(requestJson.get("operationCode").toString());
			}
			else {
				System.out.println("MESSAGE DECODEING : SORRY Operation code IS NOT FOUND ");
			}
			if(requestJson.get("password")!=null){
				m.setPassword(requestJson.get("password").toString());
			}
			else {
				System.out.println("MESSAGE DECODEING : SORRY password IS NOT FOUND ");
			}
			if(requestJson.get("data")!=null){
				JSONObject dataObject =  (JSONObject) requestJson.get("data");
				
				System.out.println("MESSAGE DECODINGS : Data Object found  " + dataObject);
					JSONArray points = (JSONArray) dataObject.get("" + 0);
					System.out.println("Points : " + points);
					ArrayList<Point> line = new ArrayList<Point>();
					Object coordinate;
					for (int i = 0; i < points.size(); i++) {
						Point p = new Point();
						JSONObject point = (JSONObject) points.get(i);
						coordinate = point.get("X");
						if(coordinate==null){
							System.out.println("MESSAGE DECODING : empty x coordinate found");
						}
						else {
							p.setX(Float.parseFloat(coordinate.toString()));
						}
						coordinate = point.get("Y");
						if(coordinate==null){
							System.out.println("MESSAGE DECODING : empty y coordinate found");
						}
						else {
							p.setY(Float.parseFloat(coordinate.toString()));
						}
						// System.out.println("X: "+point.get("X")+
						// " Y: "+point.get("Y"));

						line.add(p);
					}
					m.getCurves().add(line);

			}
			
		//	System.out.println(requestJson.get("data").toString());
						// m.set
			return m;
		} catch (org.json.simple.parser.ParseException pe) {
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}

		return null;
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Sender Id: " + this.senderId + "\n");
		str.append("Group Id : " + this.groupId + "\n");
		str.append("operationcode: "+this.operationCode+"\n");
		str.append("Paswword: "+this.password+"\n");
		str.append("Data : \n");
		for (int j = 0; j < this.getCurves().size(); j++) {
			ArrayList<Point> line = this.getCurves().get(j);
			str.append("Line " + j + ": \n");
			for (int i = 0; i < line.size(); i++) {
				str.append("X: " + line.get(i).getX() + " Y: "
						+ line.get(i).getY() + "\t");
			}
			str.append("\n");

		}
		return str.toString();
	}
}