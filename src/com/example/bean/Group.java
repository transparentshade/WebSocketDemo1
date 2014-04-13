package com.example.bean;

import java.util.ArrayList;
import java.util.HashMap;

public class Group {
	private int id;
	private HashMap<Integer ,User> list;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public HashMap<Integer ,User> getList() {
		return list;
	}
	public void setList(HashMap<Integer ,User> list) {
		this.list = list;
	}
}
