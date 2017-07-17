package com.tcs.app.entity;

import java.util.ArrayList;

public class ConversationContext {

	private ArrayList<DialogNode> dialog_stack;
	private String dialog_turn_counter;
	private String dialog_request_counter;
	
	public ConversationContext() {
		
	}

	public ArrayList<DialogNode> getDialog_stack() {
		return dialog_stack;
	}

	public void setDialog_stack(ArrayList<DialogNode> dialog_node) {
		this.dialog_stack = dialog_node;
	}

	public String getDialog_turn_counter() {
		return dialog_turn_counter;
	}

	public void setDialog_turn_counter(String dialog_turn_counter) {
		this.dialog_turn_counter = dialog_turn_counter;
	}

	public String getDialog_request_counter() {
		return dialog_request_counter;
	}

	public void setDialog_request_counter(String dialog_request_counter) {
		this.dialog_request_counter = dialog_request_counter;
	}

}
