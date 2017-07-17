package com.tcs.app;

public class Rule {

	private Boolean accountLookup;
	private Boolean orderLookup;
	private Boolean invoicePriceLookup;
	private Boolean invoiceLookup;
	private Boolean addressLookup;
	private Boolean phoneLookup;
	private Boolean emailLookup;
	private Boolean allRulesFalse;
	
	public Rule(){
		this.accountLookup = false;
		this.orderLookup = false;
		this.invoicePriceLookup= false;
		this.invoiceLookup= false;
		this.addressLookup = false;
		this.phoneLookup = false;
		this.emailLookup = false;
		this.allRulesFalse = true;

	}
	
	public Boolean getAddressLookup() {
		return addressLookup;
	}

	public void setAddressLookup(Boolean addressLookup) {
		this.addressLookup = addressLookup;
	}

	public Boolean getPhoneLookup() {
		return phoneLookup;
	}

	public void setPhoneLookup(Boolean phoneLookup) {
		this.phoneLookup = phoneLookup;
	}

	public Boolean getEmailLookup() {
		return emailLookup;
	}

	public void setEmailLookup(Boolean emailLookup) {
		this.emailLookup = emailLookup;
	}

	public void setAccountLookup(boolean accountLookup){
		this.accountLookup = accountLookup;
	}
	
	public void setOrderLookup(boolean orderLookup){
		this.orderLookup = orderLookup;
	}
	

	
	public void setAllRulesFalse(boolean allRulesFalse){
		this.allRulesFalse = false;
	}

	public Boolean getAccountLookup(){
		return this.accountLookup;
	}
	
	public Boolean getOrderLookup(){
		return this.orderLookup;
	}
	
	public Boolean getInvoiceLookup(){
		return this.invoiceLookup;
	}
	
	public void setInvoiceLookup(boolean invoiceLookup){
		this.invoiceLookup = invoiceLookup;
	}
	
	public Boolean getInvoicePriceLookup(){
		return this.invoicePriceLookup;
	}
	
	public void setInvoicePriceLookup(boolean invoicePriceLookup){
		this.invoicePriceLookup = invoicePriceLookup;
	}

	
	public Boolean getAllRulesFalse(){
		return this.allRulesFalse;
	}
	
	
	
	
}
