package com.tcs.app.entity;

public class Message {
	private String id;
	private String text;
	private String name;
	
	public Message(String id, String text, String name) {
		this.id = id;
		this.text = text;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public String getName() {
		return name;
	}

}
