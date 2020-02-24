package com.example.hydrus.utilities.hydrus_exceptions;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Rob and Sam (2020)
//
//--------------------------------------------------------------------------------------------

public class InvalidContactListSetupException extends RuntimeException {

    //--------------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------------
    public InvalidContactListSetupException(String errorMessage) {
        super(errorMessage);
    }
}