package com.example.banking.Services;

import com.example.banking.Entity.CardEntity;
import com.example.banking.Respository.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    CardRepo cardrepo;


    public void createCard(CardEntity card){
        cardrepo.save(card);
    }

    public String getCardNumber(String AccountNumber){
        return cardrepo.findByAccountNumber(AccountNumber).getCardNumber();
    }

    public CardEntity getentityofcard(String AccountNumber){
        return cardrepo.findByAccountNumber(AccountNumber);
    }
}
