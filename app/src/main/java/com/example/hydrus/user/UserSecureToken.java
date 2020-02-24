package com.example.hydrus.user;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import com.example.hydrus.system.LocalStorage;
import com.example.hydrus.security.encryption.EncryptedMessageIVPair;
import com.example.hydrus.security.encryption.SecureSymetricEncryption;
import com.example.hydrus.security.secure_memory.ConstantSecureMemory;
import com.example.hydrus.security.SecureRandomGenerator;
import com.example.hydrus.security.secure_memory.SecureToken;
import com.example.hydrus.utilities.hydrus_exceptions.InvalidSecureTokenException;

import functional.Maybe;

public class UserSecureToken {

    private static final String _SECURE_TOKEN_ENCRYPTION_KEY_ID = "_SECURE_TOKEN_ENCRYPTION_KEY_ID";
    private static final String _ENCRYPTED_SECURE_TOKEN_KEY = "ENCRYPTED_SECURE_TOKEN_KEY";
    private static final String _ENCRYPTED_SECURE_TOKEN_IV_KEY = "ENCRYPTED_SECURE_TOKEN_IV_KEY";
    private static final int _SECURE_TOKEN_LENGTH = 16;

    private static SecureToken _secureToken = null;

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static void restoreSecureTokenFromEncryptedMemory() {
        EncryptedMessageIVPair encryptedSecureTokenPair = _restoreEncryptedSecureTokenPairFromStorage();
        Maybe<ConstantSecureMemory> maybeSecureTokenInConstantMemory = SecureSymetricEncryption.decryptWithSecretKeyWithKeyID(encryptedSecureTokenPair, _SECURE_TOKEN_ENCRYPTION_KEY_ID);
        if(maybeSecureTokenInConstantMemory.isNotNothing()) {
            _secureToken = _convertConstantSecureMemorySecureTokenToSecureTokenFormat(maybeSecureTokenInConstantMemory.object());
        } else {
            _EXCEPTION_throwNewExceptionForDecryptedTokenNotValid();
        }
    }

    public static void eraseSecureTokenFromUnencryptedMemory() {
        _secureToken.securelyDeleteBytes();
    }

    public static SecureToken getSecureToken() {
        return _secureToken;
    }

    public static void setupSecureTokenForUser() {
        SecureSymetricEncryption.initializeSymetricKeyWithKeyID(_SECURE_TOKEN_ENCRYPTION_KEY_ID); //TODO:
        EncryptedMessageIVPair encryptedSecureTokenPair = _generateEncryptedSecureTokenPair();
        LocalStorage.storeStringWithKey(encryptedSecureTokenPair.getEncryptedMessage(), _ENCRYPTED_SECURE_TOKEN_KEY);
        LocalStorage.storeStringWithKey(encryptedSecureTokenPair.getIV(), _ENCRYPTED_SECURE_TOKEN_IV_KEY);
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static EncryptedMessageIVPair _generateEncryptedSecureTokenPair() {
        Maybe<EncryptedMessageIVPair> maybeEncryptedSecureTokenPair =  _maybeGetEncryptedSecureTokenPair();
        if(maybeEncryptedSecureTokenPair.isNotNothing()) {
            return maybeEncryptedSecureTokenPair.object();
        } else {
            _EXCEPTION_throwNewExceptionForEncryptedSecureTokenPairNotValid();
            return null;
        }
    }

    private static Maybe<EncryptedMessageIVPair> _maybeGetEncryptedSecureTokenPair() {
        byte[] secureTokenBytes = SecureRandomGenerator.generateRandomStringBytesWithLength(_SECURE_TOKEN_LENGTH);
        ConstantSecureMemory secureToken = ConstantSecureMemory.secureMemoryFromBytes(secureTokenBytes);
        return SecureSymetricEncryption.encryptWithSecretKeyWithKeyID(secureToken, _SECURE_TOKEN_ENCRYPTION_KEY_ID);
    }

    private static SecureToken _convertConstantSecureMemorySecureTokenToSecureTokenFormat(ConstantSecureMemory secureTokenInConstantMemory) {
        SecureToken secureToken = SecureToken.secureTokenFromBytes(secureTokenInConstantMemory.getByteRepresentation());
        secureTokenInConstantMemory.securelyDeleteBytes();
        return secureToken;
    }

    private static EncryptedMessageIVPair _restoreEncryptedSecureTokenPairFromStorage() {
        String encryptedSecureToken = _retrieveEncryptedSecureTokenStringFromLocalStorage();
        String encryptedSecureTokenIV = _retrieveEncryptedSecureTokenIVStringFromLocalStorage();
        return EncryptedMessageIVPair.getPairWithEncryptedMessageAndIV(encryptedSecureToken, encryptedSecureTokenIV);
    }

    private static String _retrieveEncryptedSecureTokenStringFromLocalStorage() {
        Maybe<String> maybeEncryptedSecureToken = LocalStorage.getStringWithKey(_ENCRYPTED_SECURE_TOKEN_KEY);
        if(maybeEncryptedSecureToken.isNotNothing()) {
            return maybeEncryptedSecureToken.object();
        } else {
            _EXCEPTION_throwNewExceptionFailedRetrieveSecureTokenStringFromLocalStorage();
            return null;
        }
    }

    private static String _retrieveEncryptedSecureTokenIVStringFromLocalStorage() {
        Maybe<String> maybeEncryptedSecureTokenIV = LocalStorage.getStringWithKey(_ENCRYPTED_SECURE_TOKEN_IV_KEY);
        if(maybeEncryptedSecureTokenIV.isNotNothing()) {
            return maybeEncryptedSecureTokenIV.object();
        } else {
            _EXCEPTION_throwNewExceptionFailedRetrieveSecureTokenStringFromLocalStorage();
            return null;
        }
    }

    private static void _EXCEPTION_throwNewExceptionFailedRetrieveSecureTokenStringFromLocalStorage() {
        throw new InvalidSecureTokenException("Secure Token Could not Be Restored From Local Storage");
    }

    private static void _EXCEPTION_throwNewExceptionForEncryptedSecureTokenPairNotValid() {
        throw new InvalidSecureTokenException("Secure Token Invalid");
    }

    private static void _EXCEPTION_throwNewExceptionForDecryptedTokenNotValid() {
        throw new InvalidSecureTokenException("Secure Token Could not Be Decrypted From Memory");
    }
}