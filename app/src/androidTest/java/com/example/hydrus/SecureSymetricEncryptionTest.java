package com.example.hydrus;

import android.app.KeyguardManager;
import android.content.Context;;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.hydrus.security.encryption.EncryptedMessageIVPair;
import com.example.hydrus.security.encryption.SecureSymetricEncryption;
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
public class SecureSymetricEncryptionTest {

    @Test
    public void testKeypairGeneration() {
        boolean keypairSuccessfullyGenerated = SecureSymetricEncryption.initializeSymetricKeyWithKeyID("Key2");
        assertTrue(keypairSuccessfullyGenerated);
    }

    @Test
    public void testSymetricEncryption() {
        System.out.println("START");
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.hydrus", appContext.getPackageName());
        KeyguardManager keyguardManager =
                (KeyguardManager) appContext.getSystemService(appContext.KEYGUARD_SERVICE);
        if (!keyguardManager.isKeyguardSecure()) {
            System.out.println("Lock screen security not enabled in Settings");
        }
        PackageManager packageManager = appContext.getPackageManager();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            System.out.println("This Android version does not support fingerprint authentication.");
        }
        if(!packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
        {
            System.out.println("Fingerprint Sensor not supported");
        }
        System.out.println("REAL _ START");

        String keyID = "key3";
        boolean keypairSuccessfullyGenerated = SecureSymetricEncryption.initializeSymetricKeyWithKeyID(keyID);
        assertTrue(keypairSuccessfullyGenerated);
        byte[] byteTest = new byte[]{98,98,98,98};
        ConstantSecureMemory secureByteTest = ConstantSecureMemory.secureMemoryFromBytes(byteTest);
        Maybe<EncryptedMessageIVPair> e = SecureSymetricEncryption.encryptWithSecretKeyWithKeyID(secureByteTest, keyID);
        if(e.isNotNothing()) {
            Maybe<ConstantSecureMemory> d = SecureSymetricEncryption.decryptWithSecretKeyWithKeyID(e.object(), keyID);
            if(e.isNotNothing()) {
                assertEquals(98, d.object().getByteRepresentation()[0]);
                assertEquals(98, d.object().getByteRepresentation()[0]);
                assertEquals(98, d.object().getByteRepresentation()[0]);
                assertEquals(98, d.object().getByteRepresentation()[0]);
                return;
            }
        }
        assertTrue(false);
    }
}