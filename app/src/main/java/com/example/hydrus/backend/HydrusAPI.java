package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import java.util.HashMap;

import json.JSON;

public class HydrusAPI {

    //--------------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------------
    public static void registerNewHydrusUserWithUserCodeAndUserPublicKeyAndAPICallback(String userCode, String userPublicKey, final HydrusAPICallback<RegisterNewHydrusUserAPIResponse> hydrusAPICallback) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("sender_usercode", userCode);
        parameters.put("public_key", userPublicKey);
        String urlForAPICall = HydrusAPI._hydrusURLForAPIWithAPIIdentifier("register_new_hydrus_user");
        HydrusHttpsRequest.makeHydrusSecuredHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(urlForAPICall, parameters, new HttpsRequestCallback() {
            @Override
            public void receivedResponseFromHTTPSServer(JSON json) {
                hydrusAPICallback.successfullyReceivedAPIResponse(RegisterNewHydrusUserAPIResponse.newUserRegistrationAPIResponseFromJson(json));
            }

            @Override
            public void receivedErrorWhenAttemptingHTTPSConnection(HttpsRequestErrorProtocol error) {
                hydrusAPICallback.failedToReceivedAPIResponse(HydrusAPIError.errorWithStringDescriptionOfError(error.ErrorMessage()));
            }
        });
    }

    public static void putMessageWithEncryptedMessageAndSignatureAndSenderUsercodeAndAPICallback(String encryptedMessage, String encryptedMessageSignature, String senderUsercode, final HydrusAPICallback<PutMessageAPIResponse> hydrusAPICallback) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("sender_usercode", senderUsercode);
        parameters.put("message", encryptedMessage);
        parameters.put("message_signature", encryptedMessageSignature);
        String urlForAPICall = HydrusAPI._hydrusURLForAPIWithAPIIdentifier("put_message");
        HydrusHttpsRequest.makeHydrusSecuredHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(urlForAPICall, parameters, new HttpsRequestCallback() {
            @Override
            public void receivedResponseFromHTTPSServer(JSON json) {
                hydrusAPICallback.successfullyReceivedAPIResponse(PutMessageAPIResponse.newPutMessageAPIResponseFromJson(json));
            }

            @Override
            public void receivedErrorWhenAttemptingHTTPSConnection(HttpsRequestErrorProtocol error) {
                hydrusAPICallback.failedToReceivedAPIResponse(HydrusAPIError.errorWithStringDescriptionOfError(error.ErrorMessage()));
            }
        });
    }

    public static void downloadPublicKeyListWithUsercodeAndAPICallback(String usercode, final HydrusAPICallback<GetPublicKeyListAPIResponse> hydrusAPICallback) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("recipient_usercode", usercode);
        String urlForAPICall = HydrusAPI._hydrusURLForAPIWithAPIIdentifier("get_public_key_list");
        HydrusHttpsRequest.makeHydrusSecuredHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(urlForAPICall, parameters, new HttpsRequestCallback() {
            @Override
            public void receivedResponseFromHTTPSServer(JSON json) {
                hydrusAPICallback.successfullyReceivedAPIResponse(GetPublicKeyListAPIResponse.newGetPublicKeyListAPIResponseFromJson(json));
            }

            @Override
            public void receivedErrorWhenAttemptingHTTPSConnection(HttpsRequestErrorProtocol error) {
                hydrusAPICallback.failedToReceivedAPIResponse(HydrusAPIError.errorWithStringDescriptionOfError(error.ErrorMessage()));
            }
        });
    }

    public static void downloadLatestMessageDumpWithUsercodeAndAPICallback(String usercode, final HydrusAPICallback<GetMessageDumpAPIResponse> hydrusAPICallback) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("recipient_usercode", usercode);
        String urlForAPICall = HydrusAPI._hydrusURLForAPIWithAPIIdentifier("get_message_dump");
        HydrusHttpsRequest.makeHydrusSecuredHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(urlForAPICall, parameters, new HttpsRequestCallback() {
            @Override
            public void receivedResponseFromHTTPSServer(JSON json) {
                hydrusAPICallback.successfullyReceivedAPIResponse(GetMessageDumpAPIResponse.newGetMessageDumpAPIResponseFromJson((json)));
            }

            @Override
            public void receivedErrorWhenAttemptingHTTPSConnection(HttpsRequestErrorProtocol error) {
                hydrusAPICallback.failedToReceivedAPIResponse(HydrusAPIError.errorWithStringDescriptionOfError(error.ErrorMessage()));
            }
        });
    }

    private static String _hydrusURLForAPIWithAPIIdentifier(String apiIdentifier) {
        return "https://95.179.209.172/hydrus_endpoint.php?api_identifier="+apiIdentifier;
    }
}

