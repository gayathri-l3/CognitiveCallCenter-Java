package com.tcs.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.tcs.app.services.tasks.RecordServiceTask;
import com.tcs.app.services.tasks.StartServiceTask;
import com.tcs.app.services.tasks.WebServiceTask;
import com.tcs.entities.Message;
import com.twilio.twiml.TwiMLException;
import com.tcs.entities.Customer;


@CrossOrigin(origins = "*")
@RestController
public class CallCenterRestController {
	public  Map<String, Map<String, Object>> masterContext = new HashMap<String, Map<String, Object>>();
	
	@Autowired
	private Database cloudantDatabase;
	
	public static Map<String, Map<String, Object>> conversationMap;
	public static ConversationService watsonConversationService;
	public static String workspaceID;
	
	public static void init(String version, String username, String password, String workspaceId) {
		conversationMap = new HashMap<String, Map<String, Object>>();
		watsonConversationService = new ConversationService(version);
		watsonConversationService.setUsernameAndPassword(username, password);
		CallCenterRestController.workspaceID = workspaceId;
	}

	@RequestMapping(value="/chat", method = RequestMethod.POST)
	public String chat(@RequestBody Customer theUser){
		return "hello world!";
	}
	
	public void addToConversationMap(String theMessageID, String theMessageIntention, Object value) {
		
		if (!conversationMap.containsKey(theMessageID)) {
			conversationMap.put(theMessageID, new HashMap<String, Object>());
		}
		conversationMap.get(theMessageID).put(theMessageIntention, value);
	}
	
	public void resetConversationMap(String theMessageID) {
		conversationMap.put(theMessageID, null);
	}
	
	@CrossOrigin(origins = "https://banking-cust-service.tcs.us-south.mybluemix.net")
	@RequestMapping(method = RequestMethod.POST, value = "/conversation", consumes = "application/json", produces = "application/json")
	public @ResponseBody String conversation(@RequestBody String body, HttpServletRequest http) throws JSONException {
		
		Gson gson = new Gson();
		Message message = gson.fromJson(body, Message.class);
		String messageID = message.getMessageID();
		
		addToConversationMap(messageID, "message intention", message.getMessageIntention());

		MessageRequest userRequest = new MessageRequest.Builder()
									.inputText(message.getMessageContent())
									.context(conversationMap.get(messageID)).build();
		
		MessageResponse watsonResponse = watsonConversationService.message(workspaceID, userRequest).execute();
		
		conversationMap.put(messageID, watsonResponse.getContext());
		
		boolean isAccountExisted = false;
		String targetNodeName = "Date Of Birth";
		
		String visitedNode = watsonResponse.getOutput().get("nodes_visited").toString();
		if(visitedNode.contains(targetNodeName)) {
			String tempMemberID = watsonResponse.getContext().get("memberID").toString();
			String tempTaxID = watsonResponse.getContext().get("taxID").toString();	
			String tempDOB = watsonResponse.getContext().get("DOB").toString();
			isAccountExisted = WebServiceTask.process(cloudantDatabase, tempMemberID, tempTaxID, tempDOB);
			//return isAccountExisted;
		} 
		
		System.out.println(watsonResponse);
		
		ArrayList<Message> messagesList = new ArrayList<Message>();
		
		for (Object outputMessage : (ArrayList<Object>) watsonResponse.getOutput().get("text")) {
			messagesList.add(new Message("watson", outputMessage.toString(), ""));
		}
		
		return gson.toJson(messagesList);
	}

	@RequestMapping(value="/start", method = RequestMethod.POST, produces = "application/xml")
	public String startConversation(){
		String response = null;
		try {
			response = StartServiceTask.process(masterContext);
			System.out.println(masterContext.get("currentContext"));
		} catch (TwiMLException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value="/record", method = RequestMethod.POST, produces = "application/xml")
	public String recordConversation( HttpServletRequest req, HttpServletResponse res ){
		String response = null;
		try {
			response = RecordServiceTask.process(req, res, masterContext, cloudantDatabase );
		} catch (TwiMLException e) {
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return response;	
	}	
}
