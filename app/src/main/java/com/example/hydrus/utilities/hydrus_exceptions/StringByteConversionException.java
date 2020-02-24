package com.example.hydrus.utilities.hydrus_exceptions;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Rob and Sam (2020)
//
//--------------------------------------------------------------------------------------------

public class StringByteConversionException extends RuntimeException {

    //--------------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------------
    public StringByteConversionException(String errorMessage) {
        super(errorMessage);
    }
}