package com.tcs.app.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
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
import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.tcs.app.Input;
import com.tcs.app.entity.Customer;
import com.tcs.app.entity.Message;
import com.tcs.app.entity.Payment;
import com.tcs.app.services.tasks.ChatTask;
import com.tcs.app.services.tasks.HoldServiceTask;
import com.tcs.app.services.tasks.RecordServiceTask;
import com.tcs.app.services.tasks.StartServiceTask;
import com.twilio.twiml.TwiMLException;

@RestController
@CrossOrigin("*")
public class CallCenterServiceController {
	
	@Autowired
	Database[] dbArr;
	
	public Map<String, Map<String, Object>> masterContext = new HashMap<String, Map<String, Object>>();
	public Input input = new Input();
	public Customer customer = new Customer();
	public Payment payment = new Payment();
	SpeechToText speechToText ;
	
	public static Map<String, Map<String, Object>> conversationMap;
	public static ConversationService watsonConversationService;
	public static String workspaceID;
	
	public static void init(String version, String username, String password, String workspaceId) {
		conversationMap = new HashMap<String, Map<String, Object>>();
		watsonConversationService = new ConversationService(version);
		watsonConversationService.setUsernameAndPassword(username, password);
		CallCenterServiceController.workspaceID = workspaceId;
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
	
	public CallCenterServiceController(){
		speechToText = new SpeechToText();
		speechToText.setUsernameAndPassword("085876cf-5d45-47ff-93eb-142797ac893b", "MO0ingDl2rb2");
	}
	//@CrossOrigin(origins = "https://saleschatservice.mybluemix.net")
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value="/conversation", method = RequestMethod.POST, produces = "application/xml")
	public void convers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JsonObject outJson = new JsonObject();
		try {
			 outJson = ChatTask.process(request,response,masterContext, input, dbArr, customer, payment, speechToText);
			System.out.println(masterContext.get("currentContext"));
		} catch (Exception re) {
			re.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		
		response.getWriter().println(outJson);
	}

	
	
	/*	
	@RequestMapping(value="/start", method = RequestMethod.POST, produces = "application/xml")
	public String startConversation() throws Exception{
		String response = null;
		try {
			response = StartServiceTask.process(masterContext, input, dbArr, customer, payment, speechToText);
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
			response = RecordServiceTask.process(req, res, masterContext, input, dbArr, customer, payment, speechToText);
		} catch (TwiMLException e) {
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return response;	
	}
	
	@RequestMapping(value="/hold", method = RequestMethod.POST, produces = "application/xml")
	public void holdConversation( HttpServletRequest req, HttpServletResponse res ){
		HoldServiceTask.process(req, res, this.masterContext);
	}
	*/

}
