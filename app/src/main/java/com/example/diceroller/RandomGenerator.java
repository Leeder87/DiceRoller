package com.example.diceroller;

import java.security.SecureRandom;

public class RandomGenerator {
    // Крипторандомный генератор
    public static int generateInt(int max) {
        // create instance of SecureRandom class
        SecureRandom rand = new SecureRandom();

        // Generate random integers in range 1 to max
        // Я хочу сделать конфликт
        // Другой коммент для конфликта
        return rand.nextInt(max) + 1;
    }
}
