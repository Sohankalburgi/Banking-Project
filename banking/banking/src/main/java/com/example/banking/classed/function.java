package com.example.banking.classed;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.banking.Entity.InternalBankBalance;
import com.example.banking.Services.InternalBankservices;

public class function {
	
	@Autowired
	InternalBankservices internalservice;
	
	public boolean checkAccount(String AccountNumb)
	{
		InternalBankBalance internal = internalservice.findAccount(AccountNumb);
		if(internal==null)
		{
			return false;
		}
		return true;
	}
}
