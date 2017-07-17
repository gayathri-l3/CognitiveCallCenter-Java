package com.tcs.app.integration;

import java.util.Map;

import org.apache.tomcat.util.digester.Rules;
import org.json.JSONObject;

import com.cloudant.client.api.Database;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.tcs.app.Input;
import com.tcs.app.Rule;
import com.tcs.app.entity.Customer;
import com.tcs.app.entity.Payment;
import com.tcs.app.rules.RuleExecutor;
import com.tcs.app.rules.RuleIdentifier;
import com.tcs.app.services.tasks.ConversationServiceTask;
import com.tcs.app.services.tasks.FetchTask;
import com.tcs.app.services.tasks.InsertTask;

public class DataAccessRouter {

	public JSONObject currentExchange = new JSONObject();

	public String process(Input input, Map<String, Map<String, Object>> masterContext, String state, Database[] dbArr, Customer customer, Payment payment, SpeechToText speechToText) throws Exception{
		String outputText = "null";
		String accountNum ="null";
		String alteredOutputText = "null";
		System.out.println("inputText"+input.getText());
		currentExchange.put("inputText", input.getText());
		//Pull previous exchange from cloudant
		JSONObject previousExchange = null;
		if(!input.getId().equals("null")){
			try {
				previousExchange = FetchTask.fetchDocumentByID(dbArr[1], input.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//Send to rules identifier - Chooses which rule to execute
		Rule rule = new Rule();
		rule = RuleIdentifier.process(input, previousExchange);
		//Send to rule executor - Executes the rule
		alteredOutputText = RuleExecutor.process(input, rule, dbArr[0], customer, payment, speechToText);
	
		}
		//ConversationServiceTask - sends the input to conversation service and returns the response from conversation
		if(input.getAlteredText().equals("null")){

			outputText = ConversationServiceTask.process(masterContext, input.getText(), state);
		
			
		} else{
			
			outputText = ConversationServiceTask.process(masterContext, input.getAlteredText(), state);
		
		}
		
		//Update cloudant with the current conversation exchange
		if(!alteredOutputText.equals("null")){
			currentExchange.put("outputText", alteredOutputText);
		} else{
			currentExchange.put("outputText", outputText);
		}
		System.out.println("");
		String id = InsertTask.process(dbArr[1], currentExchange);
		input.setId(id);

		//return the output for twilio to say back to the user
		if(alteredOutputText.equals("null")){
			return outputText;
		} else {
			return alteredOutputText;
		}
	}
	
}