/*

import com.example.arete.arete_api.response_payloads.AddNewContactResponse;
import com.example.arete.arete_api.response_payloads.RecieveMessageResponse;
import com.example.arete.arete_api.response_payloads.RegisterNewUserResponse;
import com.example.arete.arete_api.response_payloads.SendMessageResponse;
import com.example.arete.arete_user_data.AreteUser;
import com.example.hydrus.utilities.JSON;

import java.util.HashMap;

public class HydrusAPI {

    public static void registerNewHydrusUserWithUserCodeAndUserPublicEncryptionKeyAndAPICallback(String usercode, String userPublicEncryptionKey, final AreteAPICallback areteAPICallback) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("sender_usercode", usercode);
        parameters.put("public_key", userPublicEncryptionKey);
        String urlForAPICall = AreteAPI.areteURLForAPIWithAPIIdentifier("register_new_arete_user");
        HydrusHttpsRequest.makeHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(urlForAPICall, parameters, new HttpsRequestCallback() {
            @Override
            public void receivedResponseFromHTTPSServer(JSON jsonRepresentation) {
                areteAPICallback.successfullyReceivedAPIResponse(RegisterNewUserResponse.newUserRegistrationResponseFromJson(jsonRepresentation));
            }

            @Override
            public void receivedErrorWhenAttemptingHTTPSConnection(HttpsRequestErrorProtocol error) {
                areteAPICallback.failedToReceivedAPIResponse(AreteAPIError.errorWithStringDescriptionOfError(error.ErrorMessage()));
            }
        });
    }

    public static void sendMessageWithEncryptedMessageAndSignatureAndRecipientUsercodeAndAPICallback(String encryptedMessage, String encryptedMessageSignature, String usercode, final AreteAPICallback areteAPICallback) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("sender_usercode", AreteUser.globalAreteUser().getUserCode());
        parameters.put("recipient_usercode", usercode);
        parameters.put("message", encryptedMessage);
        parameters.put("message_signature", encryptedMessageSignature);

        // DEBUG
        System.out.printf("sender_usercode %s\n", usercode);

        String urlForAPICall = AreteAPI.areteURLForAPIWithAPIIdentifier("put_message_for_usercode");
        HydrusHttpsRequest.makeHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(urlForAPICall, parameters, new HttpsRequestCallback() {
            @Override
            public void receivedResponseFromHTTPSServer(JSON jsonRepresentation) {
                areteAPICallback.successfullyReceivedAPIResponse(SendMessageResponse.newSendMessageResponse(jsonRepresentation));
            }

            @Override
            public void receivedErrorWhenAttemptingHTTPSConnection(HttpsRequestErrorProtocol error) {
                areteAPICallback.failedToReceivedAPIResponse(AreteAPIError.errorWithStringDescriptionOfError(error.ErrorMessage()));
            }
        });
    }


    public static void recieveMessageSenderUsercodeAndAPICallback(String usercode, final AreteAPICallback areteAPICallback) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("sender_usercode", usercode);
        parameters.put("recipient_usercode", AreteUser.globalAreteUser().getUserCode());

        // DEBUG
        System.out.printf("sender_usercode %s\n", usercode);

        String urlForAPICall = AreteAPI.areteURLForAPIWithAPIIdentifier("take_oldest_message_for_usercode");
        HydrusHttpsRequest.makeHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(urlForAPICall, parameters, new HttpsRequestCallback() {
            @Override
            public void receivedResponseFromHTTPSServer(JSON jsonRepresentation) {
                areteAPICallback.successfullyReceivedAPIResponse(RecieveMessageResponse.newRecieveMessageResponse(jsonRepresentation));
            }

            @Override
            public void receivedErrorWhenAttemptingHTTPSConnection(HttpsRequestErrorProtocol error) {
                areteAPICallback.failedToReceivedAPIResponse(AreteAPIError.errorWithStringDescriptionOfError(error.ErrorMessage()));
            }
        });
    }

    public static void addContactWithUsercodeAndAPICallback(String usercode, final AreteAPICallback areteAPICallback) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("recipient_usercode", usercode);
        parameters.put("sender_usercode", usercode);

        // DEBUG
        System.out.printf("recipient_usercode %s\n", usercode);

        String urlForAPICall = AreteAPI.areteURLForAPIWithAPIIdentifier("get_public_key_for_user");
        HydrusHttpsRequest.makeHTTPSPOSTRequestFromURLWithParameterToValueMappingAndCallback(urlForAPICall, parameters, new HttpsRequestCallback() {
            @Override
            public void receivedResponseFromHTTPSServer(JSON jsonRepresentation) {
                areteAPICallback.successfullyReceivedAPIResponse(AddNewContactResponse.newAddNewContactResponse(jsonRepresentation));
            }

            @Override
            public void receivedErrorWhenAttemptingHTTPSConnection(HttpsRequestErrorProtocol error) {
                areteAPICallback.failedToReceivedAPIResponse(AreteAPIError.errorWithStringDescriptionOfError(error.ErrorMessage()));
            }
        });
    }

    private static String areteURLForAPIWithAPIIdentifier(String apiIdentifier) {
        return "https://95.179.209.172/arete_endpoint.php?api_identifier="+apiIdentifier;
    }
}*/