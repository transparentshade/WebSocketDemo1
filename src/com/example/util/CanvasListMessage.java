package com.example.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CanvasListMessage {
	private String canvasId;
	private String creatorId;
	private String creatorName;
	private String canvasName;
	private String canvasPassword;

	public String getCanvasId() {
		return canvasId;
	}

	public void setCanvasId(String canvasId) {
		this.canvasId = canvasId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCanvasName() {
		return canvasName;
	}

	public void setCanvasName(String canvasName) {
		this.canvasName = canvasName;
	}

	public static ArrayList<CanvasListMessage> decodeJson(String jsonString) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject requestJson = (JSONObject) parser.parse(jsonString
					.trim());
			/*
			 * System.out.println(requestJson.g);
			 * 
			 * et("senderId").toString() + " " +
			 * requestJson.get("groupId").toString() + " data " +
			 * requestJson.get("Data")
			 */
			int index = 0;
			String str;
			JSONObject j;
			ArrayList<CanvasListMessage> list = new ArrayList<CanvasListMessage>();
			while (( requestJson.get(index+"")) != null) {
				j = (JSONObject) parser.parse(requestJson.get(""+index).toString());
				CanvasListMessage message = new CanvasListMessage();
				if (j.get("canvasId") != null) {
					message.setCanvasId(j.get("canvasId").toString());
				} else {
					System.out
							.println("MESSAGE DECODEING : SORRY canvasId IS NOT FOUND ");
				}
				if (j.get("canvasName") != null) {
					message.setCanvasName(j.get("canvasName")
							.toString());
				} else {
					System.out
							.println("MESSAGE DECODEING : SORRY canvasName  IS NOT FOUND ");
				}
				if (j.get("creatorName") != null) {
					message.setCreatorName(j.get("creatorName")
							.toString());
				} else {
					System.out
							.println("MESSAGE DECODEING : SORRY creatorName code IS NOT FOUND ");
				}
				if (j.get("creatorId") != null) {
					message.setCreatorId(j.get("creatorId")
							.toString());
				} else {
					System.out
							.println("MESSAGE DECODEING : SORRY creatorId IS NOT FOUND ");
				}
				if(j.get("canvasPassword")!=null) {
					message.setCanvasPassword(j.get("canvasPassword").toString());
				}
				else {
					System.out.println("MESSAGE DECODING : SORRY CANVAS PASSWORD NOT FOUND");
					
				}
				list.add(message);
				index++;

			}
			return list;
		} catch (org.json.simple.parser.ParseException pe) {
			System.out.println("position: " + pe.getPosition());
			System.out.println(pe);
		}

		return null;
	}

	public static String encodeJson(ArrayList<CanvasListMessage> list)
			throws IOException {
		// json object
		if (list == null) {
			System.out
					.println("CANVASLISTMESSAGE : Null message found during encoding");
			return null;
		}

		JSONObject jsonObject = new JSONObject();

		StringWriter out = new StringWriter();
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			map.put("creatorId", list.get(i).getCreatorId());
			map.put("canvasId", list.get(i).getCanvasId());
			map.put("canvasName", list.get(i).getCanvasName());
			map.put("creatorName", list.get(i).getCreatorName());
			map.put("canvasPassword", list.get(i).getCanvasPassword());
			jsonObject.put("" + i, map);
		}
		// stores all the lines
		return "" + jsonObject.toJSONString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Canvas Id : " + canvasId + "\n");
		sb.append("CanvasName: " + canvasName + "\n");
		sb.append("Creatorl Id : " + creatorId + "\n");
		sb.append("cretorName : " + creatorName + "\n");
		sb.append("canvasPassword: "+canvasPassword+"\n");
		return sb.toString();
	}

	public String getCanvasPassword() {
		return canvasPassword;
	}

	public void setCanvasPassword(String canvasPassword) {
		this.canvasPassword = canvasPassword;
	}

}
