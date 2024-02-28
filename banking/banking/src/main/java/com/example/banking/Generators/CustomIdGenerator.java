package com.example.banking.Generators;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class CustomIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        // Generate a 14-digit ID
        StringBuilder sb = new StringBuilder();
        sb.append(generateRandomDigits(16)); // Generate 11 random digits

        return sb.toString();
    }

    // Method to generate random digits
    private String generateRandomDigits(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((int)(Math.random() * 10)); // Append a random digit (0-9)
        }
        return sb.toString();
    }
}
