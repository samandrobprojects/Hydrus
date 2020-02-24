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

public interface HttpsRequestCallback {

    public void receivedResponseFromHTTPSServer(JSON jsonResponseRepresentation);

    public void receivedErrorWhenAttemptingHTTPSConnection(HttpsRequestErrorProtocol error);
}