package com.tcs.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloudant.client.api.Database;
import com.tcs.app.entity.Customer;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

	@Autowired
	private Database dbArr[];

	private Database db;

	/*@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Customer findByID(@RequestParam(required = true) String _id) throws Exception {
		db = dbArr[0];
		Customer customer = FetchTask.processID(db, _id);

		return customer;

	}*/

	// Query customers for all documents or by ItemId
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<Customer> getAll() {
		db = dbArr[0];
		List<Customer> allCustomers = null;
		try {
			allCustomers = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Customer.class);
		} catch (Exception e) {
			System.out.println("Exception thrown : " + e.getMessage());
		}
		return allCustomers;
	}

}
