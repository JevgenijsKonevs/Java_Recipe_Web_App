package com.bootcamp.nomnom.util;

public class StringGenerator {
    static String getRandomString() {
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(8);

        for(int i = 0; i < 8; i++) {
            int index = (int)(symbols.length() * Math.random());
            sb.append(symbols.charAt(index));
        }

        return sb.toString();
    }
}