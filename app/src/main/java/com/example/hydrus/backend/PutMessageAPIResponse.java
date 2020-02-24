package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

public class PutMessageAPIResponse extends HydrusAPIResponseObject {

    //--------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------
    public boolean messageWasPutSuccessfully() {
        return this._responseStatusWasSuccess();
    }

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static PutMessageAPIResponse newPutMessageAPIResponseFromJson(JSON jsonRegisterNewUserResponse) {
        return new PutMessageAPIResponse(jsonRegisterNewUserResponse);
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------
    private PutMessageAPIResponse(JSON jsonRegisterNewUserResponse) {
        super(jsonRegisterNewUserResponse);
    }
}
