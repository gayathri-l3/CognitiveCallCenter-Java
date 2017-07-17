package com.tcs.app.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudant.client.api.Database;
import com.tcs.app.entity.Customer;

@Service
public class CustomerDao {
	
	@Autowired
	private Database[] dbArr;
	
	private Database db;

	public List<Customer> getAllUsers(){
		List<Customer> allCusomters = null;
		db = dbArr[0];
		try
		{
			allCusomters = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse()
					.getDocsAs(Customer.class);
		} catch (Exception e) {
			System.out.println("Exception thrown : " + e.getMessage());
		}
		return allCusomters;
	}
}
