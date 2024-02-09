package com.example.banking.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banking.Entity.InternalBankBalance;
import com.example.banking.Respository.InternalRepo;
import com.example.banking.classed.OthersAccountdetails;



@Service
public class InternalBankservices {
	
	@Autowired
	InternalRepo internalrepo;
	
	// get the balance;
	public double getBalance(String AccountNumber)
	{
		InternalBankBalance obj = internalrepo.findByAccountNumberofInternalBalance(AccountNumber);
		
		return obj.getBalance();
	}
	
	//to find the account
	public InternalBankBalance findAccount(String AccountNumber)
	{
		return internalrepo.findByAccountNumberofInternalBalance(AccountNumber);
	}
	
	// to save
	public void save(InternalBankBalance internal)
	{
		internalrepo.save(internal);
	}

	public boolean checkAccountExist(String AccountNumber)
	{
		InternalBankBalance ibb = internalrepo.findByAccountNumberofInternalBalance(AccountNumber);
		if(ibb==null)
		{
			return false;
		}
		return true;
	}

	
	
}
