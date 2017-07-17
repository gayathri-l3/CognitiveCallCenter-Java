package com.tcs.app.entity;

public class Payment {

	 private Long account_num;
	 private String card_num;
	 private int month;
	 private int year;
	 
	 public Payment(){
		  super();
			// TODO Auto-generated constructor stub
	  }
	 
	 public Long getAccountNum(){
		 return this.account_num;
	 }
	 
	 public String getCardNum(){
		 return this.card_num;
	 }
	 
	 public int getMonth(){
		 return this.month;
	 }

	 public int getYear(){
		 return this.year;
	 }
	 
	 public void setAccountNum(Long account_num){
		 this.account_num = account_num;
	 }
	 
	 public void setCardNum(String card_num){
		 this.card_num = card_num;
	 }
	 
	 public void setMonth(int month){
		 this.month = month;
	 }
	 
	 public void setYear(int year){
		 this.year = year;
	 }
	 
//	 @Override
//	  public String toString() {
//		  return "Customer [Account Num=" + account_num + ", Card Num=" + card_num +  ", Month=" + month + ", Year=" + year + "]";
//	  }
}
