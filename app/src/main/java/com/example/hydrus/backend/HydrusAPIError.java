package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam and Rob (2020)
//
//--------------------------------------------------------------------------------------------

public class HydrusAPIError {

    private String stringDescriptionOfError;

    private HydrusAPIError(String stringDescriptionOfError) {
        this.stringDescriptionOfError = stringDescriptionOfError;
    }


    public static HydrusAPIError errorWithStringDescriptionOfError(String stringDescriptionOfError) {
        return new HydrusAPIError(stringDescriptionOfError);
    }

    /*public static HydrusAPIError unknownKydrusAPIError() {
        return new HydrusAPIError("unknown error");
    }*/

    public String stringDescriptionOfError() {
        return this.stringDescriptionOfError;
    }
}