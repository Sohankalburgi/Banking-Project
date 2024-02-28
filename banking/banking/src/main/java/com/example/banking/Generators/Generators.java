package com.example.banking.Generators;

public class Generators {
    public static String CVVGenerators(int length)
    {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }

}
