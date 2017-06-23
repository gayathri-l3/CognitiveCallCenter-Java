//package com.tcs.app.services.tasks;
//
//import java.lang.reflect.Type;
//import java.util.Map;
//
//import com.cloudant.client.api.Database;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.tcs.app.entity.ConversationContext;
//import com.tcs.app.entity.User;
//
//public class ProcessUserDialogTask {
//
//	public static Map<String, Object> process(Map<String, Object> currentContext, String textResult, Database db) throws Exception {
//
//		String dialogNode = convertAndGetDialogNode(currentContext);
//		System.out.println(dialogNode);
//		// if dialog node = 4 or 5 or 7 add context variables, else remove
//		if (dialogNode.equalsIgnoreCase("node_4_1478537018719") || dialogNode.equalsIgnoreCase("node_1_1478897983605")) {
//			// formattedTextResult = NumberFormatter.process(textResult);
//			currentContext.put("taxId", textResult);
//		} else if (dialogNode.equalsIgnoreCase("node_5_1478537192359") || dialogNode.equalsIgnoreCase("node_2_1478898075943")) {
//			currentContext.put("memberId", textResult);
//		} else if (dialogNode.equalsIgnoreCase("node_7_1478537396787") || dialogNode.equalsIgnoreCase("node_5_1479316299030")) {
//			
//			boolean isDateValid = CheckDateOfBirthTask.process(textResult);
//			if (!isDateValid){
//				System.out.println("Date is NOT Valid:  " + isDateValid);
//				currentContext.put("isDateValid", false);
//				currentContext.put("dateOfBirth", "invalid");
//			} else {
//				System.out.println("Date is Valid:  " + isDateValid);
//				currentContext.put("isDateValid", true);
//				currentContext.put("dateOfBirth", textResult);
//			}
//		} else if (dialogNode.equalsIgnoreCase("node_9_1478537590359")) {
//			
//			Object memberId = currentContext.get("memberId");
//			System.out.println(memberId.toString().trim());
//			
//			User user = FetchUserTask.process(db, memberId.toString().trim());
//			if (null != user) {
//				currentContext.put("loginSuccessful", true);
//			} else {
//				currentContext.put("loginSuccessful", false);
//			}
//		}
//
//		return currentContext;
//	}
//
//	private static String convertAndGetDialogNode(Map<String, Object> currentContext) {
//
//		String currentSystemString = currentContext.get("system").toString();
//		System.out.println(currentSystemString);
//		Type contextType = new TypeToken<ConversationContext>() {
//		}.getType();
//		ConversationContext systemObject = new Gson().fromJson(currentSystemString, contextType);
//		String currentDialogNode = systemObject.getDialog_stack().get(0).getDialog_node();
//
//		return currentDialogNode;
//	}
//}
