package com.example.hydrus.system;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------
import com.example.hydrus.utilities.hydrus_exceptions.InvalidContactListSetupException;
import com.example.hydrus.utilities.hydrus_exceptions.JSONBaseClassException;
import com.example.hydrus.utilities.hydrus_exceptions.MaybeNothingAsObjectException;

public class ApplicationExceptionAuthority {

    private static void _throwFatalRuntimeException(RuntimeException fatalRuntimeExceptionInQuestion) {
        throw fatalRuntimeExceptionInQuestion;
    }

    public static void EXCEPTION_InvalidContactListSetupException() {
        _throwFatalRuntimeException(new InvalidContactListSetupException("Could not setup contact list"));
    }

    public static void _EXCEPTION_throwNewExceptionIfTriedToGetObjectForNothingMaybe() {
        _throwFatalRuntimeException(new InvalidContactListSetupException("Tried to get object from Maybe as nothing"));
    }

    public static void _EXCEPTION_throwNewJSONBaseClassException(String exceptionMessage) {
        _throwFatalRuntimeException(new JSONBaseClassException(exceptionMessage));
    }
}