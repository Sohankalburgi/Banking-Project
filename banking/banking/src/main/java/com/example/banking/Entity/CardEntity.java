package com.example.banking.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
public class CardEntity {

    @Id
    @GenericGenerator(name = "custom-id", strategy = "com.example.banking.Generators.CustomIdGenerator")
    @GeneratedValue(generator = "custom-id")
    private String CardNumber;


    private String CVV;


    private String validity;

    private String PIN;

    private String Type;

    private String LimitofTransaction;

    private String AccountNumber;

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLimitofTransaction() {
        return LimitofTransaction;
    }

    public void setLimitofTransaction(String limitofTransaction) {
        LimitofTransaction = limitofTransaction;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }
}
