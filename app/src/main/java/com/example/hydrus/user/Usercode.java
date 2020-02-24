package com.example.hydrus.user;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import com.example.hydrus.system.LocalStorage;
import com.example.hydrus.utilities.hydrus_exceptions.NoUsercodeSetException;

import functional.Maybe;

public class Usercode {

    private static final String _VALID_USERCODE_REGEX = "[A-Za-z0-9]+";
    private static int _MINIMUM_VALID_USERCODE_LENGTH = 6;
    private static int _MAXIMUM_VALID_USERCODE_LENGTH = 18;
    private static final String _USERCODE_KEY = "USERCODE_KEY";

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static boolean checkValidUsercode(String usercode) {
        int usercodeLength = usercode.length();
        if(usercodeLength <= _MAXIMUM_VALID_USERCODE_LENGTH && usercodeLength >= _MINIMUM_VALID_USERCODE_LENGTH) {
            return usercode.matches(_VALID_USERCODE_REGEX);
        }
        return false;
    }

    public static String getUsercode() {
        Maybe<String> maybeUsercode =  LocalStorage.getStringWithKey(_USERCODE_KEY);
        if(maybeUsercode.isNotNothing()) {
            return maybeUsercode.object();
        } else {
            _EXCEPTION_throwExceptionForInvalidRetrievedUsercode();
            return null;
        }
    }

    public static void setUsercode(String usercode) {
        LocalStorage.storeStringWithKey(usercode, _USERCODE_KEY);
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static void _EXCEPTION_throwExceptionForInvalidRetrievedUsercode() {
        throw new NoUsercodeSetException("No Usercode Set");
    }
}