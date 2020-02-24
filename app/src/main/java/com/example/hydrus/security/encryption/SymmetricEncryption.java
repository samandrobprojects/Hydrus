package com.example.hydrus.security.encryption;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.example.hydrus.utilities.Base64Converter;
import com.example.hydrus.utilities.hydrus_exceptions.EncryptionKeyException;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import functional.Maybe;

public class SymmetricEncryption {

    private static final String KEYSTORE = "AndroidKeyStore";
    private static final String SYMETRIC_KEY_ALIAS = "AndroidKeyStoreHydrusSymetricKeyTest11";
    private static final String _AES_ALGORITHM = "AES/CBC/PKCS7Padding";

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static SecretKey getSymetricKeyWithID(String keyID) {
        try {
            return _attemptToGetSymetricKeyWithKeyID(keyID);
        } catch (Exception e) {
            throw new EncryptionKeyException("Failed To Get Symmetric Key");
        }
    }

    public static boolean initializeSymetricKeyWithKeyID(String keyID) {
        try {
            return _attemptToCreateSymetricKeyWithID(keyID);
        } catch (Exception e) {
            throw new EncryptionKeyException("Failed To Initialize Symetric Key");
        }
    }

    public static Maybe<EncryptedMessageIVPair> maybeEncryptWithSecretKeyWithKeyID(byte[] bytesToEncrypt, String keyID) {
        try {
            return Maybe.asObject(_attemptToEncryptWithSecretKeyWithKeyID(bytesToEncrypt, keyID));
        } catch (Exception exception) {
            return Maybe.asNothing();
        }
    }

    public static Maybe<byte[]> maybeDecryptWithSecretKeyWithKeyID(EncryptedMessageIVPair encryptedMessageIVPair, String keyID) {
        try {
            return Maybe.asObject(_attemptToDecryptWithSecretKeyWithKeyID(encryptedMessageIVPair, keyID));
        }  catch (Exception exception) {
            return Maybe.asNothing();
        }
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static boolean _attemptToCreateSymetricKeyWithID(String keyID) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE);
        keyGenerator.init(
                new KeyGenParameterSpec.Builder(keyID,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .setIsStrongBoxBacked(true)
                        //.setUserAuthenticationRequired(true)              // DEBUG TODO: Add back in
                        //.setUserAuthenticationValidityDurationSeconds(-1) // DEBUG TODO: ADD BACK IN
                        .build());
        SecretKey key = keyGenerator.generateKey();
        return true;
    }

    private static SecretKey _attemptToGetSymetricKeyWithKeyID(String keyID) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEYSTORE);
        keyStore.load(null);
        return (SecretKey) keyStore.getKey(keyID, null);
    }

    private static EncryptedMessageIVPair _attemptToEncryptWithSecretKeyWithKeyID(byte[] bytesToEncrypt, String keyID) throws Exception {
        SecretKey symetricKey = getSymetricKeyWithID(keyID);
        Cipher cipher = Cipher.getInstance(_AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, symetricKey);
        String encryptedBytes = Base64Converter.encryptBASE64(cipher.doFinal(bytesToEncrypt));
        String ivString = Base64Converter.encryptBASE64(cipher.getIV());
        return EncryptedMessageIVPair.getPairWithEncryptedMessageAndIV(encryptedBytes, ivString);
    }

    private static byte[] _attemptToDecryptWithSecretKeyWithKeyID(EncryptedMessageIVPair encryptedMessageIVPair, String keyID) throws Exception {
        String ivString = encryptedMessageIVPair.getIV();
        String encryptedMessage = encryptedMessageIVPair.getEncryptedMessage();
        byte[] ivBytes = Base64Converter.decryptBASE64(ivString);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        byte[] bytesToDecrypt = Base64Converter.decryptBASE64(encryptedMessage);
        SecretKey symetricKey = getSymetricKeyWithID(keyID);
        Cipher cipher = Cipher.getInstance(_AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, symetricKey, ivSpec);
        return cipher.doFinal(bytesToDecrypt);
    }
}
