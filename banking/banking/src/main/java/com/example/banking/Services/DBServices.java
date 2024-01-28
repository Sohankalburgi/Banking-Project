package com.example.banking.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banking.Entity.BankEntity;
import com.example.banking.Respository.BankRepository;

@Service
public class DBServices {
	
	@Autowired
	BankRepository bankrepo;
	
	// Create an New Account 
	public void createAccount(BankEntity bankdetails) {
		bankrepo.save(bankdetails);
	}
	
	
	//find account number
	public BankEntity findaccountdetails(String AccountNumber)
	{
		return bankrepo.findByAccountNumber(AccountNumber);
	}
	
	// Update details
	public void UpdateAccountdetails(BankEntity bankentity,String AccountNumber)
	{
		BankEntity obj = bankrepo.findByAccountNumber(AccountNumber);
		obj.setName(bankentity.getName());
		obj.setAddress(bankentity.getAddress());
		obj.setSecurityCode(bankentity.getSecurityCode());
		obj.setPhoneNumber(bankentity.getPhoneNumber());
		bankrepo.save(obj);
	}
	
	
}
