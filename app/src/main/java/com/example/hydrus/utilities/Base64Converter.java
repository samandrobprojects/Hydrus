package com.example.hydrus.utilities;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import android.util.Base64;

public class Base64Converter {

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static byte[] decryptBASE64(String stringToConvertFromBase64) {
        return Base64.decode(stringToConvertFromBase64, Base64.NO_WRAP);
    }

    public static String encryptBASE64(byte[] byteArrayToConvertToBase64) {
        return Base64.encodeToString(byteArrayToConvertToBase64, Base64.NO_WRAP);
    }
}