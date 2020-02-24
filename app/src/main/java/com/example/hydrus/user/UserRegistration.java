package com.example.hydrus.user;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

public class UserRegistration {

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static void registerWithUserCode(String userCode) {
        Usercode.setUsercode(userCode);
        UserSecureToken.setupSecureTokenForUser();
    }
}
