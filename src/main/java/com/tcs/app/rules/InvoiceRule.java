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

public class InvoiceRule{
	
		public static String process(Input input, Rule rule, Database customerDatabase, Customer customer, Payment payment, SpeechToText speechToText){
			String outputText = null;
			String fName = null;
		//	System.out.println("Input : "+ input.getText());
		//	System.out.println("StT : "+ speechToText);
			String email = customer.getEmail();
			
					outputText = "I have sent an email containing the invoices to " + email + " Can I help you with anything else?";
			
			return outputText;
		
	}
}  
