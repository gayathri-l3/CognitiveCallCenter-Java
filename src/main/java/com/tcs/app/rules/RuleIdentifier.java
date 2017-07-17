package com.tcs.app.rules;

import org.json.JSONObject;

import com.tcs.app.Input;
import com.tcs.app.Rule;

public class RuleIdentifier {

	public static Rule process(Input input, JSONObject previousExchange){
		Rule rule = new Rule();
		previousExchange = (JSONObject) previousExchange.get("map");

		if(previousExchange.getString("outputText").contains(createCharSequence("Great and could I get your employee ID?"))||
				previousExchange.getString("outputText").contains(createCharSequence("I was unable to locate that account. Please repeat your employee ID"))||
				previousExchange.getString("outputText").contains(createCharSequence("Sorry I didn't catch that. What was the employee ID?"))){
		//	System.out.println("ACCOUNT LOOKUP RULE SET TO TRUE");
			rule.setAccountLookup(true);
			rule.setAllRulesFalse(false);
		}

		if(previousExchange.getString("outputText").contains(createCharSequence(" Please provide  the order ID of the advertisement you want to cancel."))||
				previousExchange.getString("outputText").contains(createCharSequence("Sorry I didn't catch that. What was the order ID?"))){
			System.out.println("GET ORDER RULE SET TO TRUE");
			rule.setOrderLookup(true);
			rule.setAllRulesFalse(false);
		}
		
		if(previousExchange.getString("outputText").contains(createCharSequence("Would you like me to email you the current invoice?"))){
			System.out.println("GET Invoice Price RULE SET TO TRUE");
			rule.setInvoicePriceLookup(true);;
			rule.setAllRulesFalse(false);
		}
		
		if(previousExchange.getString("outputText").contains(createCharSequence("I can email you any invoice from the last 12 months.  Which months would you like?"))){
			System.out.println("GET Invoice LOOKUP RULE SET TO TRUE");
			rule.setInvoicePriceLookup(true);
			rule.setAllRulesFalse(false);
		}
		
		if(previousExchange.getString("outputText").contains(createCharSequence("Alright  and what is the new address?"))){
			System.out.println("GET ADDRESSLOOKUP RULE SET TO TRUE");
			rule.setAddressLookup(true);
			rule.setAllRulesFalse(false);
		}
		if(previousExchange.getString("outputText").contains(createCharSequence("What would you like to set as the new number?"))){
			System.out.println("GET PHONE RULE SET TO TRUE");
			rule.setPhoneLookup(true);;
			rule.setAllRulesFalse(false);
		}
		if(previousExchange.getString("outputText").contains(createCharSequence("Alright and what is the new email address?"))){
			System.out.println("GET EMAIL RULE SET TO TRUE");
			rule.setEmailLookup(true);
			rule.setAllRulesFalse(false);
		}
//		



		
		return rule; 
	}

	private static CharSequence createCharSequence(String string) {
		CharSequence charsequence = string;
		return charsequence;
	}
	
}
