package com.tcs.app.services.tasks;


	
	import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

	import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

	import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cloudant.client.api.Database;
import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.tcs.app.Input;
import com.tcs.app.entity.Customer;
import com.tcs.app.entity.Payment;
import com.tcs.app.integration.DataAccessRouter;


	public class ChatTask {

		public static JsonObject process(HttpServletRequest req, HttpServletResponse res,
				Map<String, Map<String, Object>> masterContext,Input input, Database[] dbArr, Customer customer, Payment payment, SpeechToText speechToText) throws Exception {
			Map<String, Object> currentContext1 = null;
			input.setAlteredText("null");
			String state = null;
			String inputData = req.getReader().lines().collect(Collectors.joining());
			//String inputData = request.getParameter("inputdata");
			//System.out.println("input"+inputData.toString());
			System.out.println("inputSize"+inputData.length());
			if(inputData.length()>0){
			JSONParser parser = new JSONParser(); 
			JSONObject json = null;
			try {
				json = (JSONObject) parser.parse(inputData);
				System.out.println("json"+json.get("text"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (json.get("text") == null)
			{
			inputData = "";
			}
		else
		{
			inputData = json.get("text").toString();
			//String context = json.get("context").toString();
			//System.out.println("context"+context);
			//currentContext1= getContext(context);
			
			state = "record";
			
		}
			}
			else
			{
				inputData = "";	
				state="start";
			}
			
			input.setText(inputData);
			System.out.println(inputData);
			
			
			DataAccessRouter dataAccessRouter = new DataAccessRouter();
			String outputText = null;
			if(state.equalsIgnoreCase("record"))
			outputText = dataAccessRouter.process(input, masterContext, "record", dbArr, customer, payment, speechToText);
			
			else 
				outputText = dataAccessRouter.process(input, masterContext, "start", dbArr, customer, payment, speechToText);

			// Fetch previous response context
			
			
			//delete later
			//String outputText = convRes.getTextConcatenated(" ");
			System.out.println("Output:     " + outputText);
			JsonObject outJson = new JsonObject();
			outJson.addProperty("outputtext", outputText);
			////outJson.addProperty("context", convRes.getContext().toString());
			

			// Update context to latest response
			//masterContext.put("currentContext", convRes.getContext());

			

			return outJson;
		}

		private static Map<String, Object> getContext(String context) {
			int index = context.indexOf(",");
			int index1 = context.indexOf("=");
			String id = context.substring(index1+1, index);
			int len= context.length();
			String system = context.substring(index+1,len-1);
			System.out.println("id"+id+"system"+system);
			index1 = system.indexOf("{");
			system=system.substring(index1);
			Map<String,Object> contecMap = new HashMap<String,Object>();
			contecMap.put("system", system);
			contecMap.put("conversation_id", id);
			//contecMap.put("system", system);
			System.out.println("Map"+contecMap.toString());
			return contecMap;
		}

	}



