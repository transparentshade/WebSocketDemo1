package com.example.bean;

import java.util.Date;
import java.util.LinkedHashMap;

import javax.json.JsonObject;

import org.json.simple.JSONObject;

public class Canvas {
	private int id;
	private String name;
	private User creator;
	private User editor;
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public User getEditor() {
		return editor;
	}
	public void setEditor(User editor) {
		this.editor = editor;
	}
	private Date dateOfCreation;
	private boolean isValid;
	private String password;
	private String data;
	private Group group;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public String toString() {
		StringBuilder str = new StringBuilder(" \t\t **Canvas Details**\n");
		str.append("\t Id:  "+this.id+"\n");
		str.append("\t Name: "+this.name+"\n");
		str.append("Password: "+this.password+"\n");
		str.append("\t creatorId :  "+this.creator.getId()+"\n");
		str.append("\t Editor :  "+this.editor.getId()+"\n");
		str.append("\t isValid :  "+this.isValid+"\n");
		str.append("\t Data:  "+this.data+"\n");
		str.append("\t Group id:   "+this.group.getId()+"\n");
		return str.toString();
	}
	public String getJson() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("name", this.name);
		json.put("password", this.password);
		json.put("creatorId", this.creator.getId());
		json.put("editorId",this.editor.getId());
		json.put("isValid", this.isValid);
		json.put("data", this.data);
		json.put("groupId", this.group.getId());
		return json.toJSONString();
	}
}
