package com.tcs.app.rules;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.cloudant.client.api.Database;
import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.tcs.app.Input;
import com.tcs.app.Rule;
import com.tcs.app.entity.Customer;
import com.tcs.app.entity.Payment;
import com.tcs.app.services.tasks.FetchTask;
import com.tcs.app.services.tasks.InsertTask;
import com.tcs.app.services.tasks.SendTextTask;
import com.tcs.app.services.tasks.UpdateTask;
import com.tcs.app.common.util.ConvertWordDigitsToNumber;
import com.tcs.app.common.util.ElephantSqlConnection;

public class RuleExecutor {

	/**
	 * @param input
	 * @param rule
	 * @param customerDatabase
	 * @param customer
	 * @param payment 
	 * @param speechToText 
	 * @return outputText
	 * @throws Exception 
	 */
	public static String process(Input input, Rule rule, Database customerDatabase, Customer customer, Payment payment, SpeechToText speechToText) throws Exception{
		String outputText = "null";

		if(rule.getAllRulesFalse() == false){ //Checking to see if any of the rules have been set to true
			
			if(rule.getAccountLookup() == true){
				System.out.println("IN ACCOUNT LOOKUP RULE");
				outputText=AccountNumberRule.process(input, rule, customerDatabase, customer, payment, speechToText);
				System.out.println("OutputText:" +outputText);
			}
			
			if(rule.getOrderLookup() == true){
				System.out.println("IN ORDER LOOKUP RULE");
				outputText=OrderIDRule.process(input, rule, customerDatabase, customer, speechToText);
				System.out.println("OutputText:" +outputText);
			}
			
			if(rule.getInvoicePriceLookup()==true){
				System.out.println("In Invoice Price Lookup rule");
				outputText=InvoicePriceRule.process(input, rule, customerDatabase, customer, payment, speechToText);
				System.out.println("OutputText:" +outputText);
			}
			
			if(rule.getInvoiceLookup()==true){
				System.out.println("In Invoice Lookup rule");
				outputText=InvoiceRule.process(input, rule, customerDatabase, customer, payment, speechToText);
				System.out.println("OutputText:" +outputText);
			}
			if(rule.getAddressLookup()==true){
				System.out.println("In Address Lookup rule");
				outputText=AddressRule.process(input, rule, customerDatabase, customer, speechToText);
				System.out.println("OutputText:" +outputText);
			}
			
			if(rule.getPhoneLookup()==true){
				System.out.println("In Phone Lookup rule");
				outputText=PhoneRule.process(input, rule, customerDatabase, customer, speechToText);
				System.out.println("OutputText:" +outputText);
			}
			
			if(rule.getEmailLookup()==true){
				System.out.println("In Email Lookup rule");
				outputText=EmailRule.process(input, rule, customerDatabase, customer, speechToText);
				System.out.println("OutputText:" +outputText);
			}
		

		
		}
		
		return outputText;
	}
}
