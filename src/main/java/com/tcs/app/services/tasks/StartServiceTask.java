package com.tcs.app.services.tasks;

import java.util.Map;

import com.cloudant.client.api.Database;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.tcs.app.Input;
import com.tcs.app.entity.Customer;
import com.tcs.app.entity.Payment;
import com.tcs.app.integration.DataAccessRouter;
import com.twilio.twiml.Method;
import com.twilio.twiml.Record;
import com.twilio.twiml.Say;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;

public class StartServiceTask {

	public static String process(Map<String, Map<String, Object>> masterContext, Input input, Database[] dbArr, Customer customer, Payment payment, SpeechToText speechToText) throws Exception {
		input.setText("null");
		input.setId("null");
		input.setAlteredText("null");
		customer = null;
		System.out.println("call-start ");

		//String outputText = ConversationServiceTask.process(masterContext, "", "start");
		DataAccessRouter dataAccessRouter = new DataAccessRouter();
		String outputText = dataAccessRouter.process(input, masterContext, "start", dbArr, customer, payment, speechToText);
		VoiceResponse twiml = new VoiceResponse.Builder()
				.say(new Say.Builder(outputText)
						.voice(Say.Voice.ALICE).build())
				.record(new Record.Builder()
						//.action("https://c6213161.ngrok.io/micro/record")
						.action("https://callcenterservice.mybluemix.net/micro/record")
						.method(Method.POST)
						.timeout(3)
						.finishOnKey("#")
						.maxLength(30)
						.build())
				.build();

		return twiml.toXml();
	}
}
