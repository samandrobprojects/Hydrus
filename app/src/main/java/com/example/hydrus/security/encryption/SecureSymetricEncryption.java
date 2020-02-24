package com.example.hydrus.security.encryption;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import com.example.hydrus.security.secure_memory.ConstantSecureMemory;
import com.example.hydrus.utilities.hydrus_exceptions.EncryptionKeyException;

import javax.crypto.SecretKey;

import functional.Maybe;

public class SecureSymetricEncryption {

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static SecretKey getSymetricKeyWithKeyID(String keyID) {
        try {
            return SymmetricEncryption.getSymetricKeyWithID(keyID);
        } catch (Exception e) {
            throw new EncryptionKeyException("Failed To Retrieve Symetric Key");
        }
    }

    public static boolean initializeSymetricKeyWithKeyID(String keyID) {
        return SymmetricEncryption.initializeSymetricKeyWithKeyID(keyID);
    }

    public static Maybe<EncryptedMessageIVPair> encryptWithSecretKeyWithKeyID(ConstantSecureMemory bytesToEncrypt, String keyID) {
        Maybe<EncryptedMessageIVPair> maybeEncryptedMessageIVPair = SymmetricEncryption.maybeEncryptWithSecretKeyWithKeyID(bytesToEncrypt.getByteRepresentation(), keyID);
        bytesToEncrypt.securelyDeleteBytes();
        return maybeEncryptedMessageIVPair;
    }

    public static Maybe<ConstantSecureMemory> decryptWithSecretKeyWithKeyID(EncryptedMessageIVPair encryptedMessageIVPair, String keyID) {
        Maybe<byte[]> maybeDecryptedBytes = SymmetricEncryption.maybeDecryptWithSecretKeyWithKeyID(encryptedMessageIVPair, keyID);
        if(maybeDecryptedBytes.isNotNothing()) {
            ConstantSecureMemory decryptedBytesInSecureMemory = ConstantSecureMemory.secureMemoryFromBytes(maybeDecryptedBytes.object());
            return Maybe.asObject(decryptedBytesInSecureMemory);
        } else {
            return Maybe.asNothing();
        }
    }
}
