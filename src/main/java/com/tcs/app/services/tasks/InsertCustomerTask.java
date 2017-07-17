package com.tcs.app.services.tasks;

import java.util.List;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.google.gson.JsonObject;
import com.tcs.app.entity.Customer;
//Insert document into cloudant
public class InsertCustomerTask {
	public static void insertDoc(Database db, Customer cust)throws Exception{
		System.out.println("In insertDoc");
		JsonObject json = new JsonObject();
		json.addProperty("account_num", cust.getAccountNum());
		json.addProperty("emp_id", cust.getEmpID());
		json.addProperty("address", cust.getAddress());
		json.addProperty("email", cust.getEmail());
		json.addProperty("phone_num", cust.getPhoneNum());
		Response response =db.save(json);
		//will return _rev and _id
		System.out.println("Response: " + response);
		
	}
}
