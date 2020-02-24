package com.example.hydrus.utilities.hydrus_exceptions;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Rob and Sam (2020)
//
//--------------------------------------------------------------------------------------------

public class MaybeNothingAsObjectException extends RuntimeException {

    //--------------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------------
    public MaybeNothingAsObjectException(String errorMessage) {
        super(errorMessage);
    }
}