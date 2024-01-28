package com.example.banking.classed;

public class OthersAccountdetails {
	
	private String AccountNumber;
	
	private String OtherAccountNumber;
	
	private double Amount;
	
	public String getOtherAccountNumber() {
		return OtherAccountNumber;
	}
	public void setOtherAccountNumber(String otherAccountNumber) {
		OtherAccountNumber = otherAccountNumber;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = Double.parseDouble(amount);
	}
	public String getAccountNumber() {
		return AccountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}
	
	
	
	
}
