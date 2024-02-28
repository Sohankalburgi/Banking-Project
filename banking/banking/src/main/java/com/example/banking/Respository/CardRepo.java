package com.example.banking.Respository;

import com.example.banking.Entity.BankEntity;
import com.example.banking.Entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends JpaRepository<CardEntity,String> {

    @Query("SELECT b FROM CardEntity b WHERE b.AccountNumber = :AccountNumber")
    CardEntity findByAccountNumber(String AccountNumber);
}
