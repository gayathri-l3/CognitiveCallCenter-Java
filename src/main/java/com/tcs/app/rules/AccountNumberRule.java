package com.tcs.app.rules;

import com.cloudant.client.api.Database;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.tcs.app.Input;
import com.tcs.app.Rule;
import com.tcs.app.entity.Customer;
import com.tcs.app.entity.Payment;
import com.tcs.app.services.tasks.FetchTask;

public class AccountNumberRule {
	public static String process(Input input, Rule rule, Database customerDatabase, Customer customer, Payment payment, SpeechToText speechToText){
		String outputText = null;
		String fName = null;
	//	System.out.println("Input : "+ input.getText());
	//	System.out.println("StT : "+ speechToText);
		
		try {
			try{
				int account =Integer.parseInt(input.getText());
				Customer tempCustomer =	FetchTask.fetchCustomerByAccount(customerDatabase, (long) account);
				customer.setAccountNum(tempCustomer.getAccountNum());
				customer.set_Rev(tempCustomer.get_Rev());
				customer.set_Id(tempCustomer.get_Id());
				customer.setAddress(tempCustomer.getAddress());
				customer.setEmail(tempCustomer.getEmail());
				customer.setEmpID(tempCustomer.getEmpID());
				customer.setFName(tempCustomer.getFname());
				customer.setLName(tempCustomer.getLname());
				customer.setOrders(tempCustomer.getOrders());
				
				 fName = customer.getFname();
				
				 System.out.println(customer.getOrders());
			}catch(NumberFormatException | NullPointerException ex){
				ex.printStackTrace();
			//	System.out.println("Customer is null");
				customer =null;
			}
			
			if(customer != null){
				input.setAlteredText("accept");
				outputText = "Thank you " + fName + " How can I help you?";
			} else {
				input.setAlteredText("reject");
				outputText = "I was unable to locate that account. Please repeat your employee ID";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			input.setAlteredText("reject");
			e.printStackTrace();
		}
		return outputText;
	}
}
