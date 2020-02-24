package com.example.hydrus.utilities.hydrus_exceptions;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Rob and Sam (2020)
//
//--------------------------------------------------------------------------------------------

public class NoUsercodeSetException extends RuntimeException {

    //--------------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------------
    public NoUsercodeSetException(String errorMessage) {
        super(errorMessage);
    }
}
