package com.example.banking.Services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.banking.Entity.BankEntity;
import com.example.banking.Respository.BankRepository;

public class DBServices {
	
	@Autowired
	BankRepository bankrepo;
	
	// Create an New Account 
	public void createAccount(BankEntity bankdetails) {
		bankrepo.save(bankdetails);
	}
}
