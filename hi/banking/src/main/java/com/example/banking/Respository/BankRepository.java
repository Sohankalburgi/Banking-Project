package com.example.banking.Respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.banking.Entity.BankEntity;

public interface BankRepository extends JpaRepository<BankEntity, String>{
	

}
