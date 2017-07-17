package com.tcs.app.services.tasks;

import java.io.IOException;

import org.json.JSONObject;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.tcs.app.entity.Customer;

public class UpdateTask {

//	public static String process(Database database, JSONObject json){
	public static String process(Database database, Customer customer)throws Exception{
		Response response = database.update(customer);
		System.out.println("UPDATE RESPONSE: " + response.toString());
		return response.getId();
	}
	
}
