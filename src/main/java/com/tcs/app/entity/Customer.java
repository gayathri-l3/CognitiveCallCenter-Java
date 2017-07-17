package com.tcs.app.entity;

public class Customer {

  private String _rev;
  private String _id;
  private Long  account_num;
  private Long emp_id;
  private String emp_fName;
  private String emp_lName;
  private String address;
  private String email;
  private Long phone_num;
  private int[] order_id;

  public Customer(){
	  super();
		// TODO Auto-generated constructor stub
  }

  public String get_Id() {
    return this._id;
  }

  public void set_Id(String id) {
    this._id = id;
  }
  
  public String get_Rev() {
	  return this._rev;
  }
  
  public void set_Rev(String rev) {
	  this._rev = rev;
  }

  public Long getAccountNum() {
    return this.account_num;
  }

  public void setAccountNum(Long account_num) {
    this.account_num = account_num;
  }

  public Long getEmpID() {
    return this.emp_id;
  }

  public void setEmpID(Long emp_id) {
    this.emp_id = emp_id;
  }
  
  public String getFname() {
	    return this.emp_fName;
	  }

  public void setLName(String emp_lName) {
	    this.emp_lName = emp_lName;
	  }
  public String getLname() {
	    return this.emp_lName;
	  }

public void setFName(String emp_fName) {
	    this.emp_fName = emp_fName;
	  }  
  

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getPhoneNum() {
    return this.phone_num;
  }

  public void setPhoneNum(Long phone_num) {
    this.phone_num = phone_num;
  }
  
  public int[] getOrders(){
	  System.out.println("in get orders");
	  return this.order_id;
  }
  
  public void setOrders(int[] order_id){
	 this.order_id=order_id;
  }

  @Override
  public String toString() {
	  return "Customer [_rev=" + _rev + ", _id=" + _id + ", account_num=" + account_num + ", employee_id=" + emp_id
			  + ", address=" + address + ", email=" + email + ", orders=" + order_id + "]";
  }

}
