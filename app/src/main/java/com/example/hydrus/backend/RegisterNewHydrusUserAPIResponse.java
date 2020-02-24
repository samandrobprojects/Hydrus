package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import json.JSON;

public class RegisterNewHydrusUserAPIResponse extends HydrusAPIResponseObject {

    //--------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------
    public boolean userWasRegisteredSuccessfully() {
        return this._responseStatusWasSuccess();
    }

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static RegisterNewHydrusUserAPIResponse newUserRegistrationAPIResponseFromJson(JSON jsonRegisterNewUserResponse) {
        return new RegisterNewHydrusUserAPIResponse(jsonRegisterNewUserResponse);
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------
    private RegisterNewHydrusUserAPIResponse(JSON jsonRegisterNewUserResponse) {
        super(jsonRegisterNewUserResponse);
    }

}
