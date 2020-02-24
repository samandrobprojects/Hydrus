package com.example.hydrus.security.secure_memory;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import com.example.hydrus.security.SecureRandomGenerator;

import java.util.ArrayList;

public class SecureEraser {

    private static int _DELETE_ITERATIONS_FOR_SECURE_DATA_ERASING = 120;
    private static int _DELETE_ITERATIONS_FOR_ERASING_RAM = 2;

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    public static void eraseByteArray(byte[] byteArrayToErase) {
        if (!(byteArrayToErase == null)) {
            for (int numberDeleteIterations = 0; numberDeleteIterations < _DELETE_ITERATIONS_FOR_SECURE_DATA_ERASING; numberDeleteIterations++) {
                randomizeBytes(byteArrayToErase);
            }
        }
    }

    public static void eraseCharArray(char[] charArrayToErase) {
        if (!(charArrayToErase == null)) {
            for(int numberDeleteIterations = 0; numberDeleteIterations < _DELETE_ITERATIONS_FOR_SECURE_DATA_ERASING; numberDeleteIterations++) {
                randomizeCharacters(charArrayToErase);
            }
        }
    }

    public static void eraseAllApplciationMemory() {
        for(int ramEraseIterationNumber = 0; ramEraseIterationNumber < _DELETE_ITERATIONS_FOR_ERASING_RAM; ramEraseIterationNumber++) {
            wipeMemoryToCharacter((char) (ramEraseIterationNumber+96));
        }
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static void randomizeCharacters(char[] charsToRandomize) {
        for (int characterIndex = 0; characterIndex < charsToRandomize.length; characterIndex++) {
            charsToRandomize[characterIndex] = SecureRandomGenerator.generateRandomCharacter();
        }
    }

    private static void randomizeBytes(byte[] bytesToRandomize) {
        for (int characterIndex = 0; characterIndex < bytesToRandomize.length; characterIndex++) {
            bytesToRandomize[characterIndex] = (byte) SecureRandomGenerator.generateRandomCharacter();
        }
    }

    private static void wipeMemoryToCharacter(char characterToWipeTo) {
        ArrayList<MemoryWipeObject> potentiallyInfiniteList = new ArrayList<MemoryWipeObject>();
        try {
            while(true) {
                MemoryWipeObject objectForWipe = new MemoryWipeObject();
                objectForWipe.memoryByteToSet = (byte) characterToWipeTo;
                potentiallyInfiniteList.add(objectForWipe);
            }
        } catch(OutOfMemoryError e) {
            return;
        }
    }
}
