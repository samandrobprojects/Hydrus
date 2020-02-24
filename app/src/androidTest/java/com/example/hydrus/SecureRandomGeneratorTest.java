package com.example.hydrus;

import android.support.test.runner.AndroidJUnit4;

import com.example.hydrus.security.SecureRandomGenerator;
import com.example.hydrus.security.encryption.SecureSymetricEncryption;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SecureRandomGeneratorTest {

    private static int _START_OF_ASCII_CHARACTERS = 97;
    private static int _NUMBER_OF_ASCII_CHARACTERS = 26;

    @Test
    public void testRandomStringByteGeneration() {
        int randomStringByteArrayLength = 16;
        byte[] randomStringByteArray = SecureRandomGenerator.generateRandomStringBytesWithLength(randomStringByteArrayLength);
        for(int byteIndex = 0; byteIndex < randomStringByteArrayLength; byteIndex++) {
            assertTrue((char) randomStringByteArray[byteIndex] >= (char)_START_OF_ASCII_CHARACTERS);
            assertTrue((char) randomStringByteArray[byteIndex] <= (char)(_START_OF_ASCII_CHARACTERS+_NUMBER_OF_ASCII_CHARACTERS));
        }
    }
}