package com.example.hydrus.security.encryption;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

public class EncryptedMessageIVPair {

    private final String _encryptedMessage;
    private final String _iv;

    //--------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------
    EncryptedMessageIVPair(String encryptedMessage, String iv) {
        _encryptedMessage = encryptedMessage;
        _iv = iv;
    }

    public static EncryptedMessageIVPair getPairWithEncryptedMessageAndIV(String encryptedMessage, String iv) {
        return new EncryptedMessageIVPair(encryptedMessage, iv);
    }

    public String getEncryptedMessage() {
        return _encryptedMessage;
    }

    public String getIV() {
        return _iv;
    }
}
