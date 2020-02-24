package com.example.hydrus;
import android.support.test.runner.AndroidJUnit4;

import com.example.hydrus.security.encryption.AsymmetricEncryption;
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
public class AsymetricEncryptionInternalTest {

    private static final String _TEST_KEY_ID = "TestKeyID";

    @Test
    public void testKeypairGeneration() {
        Boolean check = AsymmetricEncryption.initializeKeyPairWithKeyID(_TEST_KEY_ID);
        assertTrue(check);
    }

    @Test
    public void testEncryptAnsdDecrypt() {
        byte[] array = {98,98,98,98};
        Maybe<ConstantSecureMemory> maybePublicKey = AsymmetricEncryption.maybeGetPublicKeyWithKeyID(_TEST_KEY_ID);
        if(maybePublicKey.isNotNothing()) {
            Maybe<String> e = AsymmetricEncryption.maybeEncryptByPublicKeyWithByteData(array, maybePublicKey.object());
            if(e.isNotNothing()) {
                Maybe<byte[]> d = AsymmetricEncryption.maybeDecryptByPrivateKeyWithKeyID(e.object(), _TEST_KEY_ID);
                if(d.isNotNothing()) {
                    assertEquals((char) 98, d.object()[0]);
                    return;
                }
            }
        }
        assertTrue(false);
    }
}