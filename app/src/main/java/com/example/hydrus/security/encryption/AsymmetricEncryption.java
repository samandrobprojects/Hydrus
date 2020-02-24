package com.example.hydrus.security.encryption;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.example.hydrus.security.secure_memory.ConstantSecureMemory;
import com.example.hydrus.utilities.Base64Converter;
import com.example.hydrus.utilities.hydrus_exceptions.EncryptionKeyException;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

import functional.Maybe;

// TODO: Salted

public class AsymmetricEncryption {

    private static final String _KEYSTORE = "AndroidKeyStore";
    //private static final String _PRIVATE_KEY_ALIAS = "AndroidKeyStoreHydrusPrivateKeyM";
    private static final String _KEY_ALGORITHM = "RSA";
    private static final String _CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final String _SIGNATURE_ALGORITHM = "SHA256withRSA";

    //-------------------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //-------------------------------------------------------------------------------------------------
    public static Maybe<String> maybeSignBytesWithKeyID(byte[] data, String keyID) {
        try {
            return Maybe.asObject(_signBytesWithKeyID(data, keyID));
        } catch (Exception exception) {
            return Maybe.asNothing();
        }
    }

    public static Maybe<String> maybeSignStringDataWithKeyID(String stringData, String keyID) {
        byte[] data = Base64Converter.decryptBASE64(stringData);
        return maybeSignBytesWithKeyID(data, keyID);
    }

    public static boolean verifyBytes(byte[] data, String publicKey, String sign) {
        try {
            byte[] keyBytes = Base64Converter.decryptBASE64(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(_KEY_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(_SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            signature.update(data);
            return signature.verify(Base64Converter.decryptBASE64(sign));
        } catch (Exception exception) {
            return false;
        }
    }

    public static boolean verify(String stringData, String publicKey, String sign) {
        byte[] data = Base64Converter.decryptBASE64(stringData);
        return verifyBytes(data, publicKey, sign);
    }

    public static Maybe<byte[]> maybeDecryptByPrivateKeyWithKeyID(String stringData, String keyID) {
        try {
            return Maybe.asObject(_decryptByPrivateKeyWithKeyID(stringData,keyID));
        } catch (Exception exception) {
            return Maybe.asNothing();
        }
    }

    public static Maybe<String> maybeEncryptByPublicKeyWithByteData(byte[] data, ConstantSecureMemory publicKey) {
        try {
            return Maybe.asObject(_encryptByPublicKeyWithByteData(data,publicKey));
        } catch (Exception exception) {
            return Maybe.asNothing();
        }
    }

    public static Maybe<ConstantSecureMemory> maybeGetPublicKeyWithKeyID(String keyID) {
        try {
            return Maybe.asObject(_getPublicKeyWithID(keyID));
        } catch (Exception exception) {
            return Maybe.asNothing();
        }
    }

    public static boolean initializeKeyPairWithKeyID(String keyID) {
        try {
            _initializeKeypairWithKeyID(keyID);
            return true;
        } catch (Exception e) {
            throw new EncryptionKeyException("Failed To Initialize Asymetric Key");
        }
    }

    //-------------------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //-------------------------------------------------------------------------------------------------
    private static String _signBytesWithKeyID(byte[] data, String keyID) throws Exception {
        Signature signature = Signature.getInstance(_SIGNATURE_ALGORITHM);
        signature.initSign(_getPrivateKeyWithKeyID(keyID));
        signature.update(data);
        return Base64Converter.encryptBASE64(signature.sign());
    }

    private static byte[] _decryptByPrivateKeyWithKeyID(String stringData, String keyID) throws Exception {
        byte[] data = Base64Converter.decryptBASE64(stringData);
        Cipher cipher = Cipher.getInstance(_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, _getPrivateKeyWithKeyID(keyID), new SecureRandom());
        return cipher.doFinal(data);
    }

    private static String _encryptByPublicKeyWithByteData(byte[] data, ConstantSecureMemory publicKey) throws Exception {
        byte[] keyBytes = publicKey.getByteRepresentation();
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(_KEY_ALGORITHM);
        Key publicKeyasKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,publicKeyasKey, new SecureRandom());
        String retval = Base64Converter.encryptBASE64(cipher.doFinal(data));
        return retval;
    }

    private static ConstantSecureMemory _getPublicKeyWithID(String keyID) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(_KEYSTORE);
        keyStore.load(null);
        PublicKey publicKey = keyStore.getCertificate(keyID).getPublicKey();
        byte[] keyBytes = publicKey.getEncoded();
        return ConstantSecureMemory.secureMemoryFromBytes(keyBytes);
    }

    private static void _initializeKeypairWithKeyID(String keyID) throws Exception {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            _attemptToCreateSecurityKeyWithKeyID(keyID);
        } else {
            throw new EncryptionKeyException("Failed To Initialize Asymetric Key");
        }
    }

    private static PrivateKey _getPrivateKeyWithKeyID(String keyID) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(_KEYSTORE);
        keyStore.load(null);
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyID, null);
        return privateKey;
    }

    private static void _attemptToCreateSecurityKeyWithKeyID(String keyID) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA, _KEYSTORE);
        KeyGenParameterSpec keyGeneratorParameterSpecification;
        keyGeneratorParameterSpecification = getKeyGenParameterSpecForStrongBoxSecureKeyWithKeyID(keyID);
        keyPairGenerator.initialize(keyGeneratorParameterSpecification, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
    }

    private static KeyGenParameterSpec getKeyGenParameterSpecForStrongBoxSecureKeyWithKeyID(String keyID) {
        Calendar start = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        end.add(Calendar.YEAR, 1);
        KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(
                keyID,
                KeyProperties.PURPOSE_ENCRYPT |
                        KeyProperties.PURPOSE_DECRYPT |
                        KeyProperties.PURPOSE_SIGN |
                        KeyProperties.PURPOSE_VERIFY);
        builder.setIsStrongBoxBacked(true)
                .setRandomizedEncryptionRequired(true)
                //.setUnlockedDeviceRequired(true)
                //.setUserAuthenticationRequired(true)              // DEBUG TODO: Add back in
                //.setUserAuthenticationValidityDurationSeconds(-1) // DEBUG TODO: ADD BACK IN
                .setKeySize(2048)
                .setCertificateSubject(new X500Principal("CN=" + keyID))
                .setDigests(KeyProperties.DIGEST_SHA256)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                .setCertificateSerialNumber(BigInteger.valueOf(1337))
                .setCertificateNotBefore(start.getTime())
                .setCertificateNotAfter(end.getTime());
        return builder.build();
    }
}