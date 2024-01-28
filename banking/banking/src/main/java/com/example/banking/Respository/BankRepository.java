package com.example.banking.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.banking.Entity.BankEntity;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Integer>{
	
	 @Query("SELECT b FROM BankEntity b WHERE b.AccountNumber = :AccountNumber")
	 BankEntity findByAccountNumber(String AccountNumber);

}

