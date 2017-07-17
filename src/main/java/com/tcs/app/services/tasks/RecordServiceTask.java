package com.tcs.app.services.tasks;

import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.service.exception.BadRequestException;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.tcs.app.Input;
import com.tcs.app.entity.ConversationContext;
import com.tcs.app.entity.Customer;
import com.tcs.app.entity.Payment;
import com.tcs.app.integration.DataAccessRouter;
import com.twilio.twiml.Method;
import com.twilio.twiml.Record;
//import com.twilio.twiml.Record.Builder;
import com.twilio.twiml.Say;
import com.twilio.twiml.VoiceResponse;


public class RecordServiceTask {

	public static String process(HttpServletRequest req, HttpServletResponse res,
			Map<String, Map<String, Object>> masterContext, Input input, Database[] dbArr, Customer customer, Payment payment, SpeechToText speechToText) throws Exception {
		System.out.println("Currently in call-record");
		input.setAlteredText("null");
		String recordingUrl = req.getParameter("RecordingUrl");		
		String textResult = " ";
		
		if (recordingUrl != null) {
			try{
			    textResult = SpeechToTextTask.process(recordingUrl.trim(), speechToText);
			    System.out.println("TextResult : " + textResult);
			}catch(BadRequestException badRequestException){
				try{
				System.out.println("Caught BadRequestException:  " + badRequestException.getCause());
				textResult = SpeechToTextTask.process(recordingUrl.trim(), speechToText);
				}catch(BadRequestException badRequestException2){
					System.out.println("Caught BadRequestException2:  " + badRequestException.getCause());
					textResult = "garbage";
				}
			}
		}
		
		System.out.println("SpeechToText Result: " + textResult);
		input.setText(textResult);
		DataAccessRouter dataAccessRouter = new DataAccessRouter();
		String outputText = dataAccessRouter.process(input, masterContext, "record", dbArr, customer, payment, speechToText);
		//String outputText = ConversationServiceTask.process(masterContext, textResult, "record");
		VoiceResponse voiceResponse = new VoiceResponse.Builder()
				.say(new Say.Builder(outputText)
						.voice(Say.Voice.ALICE).build())
				.record(new Record.Builder()
						.action("https://callcenterservice.mybluemix.net/micro/record")
						//.action("https://c6213161.ngrok.io/micro/record")
						.method(Method.POST)
						.timeout(10)
						.finishOnKey("#")
						.maxLength(30)
						.build())
				.build();
		
//		VoiceResponse voiceResponse = new VoiceResponse.Builder()
//				.redirect(new Redirect.Builder().url("/hold").build())
//				.build();

		return voiceResponse.toXml();
	}
	
	private static String convertAndGetDialogNode(Map<String, Object> currentContext){
	
		String currentSystemString = currentContext.get("system").toString();
		
		Type contextType = new TypeToken<ConversationContext>(){}.getType();
		ConversationContext systemObject = new Gson().fromJson(currentSystemString, contextType);
		String currentDialogNode = systemObject.getDialog_stack().get(0).getDialog_node();
		
		return currentDialogNode;
	}
}
