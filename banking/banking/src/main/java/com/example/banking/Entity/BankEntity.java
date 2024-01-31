package com.example.banking.Entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class BankEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Pattern(regexp = "\\d{14}",message = "Enter Right Account Number")
	private String AccountNumber;
	
	@Pattern(regexp = "\\d{6}",message = "Enter the Security Code")
	private String SecurityCode;
	
	private double Balance;
	
	
	private String Name;
	
	
	private String Address;
	
	
	private String IFSC;
	
	@Pattern(regexp = "\\d{10}",message ="Enter the Correct Phone Number")
	private String PhoneNumber;
	
	
	public String getAccountNumber() {
		return AccountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}

	public String getSecurityCode() {
		return SecurityCode;
	}

	public void setSecurityCode(String securityCode) {
		SecurityCode = securityCode;
	}

	public double getBalance() {
		return Balance;
	}

	public void setBalance(double balance) {
		Balance = balance;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getIFSC() {
		return IFSC;
	}

	public void setIFSC(String iFSC) {
		IFSC = iFSC;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
}
