package com.tcs.app.services.tasks;

import java.util.Map;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.twilio.twiml.Method;
import com.twilio.twiml.Record;
import com.twilio.twiml.Say;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;

public class StartServiceTask {

	public static String process(Map<String, Map<String, Object>> masterContext) throws TwiMLException {

		System.out.println("call-start ");

		ConversationService conversationService = new ConversationService("2017-02-03");
		conversationService.setUsernameAndPassword("74a4f528-cefa-47cc-8d12-4ae80fbfcc13", "EJuGmkJ8DMXV");

		MessageRequest newMessage = new MessageRequest.Builder().inputText("")
				// Replace with the context obtained from the initial request
				// .context(...)
				.build();
		
		String workspaceId = "05a5344d-1cb6-40bc-9120-04567aa2fddf";

		MessageResponse convRes = conversationService.message(workspaceId, newMessage).execute();

		String outputText = convRes.getTextConcatenated(" ");
		System.out.println(outputText);
		
//		String key = convRes.getContext().get("conversation_id").toString();
//		masterContext.put(key, convRes);
		
		masterContext.put("currentContext", convRes.getContext());
		

		VoiceResponse twiml = new VoiceResponse.Builder()
				.say(new Say.Builder(outputText)
						.voice(Say.Voice.ALICE).build())
				.record(new Record.Builder()
						.action("https://375580d3.ngrok.io/micro/record")
						.method(Method.POST)
						.timeout(3)
						.finishOnKey("#")
						.maxLength(30)
						.build())
				.build();

		return twiml.toXml();
	}
}
