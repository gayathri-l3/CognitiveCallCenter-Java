package com.tcs.app.services.tasks;

import org.json.JSONObject;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;

public class InsertTask {
	
	public static String process(Database database, JSONObject json){
		Response response = database.save(json);
		return response.getId();
	}

}
