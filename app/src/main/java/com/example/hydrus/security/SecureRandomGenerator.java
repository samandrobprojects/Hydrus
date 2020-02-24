package com.example.hydrus.security;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import java.security.SecureRandom;
import java.util.Random;

public class SecureRandomGenerator {

    private static int _START_OF_ASCII_CHARACTERS = 97;
    private static int _NUMBER_OF_ASCII_CHARACTERS = 26;

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static byte[] generateRandomStringBytesWithLength(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomByteArray = new byte[length];
        for(int byteIndex = 0; byteIndex < length; byteIndex++) {
            byte[] rno = new byte[length];
            secureRandom.nextBytes(rno);
            int randomNumber =rno[0];
            randomNumber = Math.abs(randomNumber);
            Random random = new Random(randomNumber);
            randomNumber = random.nextInt(_NUMBER_OF_ASCII_CHARACTERS);
            randomNumber = randomNumber + _START_OF_ASCII_CHARACTERS;
            randomByteArray[byteIndex] = (byte) randomNumber;
        }
        return randomByteArray;
    }

    public static String generateRandomStringWithLength(int stringLength) {
        int randomCharacterCount = 0;
        String randomString = "";
        SecureRandom ranGen = new SecureRandom();
        while(randomCharacterCount < stringLength) {
            char randomCharacter = _generateRandomCharacterWithRandomGenerator(ranGen);
            randomString += Character.toString(randomCharacter);
            randomCharacterCount++;
        }
        return randomString;
    }

    public static int generateRandomNumber(int bound) {
        byte[] rno = new byte[4];
        SecureRandom  secureRandom = new SecureRandom();
        secureRandom.nextBytes(rno);
        int randomNumber =rno[0];
        randomNumber = Math.abs(randomNumber);
        Random random = new Random(randomNumber);
        randomNumber = random.nextInt(bound);
        return randomNumber;
    }

    public static char generateRandomCharacter() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] rno = new byte[4];
        secureRandom.nextBytes(rno);
        int randomNumber =rno[0];
        randomNumber = Math.abs(randomNumber);
        Random random = new Random(randomNumber);
        randomNumber = random.nextInt(_NUMBER_OF_ASCII_CHARACTERS);
        randomNumber = randomNumber + _START_OF_ASCII_CHARACTERS;
        char randomCharacter = (char) randomNumber;
        return randomCharacter;
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static char _generateRandomCharacterWithRandomGenerator(SecureRandom secureRandom) {
        byte[] rno = new byte[4];
        secureRandom.nextBytes(rno);
        int randomNumber =rno[0];
        randomNumber = Math.abs(randomNumber);
        Random random = new Random(randomNumber);
        randomNumber = random.nextInt(_NUMBER_OF_ASCII_CHARACTERS);
        randomNumber = randomNumber + _START_OF_ASCII_CHARACTERS;
        char randomCharacter = (char) randomNumber;
        return randomCharacter;
    }
}
