package com.example.hydrus;

import android.support.test.runner.AndroidJUnit4;

import com.example.hydrus.security.encryption.SecureAsymmetricEncryption;
import com.example.hydrus.security.secure_memory.VariableSecureMemory;
import com.example.hydrus.security.secure_memory.ConstantSecureMemory;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SecureAsymmetricEncryptionTest {

    private static final String _TEST_KEY_ID = "TestKeyID";

    @Test
    public void testKeypairGeneration() {
        boolean keypairSuccessfullyGenerated = SecureAsymmetricEncryption.initializeKeyPairWithKeyID(_TEST_KEY_ID);
        assertTrue(keypairSuccessfullyGenerated);
    }

    @Test
    public void testEncryptAndDecrypt() {
        try {
            byte[] test_message_bytes = new byte[5];
            test_message_bytes[0] = (byte) 'h';
            test_message_bytes[1] = (byte) 'e';
            test_message_bytes[2] = (byte) 'l';
            test_message_bytes[3] = (byte) 'l';
            test_message_bytes[4] = (byte) 'o';
            VariableSecureMemory secureMessageBytes = VariableSecureMemory.secureBytesFromBytes(test_message_bytes);
            Maybe<ConstantSecureMemory> maybeMyPublicKey = SecureAsymmetricEncryption.maybeGetPublicKeyWithKeyID(_TEST_KEY_ID);
            if(maybeMyPublicKey.isNotNothing()) {
                Maybe<String> maybeEncryptedMessage = SecureAsymmetricEncryption.maybeEncryptSecureByteDataWithPublicKeySecurelyErasingData(secureMessageBytes, maybeMyPublicKey.object());
                if(maybeEncryptedMessage.isNotNothing()) {
                    Maybe<ConstantSecureMemory> maybeDecryptedMessageBytes = SecureAsymmetricEncryption.maybeDecryptStringByPrivateKeyWithKeyID(maybeEncryptedMessage.object(), _TEST_KEY_ID);
                    if(maybeDecryptedMessageBytes.isNotNothing()) {
                        assertEquals(maybeDecryptedMessageBytes.object().getByteRepresentation()[0], (byte) 'h');
                        return;
                    }
                }
            }
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}