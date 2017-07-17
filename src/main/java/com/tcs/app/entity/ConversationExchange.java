package com.tcs.app.entity;

public class ConversationExchange {

	private String _id;
	private String inputText;
	private String conversationResponse;

	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getInputText() {
		return inputText;
	}
	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	public String getConversationResponse() {
		return conversationResponse;
	}
	public void setConversationResponse(String conversationResponse) {
		this.conversationResponse = conversationResponse;
	}
	
}
