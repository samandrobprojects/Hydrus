package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam and Rob (2020)
//
//--------------------------------------------------------------------------------------------

public interface HydrusAPICallback<HYDRUS_API_RESPONSE_OBJECT extends HydrusAPIResponseObject> {
    public void successfullyReceivedAPIResponse(HYDRUS_API_RESPONSE_OBJECT apiResponse);
    public void failedToReceivedAPIResponse(HydrusAPIError hydrusAPIError);
}