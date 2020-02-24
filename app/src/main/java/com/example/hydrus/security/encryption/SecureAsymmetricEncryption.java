package com.example.hydrus.security.encryption;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import com.example.hydrus.security.secure_memory.ConstantSecureMemory;
import com.example.hydrus.utilities.Base64Converter;
import com.example.hydrus.security.secure_memory.VariableSecureMemory;

import functional.Maybe;

public class SecureAsymmetricEncryption {

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static boolean initializeKeyPairWithKeyID(String keyID) {
        return AsymmetricEncryption.initializeKeyPairWithKeyID(keyID);
    }

    public static Maybe<ConstantSecureMemory> maybeGetPublicKeyWithKeyID(String keyID) {
        return AsymmetricEncryption.maybeGetPublicKeyWithKeyID(keyID);
    }

    public static Maybe<String> maybeEncryptSecureByteDataWithPublicKeySecurelyErasingData(VariableSecureMemory secureBytesToEncrypt, ConstantSecureMemory publicKey) {
        Maybe<String> maybeEncryptedData = AsymmetricEncryption.maybeEncryptByPublicKeyWithByteData(secureBytesToEncrypt.getByteRepresentation(), publicKey);
        secureBytesToEncrypt.securelyDeleteBytes();
        return maybeEncryptedData;
    }

    public static Maybe<ConstantSecureMemory> maybeDecryptStringByPrivateKeyWithKeyID(String stringToDecrypt, String keyID) {
        Maybe<byte[]> maybeDecryptedBytes = AsymmetricEncryption.maybeDecryptByPrivateKeyWithKeyID(stringToDecrypt, keyID);
        if(maybeDecryptedBytes.isNotNothing()) {
            return Maybe.asObject(ConstantSecureMemory.secureMemoryFromBytes(maybeDecryptedBytes.object()));
        } else {
            return Maybe.asNothing();
        }
    }

    public static Maybe<String> maybeSignStringWithStoredPrivateKeyWithKeyID(String stringData, String keyID) {
        byte[] data = Base64Converter.decryptBASE64(stringData);
        return maybeSignBytesWithStoredPrivateKeyWithKeyID(data, keyID);
    }

    public static boolean verifyStringWithPublicKeyAndSignature(String stringToVerify, String publicKey, String signature) {
        byte[] bytesToVerify = Base64Converter.decryptBASE64(stringToVerify);
        return verifyBytesWithPublicKeyAndSignature(bytesToVerify, publicKey, signature);
    }

    public static boolean verifyBytesWithPublicKeyAndSignature(byte[] bytesToVerify, String publicKey, String signature) {
        return AsymmetricEncryption.verifyBytes(bytesToVerify, publicKey, signature);
    }

    public static Maybe<String> maybeSignBytesWithStoredPrivateKeyWithKeyID(byte[] bytesToSign, String keyID) {
        return AsymmetricEncryption.maybeSignBytesWithKeyID(bytesToSign, keyID);
    }
}
