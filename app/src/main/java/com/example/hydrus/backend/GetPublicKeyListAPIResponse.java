package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import functional.Maybe;
import functional.Monad;
import functional.MonadicOperation;
import json.JSON;

public class GetPublicKeyListAPIResponse extends HydrusAPIResponseObject {

    //--------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------
    public Maybe<PublicKeyList> maybeGetPublicKeyListIfAvaliable() {
        Maybe<JSON> maybePublicKeyListJSONFromPayload = this._maybeGetJSONFromPayloadWithKey("public_key_list");
        return maybePublicKeyListJSONFromPayload.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<PublicKeyList>, JSON, PublicKeyList>() {
            @Override
            public Monad<PublicKeyList> performMonadicOperation(JSON publicKeyListJSONFromPayload) {
                return PublicKeyList.maybeParsePublicKeyListFromJSON(publicKeyListJSONFromPayload);
            }
        });
    }

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static GetPublicKeyListAPIResponse newGetPublicKeyListAPIResponseFromJson(JSON jsonPublicKeyListResponse) {
        return new GetPublicKeyListAPIResponse(jsonPublicKeyListResponse);
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------
    private GetPublicKeyListAPIResponse(JSON jsonRegisterNewUserResponse) {
        super(jsonRegisterNewUserResponse);
    }
}
