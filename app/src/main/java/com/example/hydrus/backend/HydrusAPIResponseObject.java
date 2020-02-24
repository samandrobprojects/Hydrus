package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam and Rob (2020)
//
//--------------------------------------------------------------------------------------------

import functional.Maybe;
import functional.Monad;
import functional.MonadicOperation;
import json.JSON;
import json.JSONMapping;
import json.JSONString;

public abstract class HydrusAPIResponseObject {

    private static final String _API_RESPONSE_STATUS_SUCCESS_VALUE = "success";
    private static final String _API_RESPONSE_STATUS_JSON_KEY = "response_status";

    private static final String _API_PAYLOAD_JSON_KEY = "payload";

    private Maybe<JSONMapping> _maybeAPIResponseAsJSONMapping;

    //--------------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------------
    protected HydrusAPIResponseObject(JSON apiResponseJSON) {
        _maybeAPIResponseAsJSONMapping = JSONMapping.maybeJSONMappingFromJSON(apiResponseJSON);
    }

    protected boolean _responseStatusWasSuccess() {
        return _maybeGetResponseStatusStringFromResponse().containsObjectEqualTo(_API_RESPONSE_STATUS_SUCCESS_VALUE);
    }

    protected Maybe<JSON> _maybeGetJSONFromPayloadWithKey(final String keyInQuestion) {
        Maybe<JSONMapping> maybeResponsePayloadAsJSONMapping = _maybeGetPayloadAsJSONMapping();
        return maybeResponsePayloadAsJSONMapping.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<JSON>, JSONMapping, JSON>() {
            @Override
            public Maybe<JSON> performMonadicOperation(JSONMapping payloadAsJSONMapping) {
                return payloadAsJSONMapping.maybeGetJSONForGivenKeyInMapping(keyInQuestion);
            }
        });
    }

    private Maybe<JSONMapping> _maybeGetPayloadAsJSONMapping() {
        return _maybeAPIResponseAsJSONMapping.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<JSONMapping>, JSONMapping, JSONMapping>() {
            @Override
            public Maybe<JSONMapping> performMonadicOperation(JSONMapping apiResponseAsJSONMapping) {
                return apiResponseAsJSONMapping.maybeGetJSONMappingForGivenKeyInMapping(_API_PAYLOAD_JSON_KEY);
            }
        });
    }

    private Maybe<JSONString> _maybeGetResponseStatusJSONStringFromResponse() {
        return _maybeAPIResponseAsJSONMapping.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<JSONString>, JSONMapping, JSONString>() {
            @Override
            public Maybe<JSONString> performMonadicOperation(JSONMapping apiResponseAsJSONMapping) {
                return apiResponseAsJSONMapping.maybeGetJSONStringForGivenKeyInMapping(_API_RESPONSE_STATUS_JSON_KEY);
            }
        });
    }

    private Maybe<String> _maybeGetResponseStatusStringFromResponse() {
        Maybe<JSONString> maybeResponseStatusJSONString = _maybeGetResponseStatusJSONStringFromResponse();
        return maybeResponseStatusJSONString.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<String>, JSONString, String>() {
            @Override
            public Maybe<String> performMonadicOperation(JSONString responseStatusAsJSONString) {
                return Maybe.asObject(responseStatusAsJSONString.getAsString());
            }
        });
    }
}