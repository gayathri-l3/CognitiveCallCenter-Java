package com.tcs.entities;

public class Message {
//	private String id;
//	private String text;
//	private String name;
//	private String alteredContent;
//	
//	public Message(String id, String text, String name) {
//		this.id = id;
//		this.text = text;
//		this.name = name;
//	}
//	
//	public String getId() {
//		return id;
//	}
//	
//	public String getText() {
//		return text;
//	}
//	
//	public String getName() {
//		return name;
//	}
	
	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessageIntention() {
		return messageIntention;
	}

	public void setMessageIntention(String messageIntention) {
		this.messageIntention = messageIntention;
	}

	public String getMessageAlteredContent() {
		return messageAlteredContent;
	}

	public void setMessageAlteredContent(String messageAlteredContent) {
		this.messageAlteredContent = messageAlteredContent;
	}
	
	private String messageID;
	private String messageContent;
	private String messageIntention;
	private String messageAlteredContent;
	
	public Message(String theMessageID, String theMessageContent, String theMessageIntention) {
		messageID = theMessageID;
		messageContent = theMessageContent;
		messageIntention = theMessageIntention;
	}
	
	
}
