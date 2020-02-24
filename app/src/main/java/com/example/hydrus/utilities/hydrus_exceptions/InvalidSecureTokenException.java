package com.example.hydrus.utilities.hydrus_exceptions;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Rob and Sam (2020)
//
//--------------------------------------------------------------------------------------------

public class InvalidSecureTokenException extends RuntimeException {

    //--------------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------------
    public InvalidSecureTokenException(String errorMessage) {
        super(errorMessage);
    }
}
