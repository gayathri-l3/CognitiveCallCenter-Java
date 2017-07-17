package com.tcs.app.services.tasks;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.json.JSONObject;

import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.tcs.app.entity.Customer;

public class FetchTask {

	//grab document given an id
	public static JSONObject fetchDocumentByID(Database db, String id) throws Exception {
		System.out.println("In fetchDocumentByID with id: " + id);
		
		//Customer customer = null;
		JSONObject jsonResponse = null;
		if (!id.equals("null")) {
			InputStream inputStream = db.find(id);
			String responseString = IOUtils.toString(inputStream);
			jsonResponse = new JSONObject(responseString);
			System.out.println(jsonResponse.toString());
		}
		return jsonResponse;
	}

	//grab document given phone number 
	public static Customer fetchCustomerByPhone(Database db, Long phone_num) throws Exception {
		System.out.println("In fetchCustomerByPhone with Phone Number: " + phone_num);
		
		Customer customer = null;
		try{
		if (phone_num != null) {
			List<Customer> customers = null;
			String query = "{ \n \"selector\": {\n\"_id\": { \n \"$gt\":0 \n }, \n\"phone_num\":  " + phone_num + "  \n}\n}";
			customers = db.findByIndex(query, Customer.class);
			if (customers.size() > 0) {
				customer = customers.get(0);
			}
			System.out.println("Customers: " + customer);
		}
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		return customer;
	}
	
	//grab document given account number 
	public static Customer fetchCustomerByAccount(Database db, Long account_num) throws Exception {
		System.out.println("In fetchCustomerByAccount with Account Number: " + account_num);
		
		Customer customer = null;
		try{
		if (account_num != null) {
			List<Customer> customers = null;
			String query = "{ \n \"selector\": {\n\"_id\": { \n \"$gt\":0 \n }, \n\"emp_id\":  " + account_num + "  \n}\n}";
			customers = db.findByIndex(query, Customer.class);
			if (customers.size() > 0) {
				customer = customers.get(0);
			}
	//		System.out.println("Customer: " + customer);
		}
		}catch(NullPointerException e){
			e.printStackTrace();
			
		}
		return customer;
	}
	
	public static Response updateCustomerOrder(Database db, Customer customer, Long orderID)throws Exception{

		if(customer== null){
			return null;
		}
		List<Customer> customers = null;
		String selector =  "{ \n \"selector\": {\n\"_id\": { \n \"$gt\":0 \n }, \n\"emp_id\":  " + customer.getAccountNum() + "  \n}\n}";
		customers = db.findByIndex(selector, Customer.class);

		
		Customer c = customers.get(0);
		
		int[] orders = c.getOrders();
		for(int i=0; i< orders.length;i++ ){
			System.out.println("\n\nOrders beforehand : " + orders[i]);
		}
		for(int i=0; i< orders.length;i++ ){
			if(orderID == orders[i]){
				System.out.println("It matched");
				orders = ArrayUtils.remove(orders, i);
			
			}
		}

		c.setOrders(orders);
		db.update(c);
		return db.update(c);	
		
	}
	
	public static Response updateCustomerAddress(Database db, Customer customer, String address)throws Exception{

		if(customer== null){
			return null;
		}
		List<Customer> customers = null;
		String selector =  "{ \n \"selector\": {\n\"_id\": { \n \"$gt\":0 \n }, \n\"emp_id\":  " + customer.getAccountNum() + "  \n}\n}";
		customers = db.findByIndex(selector, Customer.class);
		Customer c = customers.get(0);
		c.setAddress(address);;
		db.update(c);
		return db.update(c);	
		
	}
	
	public static Response updateCustomerPhone(Database db, Customer customer, Long phone)throws Exception{

		if(customer== null){
			return null;
		}
		List<Customer> customers = null;
		String selector =  "{ \n \"selector\": {\n\"_id\": { \n \"$gt\":0 \n }, \n\"emp_id\":  " + customer.getAccountNum() + "  \n}\n}";
		customers = db.findByIndex(selector, Customer.class);
		Customer c = customers.get(0);
		c.setPhoneNum(phone);;
		db.update(c);
		return db.update(c);	
		
	}
	
	public static Response updateCustomerEmail(Database db, Customer customer, String email)throws Exception{

		if(customer== null){
			return null;
		}
		List<Customer> customers = null;
		String selector =  "{ \n \"selector\": {\n\"_id\": { \n \"$gt\":0 \n }, \n\"emp_id\":  " + customer.getAccountNum() + "  \n}\n}";
		customers = db.findByIndex(selector, Customer.class);
		Customer c = customers.get(0);
		c.setEmail(email);;
		db.update(c);
		return db.update(c);	
		
	}
}