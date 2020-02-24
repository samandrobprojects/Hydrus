package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// AyreLabs (2019)
//
//--------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------
// IMPORTS
//--------------------------------------------------------------------------------------------

public class HttpsRequestErrorProtocol {

    public static int ERROR_CONNECTING_TO_SERVER  = 0;
    public static int ERROR_FROM_SERVER_API  = 1;

    private String _errorString;
    private int _errorType;

    private HttpsRequestErrorProtocol(String errorString, int errorType) {
        this._errorString = errorString;
        this._errorType = errorType;
    }


    public static HttpsRequestErrorProtocol newAPIErrorWithString(String errorString) {
        return new HttpsRequestErrorProtocol(errorString, ERROR_FROM_SERVER_API);
    }

    public static HttpsRequestErrorProtocol newConnectionError() {
        return new HttpsRequestErrorProtocol("", ERROR_CONNECTING_TO_SERVER);
    }

    public int ErrorType() {
        return this._errorType;
    }

    public String ErrorMessage() {
        return this._errorString;
    }
}
