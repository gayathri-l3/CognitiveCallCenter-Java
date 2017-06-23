package com.tcs.app.services.tasks;

import com.cloudant.client.api.Database;

public class WebServiceTask {
	
	public static boolean process(Database theCloudantDatabase, String theMemberID, String theTaxID, String theDOB) {
		
		return  VerifyAccountTask.verifyAccount(theCloudantDatabase,
				theMemberID,
				theTaxID,
				theDOB);
	}
}
