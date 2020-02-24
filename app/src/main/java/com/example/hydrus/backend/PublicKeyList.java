package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam and Rob (2020)
//
//--------------------------------------------------------------------------------------------
import com.example.hydrus.general_purpose.Pair;

import java.util.Collection;

import functional.Maybe;
import functional.Monad;
import functional.MonadicOperation;
import json.JSON;
import json.JSONArray;
import json.JSONMapping;
import json.JSONString;
import mapping.MapableOperation;

public class PublicKeyList {

    private static final String HYDRUS_API_USERCODE_JSON_KEY = "usercode";
    private static final String HYDRUS_API_PUBLICKEY_JSON_KEY = "public_key"; //TODO: Question Everything

    private Collection<Pair<String, String>> _usercodePublicKeyPairList;

    //--------------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------------
    public static Maybe<PublicKeyList> maybeParsePublicKeyListFromJSON(JSON jsonRepresentationOfPublicKeyList) {
        Maybe<Collection<Pair<String, String>>> maybeUsercodePublicKeyPairList = _maybeListOfPublicKeyAndUsercodePairsFromJsonRepresentationOfPublicKeyList(jsonRepresentationOfPublicKeyList);
        return maybeUsercodePublicKeyPairList.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<PublicKeyList>, Collection<Pair<String, String>>, PublicKeyList>() {
            @Override
            public Maybe<PublicKeyList> performMonadicOperation(Collection<Pair<String, String>> usercodePublicKeyPairList) {
                return Maybe.asObject(new PublicKeyList(usercodePublicKeyPairList));
            }
        });
    }

    private static Maybe<Collection<Pair<String, String>>> _maybeListOfPublicKeyAndUsercodePairsFromJsonRepresentationOfPublicKeyList(JSON jsonRepresentationOfPublicKeyList) {
        Maybe<JSONArray> maybeJSONArrayRepresentationOfPublicKeyListFromMaybeJSON = JSONArray.maybeJSONArrayFromJSON(jsonRepresentationOfPublicKeyList);
        return maybeJSONArrayRepresentationOfPublicKeyListFromMaybeJSON.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<Collection<Pair<String, String>>>, JSONArray, Collection<Pair<String, String>>>() {
            @Override
            public Maybe<Collection<Pair<String, String>>> performMonadicOperation(JSONArray jsonArrayRepresentationOfPublicKeyList) {
                return jsonArrayRepresentationOfPublicKeyList.maybeMapToCollectionWithOperation(new MapableOperation<Pair<String, String>, JSON>() {
                    @Override
                    public Maybe<Pair<String, String>> applyMapOperation(JSON jsonRepresentationOfUsercodeAndPublicKey) {

                        return _maybeMapJSONToPairOfUsercodeAndPublicKey(jsonRepresentationOfUsercodeAndPublicKey);
                    }
                });
            }
        });
    }

    private static Maybe<Pair<String, String>> _maybeMapJSONToPairOfUsercodeAndPublicKey(JSON jsonRepresentationOfUsercodeAndPublicKey) {
        Maybe<JSONMapping> asd1 = JSONMapping.maybeJSONMappingFromJSON(jsonRepresentationOfUsercodeAndPublicKey);
        Maybe<String> maybeUsercodeString = asd1.applyGivenOperationOntoThisObjectMondically(_monadicOperationToExtractStringWithKeyFromThisJSONMapping(HYDRUS_API_USERCODE_JSON_KEY));
        Maybe<String> maybePublicKeyString = asd1.applyGivenOperationOntoThisObjectMondically(_monadicOperationToExtractStringWithKeyFromThisJSONMapping(HYDRUS_API_PUBLICKEY_JSON_KEY));
        if (maybeUsercodeString.isNotNothing() && maybePublicKeyString.isNotNothing()) {
            Pair<String, String> usercodePublicKeyPair = Pair.pairWithFirstAndSecondObjects(maybeUsercodeString.object(), maybePublicKeyString.object());
            return Maybe.asObject(usercodePublicKeyPair);
        }
        return Maybe.asNothing();
    }

    private static MonadicOperation<Monad<String>, JSONMapping, String> _monadicOperationToExtractStringWithKeyFromThisJSONMapping(final String keyInQuestion) {
        return new MonadicOperation<Monad<String>, JSONMapping, String>() {
            @Override
            public Monad<String> performMonadicOperation(JSONMapping jsonMappingInQuestion) {
                return jsonMappingInQuestion.maybeGetJSONStringForGivenKeyInMapping(keyInQuestion)
                        .applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<String>, JSONString, String>() {
                            @Override
                            public Maybe<String> performMonadicOperation(JSONString extractedJSONString) {
                                return Maybe.asObject(extractedJSONString.getAsString());
                            }
                        });
            }
        };
    }

    //--------------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------------
    public Collection<Pair<String, String>> getAllPairsOfUsercodeAndPublicKeyInPublicKeyList() {
        return _usercodePublicKeyPairList;
    }

    //--------------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------------
    private PublicKeyList(Collection<Pair<String, String>> usercodePublicKeyPairList) {
        _usercodePublicKeyPairList = usercodePublicKeyPairList;
    }

    //--------------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------------
}
