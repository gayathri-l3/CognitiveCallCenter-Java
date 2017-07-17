package com.tcs.app;

public class Input {

	private String id;
	private String text;
	private String alteredText;
	
	public Input(){
		this.id = "null";
		this.text = "null";
		this.alteredText = "null";
	}

	public void setId(String id){
		this.id = id;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setAlteredText(String alteredText){
		this.alteredText = alteredText;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getText(){
		return this.text;
	}
	
	public String getAlteredText(){
		return this.alteredText;
	}
}
