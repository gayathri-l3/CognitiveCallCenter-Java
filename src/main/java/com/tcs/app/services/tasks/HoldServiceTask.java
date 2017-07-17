package com.tcs.app.services.tasks;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.twilio.twiml.Record.Builder;
import com.twilio.twiml.Say;
import com.twilio.twiml.VoiceResponse;

public class HoldServiceTask {

public static void process(HttpServletRequest req, HttpServletResponse res, Map<String, Map<String, Object>> masterContext){
		
	System.out.println(req.getParameterNames());
	
	ConversationService service = new ConversationService("2017-06-20");
	service.setUsernameAndPassword("eba74c07-ecbc-489f-afbc-74a243d3d177", "URgB2acIKbM1");

	MessageRequest newMessage = new MessageRequest.Builder()
			.inputText("")
			// Replace with the context obtained from the initial request
			//.context(...)
			.build();

	String workspaceId = "f89f6aac-12ac-4f69-801f-dc1ebdfd4b9b";

	MessageResponse convRes = service
			.message(workspaceId, newMessage)
			.execute();
	
	String outputText = convRes.getTextConcatenated(" ");
	System.out.println(outputText);
	
	String recordingUrl = req.getParameter("RecordingUrl");
	
	//Record Builder
	Builder recBuilder = new Builder();
	recBuilder = recBuilder.timeout(3);
	recBuilder = recBuilder.action("/micro/record");
	
	VoiceResponse twiml = new VoiceResponse.Builder()
			.say(new Say.Builder(outputText)
					.voice(Say.Voice.ALICE)
					.build())
			.record(recBuilder.build())
			.build();	

	}
	
	
}
