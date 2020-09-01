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

    public static FateDieResult generateFudge() {
        SecureRandom rand = new SecureRandom();
        int randomResult = rand.nextInt(3);
        FateDieResult result = null;
        switch(randomResult) {
            case 0:
                result = FateDieResult.MINUS;
                break;

            case 1:
                result = FateDieResult.EMPTY;
                break;

            case 2:
                result = FateDieResult.PLUS;
                break;
        }
        return result;
    }
}
