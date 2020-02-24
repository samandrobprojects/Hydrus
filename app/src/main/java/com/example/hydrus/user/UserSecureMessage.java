package com.example.hydrus.user;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------
import com.example.hydrus.security.encryption.SecureAsymmetricEncryption;
import com.example.hydrus.security.secure_memory.ConstantSecureMemory;
import com.example.hydrus.security.secure_memory.VariableSecureMemory;

import functional.Maybe;

public class UserSecureMessage {

    private static VariableSecureMemory _secureMessage = VariableSecureMemory.emptySecureBytes();

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static Maybe<String> maybeGetSecureMessageEncryptedForPublicKey(ConstantSecureMemory publicKey) {
        return SecureAsymmetricEncryption.maybeEncryptSecureByteDataWithPublicKeySecurelyErasingData(_secureMessage, publicKey);
    }

    public static void appendByteToMessage(byte byteToAppend) {
        byte[] bytesToAppend = new byte[1];
        bytesToAppend[0] = byteToAppend;
        _secureMessage.appendBytes(bytesToAppend);
    }

    public static void removeByteFromMessage() {
        _secureMessage.removeByte();
    }

    public static void securelyEraseMessage() {
        _secureMessage.securelyDeleteBytes();
    }
}
