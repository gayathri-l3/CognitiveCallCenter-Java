package com.tcs.app.services.tasks;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.cloudant.client.api.Database;
import com.tcs.entities.Customer;

public class VerifyAccountTask {
	
	@Autowired
	
	public static boolean verifyAccount(Database theDB, String theMemberID, String theTaxID, String theDOB) {
		
		boolean isAccountExisted = false;
		String memberIDColumn = "memberId";
		String taxIDColumn = "taxId";
		String DOBColumn = "DOB";
		
		//System.out.println("About to look up memeber ID : " + theMemberID);
		//String sample = "\"selector\": { \"memberId\": \"123456789\"}";
		
		String query = "\"selector\": { \"" + memberIDColumn + "\": \"" + theMemberID + "\""
				+ ", \"" + taxIDColumn + "\": \"" + theTaxID + "\""
				+ ", \"" + DOBColumn + "\": \"" + theDOB + "\""
				+ "}";
		
		//System.out.println("sample : " + sample);
		//System.out.println("query : " + query);
		
		List<Customer> customerList = theDB.findByIndex(query, Customer.class);
		
		if(!customerList.isEmpty()) {
			isAccountExisted = true;
		}
		
		return isAccountExisted;
	}
}
