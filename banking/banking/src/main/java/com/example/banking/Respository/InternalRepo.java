package com.example.banking.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.banking.Entity.InternalBankBalance;
import com.example.banking.classed.OthersAccountdetails;

@Repository
public interface InternalRepo extends JpaRepository<InternalBankBalance,Integer>{
	
	@Query("SELECT b FROM InternalBankBalance b WHERE b.AccountNumber = :AccountNumber")
	InternalBankBalance findByAccountNumberofInternalBalance(String AccountNumber);
	
	
}
