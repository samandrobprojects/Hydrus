package com.example.hydrus.utilities;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------
import com.example.hydrus.utilities.hydrus_exceptions.StringByteConversionException;
import java.nio.charset.StandardCharsets;

public class StringByteConverter {

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static String convertByteArrayToString(byte[] byteArrayToConvert) {
        try {
            return new String(byteArrayToConvert, StandardCharsets.UTF_8);
        } catch (Exception e) {
            _EXCEPTION_throwNewStringByteConversionException();
            return null;
        }
    }

    public static byte[] convertStringToByteArray(String stringToConvert) {
        try {
            return stringToConvert.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            _EXCEPTION_throwNewStringByteConversionException();
            return null;
        }
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static void _EXCEPTION_throwNewStringByteConversionException() {
        throw new StringByteConversionException("Invalid String Byte Conversion");
    }
}
