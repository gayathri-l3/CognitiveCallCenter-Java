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

public class EmailRule{
	public static String process(Input input, Rule rule, Database customerDatabase, Customer customer, SpeechToText speechToText) throws Exception{
		String email = input.getText();
		
		
		
		System.out.println("Email : " + email);
		String outputText = "Okay I have updated your email address to " + email + " Can I help you with anything else?";
		
		System.out.println("\n\nCustomer Account : \n\n " + customer.getAccountNum());
		if(email != null){
			input.setAlteredText("accept");
			try{
				FetchTask.updateCustomerEmail(customerDatabase, customer, email);
			}catch(DocumentConflictException ex){
				input.setAlteredText("accept");
			}

		}else{
			input.setAlteredText("reject");
		}
		return outputText;
	}
}
