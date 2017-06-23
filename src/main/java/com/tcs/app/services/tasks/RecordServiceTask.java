package com.tcs.app.services.tasks;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.service.exception.BadRequestException;
import com.twilio.twiml.Method;
import com.twilio.twiml.Record;
//import com.twilio.twiml.Record.Builder;
import com.twilio.twiml.Say;
import com.twilio.twiml.VoiceResponse;
import com.cloudant.client.api.Database;

public class RecordServiceTask {
	@Autowired
	private static Database cloudantDatabase;

	public static String process(HttpServletRequest req, HttpServletResponse res,
			Map<String, Map<String, Object>> masterContext , Database db) throws Exception {

		String recordingUrl = req.getParameter("RecordingUrl");
		// Get twilio recording and conver to text using Watson StT
		String textResult = " ";
		
		if (recordingUrl != null) {
			try{
			    textResult = SpeechToTextTask.process(recordingUrl).trim();
			}catch(BadRequestException badRequestException){
				System.out.println("Caught BadRequestException:  " + badRequestException.getCause());
				textResult = SpeechToTextTask.process(recordingUrl).trim();
			}
		}
		
		System.out.println(textResult);

		// Fetch previous response context
		Map<String, Object> currentContext = masterContext.get("currentContext");
		
		//currentContext = ProcessUserDialogTask.process(currentContext, textResult, db);
		ConversationService service = new ConversationService("2017-02-03");
		service.setUsernameAndPassword("74a4f528-cefa-47cc-8d12-4ae80fbfcc13", "EJuGmkJ8DMXV");
		
		String workspaceId = "05a5344d-1cb6-40bc-9120-04567aa2fddf";
		
		MessageRequest newMessage = new MessageRequest.Builder().context(currentContext).inputText(textResult).build();
		MessageResponse convRes = service.message(workspaceId, newMessage).execute();
		String targetNodeName = "Date Of Birth";
		
		boolean isAccountExisted = false;
		String visitedNode = convRes.getOutput().get("nodes_visited").toString();
		if(visitedNode.contains(targetNodeName)) {
			String tempMemberID = convRes.getContext().get("memberID").toString();
			String tempTaxID = convRes.getContext().get("taxID").toString();	
			String tempDOB = convRes.getContext().get("DOB").toString();
			isAccountExisted = WebServiceTask.process(cloudantDatabase, tempMemberID, tempTaxID, tempDOB);
			//return isAccountExisted;
		} 
		//delete later
		String outputText = convRes.getTextConcatenated(" ");
		System.out.println("SpeechToText Output:     " + outputText);

		// Update context to latest response
		masterContext.put("currentContext", convRes.getContext());

		VoiceResponse voiceResponse = new VoiceResponse.Builder()
				.say(new Say.Builder(outputText).voice(Say.Voice.ALICE).build())
				.record(new Record.Builder().action("https://375580d3.ngrok.io/micro/record").method(Method.POST)
						.timeout(5).finishOnKey("#").maxLength(30).build())
				.build();

		// VoiceResponse voiceResponse = new VoiceResponse.Builder()
		// .redirect(new Redirect.Builder().url("/hold").build())
		// .build();

		return voiceResponse.toXml();
	}

}
