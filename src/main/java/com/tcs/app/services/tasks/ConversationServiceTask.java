package com.tcs.app.services.tasks;

import java.util.Map;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

public class ConversationServiceTask {

	public static String process(Map<String, Map<String, Object>> masterContext, String inputText, String state){
		Map<String, Object> currentContext = null;
		if(state.equals("record")){
			currentContext = masterContext.get("currentContext");
			//System.out.println("Current Context : " + currentContext.toString() + " --------------------------");
		}
		ConversationService service = new ConversationService("2016-09-20");
		service.setUsernameAndPassword("eba74c07-ecbc-489f-afbc-74a243d3d177", "URgB2acIKbM1");
		MessageRequest newMessage;
		if(state.equals("record")){
		newMessage = new MessageRequest.Builder()
				.context(currentContext)
//				.inputText(" ")//textResult)
				.inputText(inputText)
				.build();
		} else {
		newMessage = new MessageRequest.Builder()
				//.context(currentContext)
//				.inputText(" ")//textResult)
				.inputText(inputText)
				.build();
		}
		String workspaceId = "f89f6aac-12ac-4f69-801f-dc1ebdfd4b9b";

		MessageResponse convRes = service.message(workspaceId, newMessage).execute();
		//System.out.println("=============================================== \n Converstion Res:  " + convRes.toString() + "\n ===============================================");

		String outputText = convRes.getTextConcatenated(" ");
		
		
		
		System.out.println("=============================================== \n Output Text:  " + outputText + "\n ===============================================");
	//	System.out.println(outputText);

		masterContext.put("currentContext", convRes.getContext());
		
		return outputText;
	}
}