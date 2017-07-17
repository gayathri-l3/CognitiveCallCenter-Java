package com.tcs.app.rules;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.org.lightcouch.DocumentConflictException;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.tcs.app.Input;
import com.tcs.app.Rule;
import com.tcs.app.common.util.ElephantSqlConnection;
import com.tcs.app.entity.Customer;
import com.tcs.app.entity.Payment;
import com.tcs.app.services.tasks.FetchTask;
import com.tcs.app.services.tasks.UpdateTask;

public class OrderIDRule{
	public static String process(Input input, Rule rule, Database customerDatabase, Customer customer, SpeechToText speechToText) throws Exception{
		String id = input.getText().replaceAll("\\D", "");
		
		long orderID = 0;
		try{
			orderID = Integer.parseInt(id);
		}catch(NumberFormatException exe){
			orderID = 0;
		}
		
		System.out.println("OrderID : " + orderID);
		String outputText = "You want to cancel the order with the ID: " + orderID + " Is this correct?";
		
		System.out.println("\n\nCustomer Account : \n\n " + customer.getAccountNum());
		if(orderID >= 1){
			input.setAlteredText("accept");
			try{
				FetchTask.updateCustomerOrder(customerDatabase, customer, orderID);
			}catch(DocumentConflictException ex){
				input.setAlteredText("accept");
			}

		}else{
			input.setAlteredText("reject");
		}
		return outputText;
	}
}
