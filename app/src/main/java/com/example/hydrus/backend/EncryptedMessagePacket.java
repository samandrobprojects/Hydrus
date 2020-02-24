package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam and Rob (2020)
//
//--------------------------------------------------------------------------------------------

import java.util.Map;

import functional.Maybe;
import json.JSON;

public class EncryptedMessagePacket {

    private static final String _ENCRYPTED_MESSAGE_KEY = "encrypted_message";
    private static final String _MESSAGE_SIGNATURE_KEY = "message_signature";
    private static final String _SENDER_USERCODE_KEY = "sender_usercode";

    private final String _senderUsercode;
    private final String _encryptedMessage;
    private final String _messageSignature;

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static Maybe<EncryptedMessagePacket> maybeParseEncryptedMessagePacketFromJSON(JSON encryptedMessagePacketJSON_AtleastItMightBe) {
        Maybe<JSON> maybeEncryptedMessagePacketJSON = Maybe.asObject(encryptedMessagePacketJSON_AtleastItMightBe);
        EncryptedMessagePacket.Builder EncryptedMessagePacketBuilder = new EncryptedMessagePacket.Builder();
        return maybeBuildEncryptedMessagePacketFromMaybeJSONWithBuilder(maybeEncryptedMessagePacketJSON, EncryptedMessagePacketBuilder);
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static Maybe<EncryptedMessagePacket> maybeBuildEncryptedMessagePacketFromMaybeJSONWithBuilder(Maybe<JSON> maybeEncryptedMessagePacketJSON, EncryptedMessagePacket.Builder EncryptedMessagePacketBuilder) {
        maybeAddParameterFromMaybeJSONToBuilderForParameterName(maybeEncryptedMessagePacketJSON, EncryptedMessagePacketBuilder, _ENCRYPTED_MESSAGE_KEY);
        maybeAddParameterFromMaybeJSONToBuilderForParameterName(maybeEncryptedMessagePacketJSON, EncryptedMessagePacketBuilder ,_MESSAGE_SIGNATURE_KEY);
        maybeAddParameterFromMaybeJSONToBuilderForParameterName(maybeEncryptedMessagePacketJSON, EncryptedMessagePacketBuilder, _SENDER_USERCODE_KEY);
        return EncryptedMessagePacketBuilder.build();
    }

    private static void maybeAddParameterFromMaybeJSONToBuilderForParameterName(Maybe<JSON> maybeEncryptedMessagePacketJSON, EncryptedMessagePacket.Builder EncryptedMessagePacketBuilder, String parameterName) {
        Maybe<String> maybeEncryptedMessage = OperationsOnMaybeJSON.maybeGetStringInMaybeJSONForKey(maybeEncryptedMessagePacketJSON, parameterName);
        EncryptedMessagePacketBuilder.maybeSetParameterValueForKey(maybeEncryptedMessage, parameterName);
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------
    private EncryptedMessagePacket(String senderUsercode, String encryptedMessage, String messageSignature) {
        _senderUsercode = senderUsercode;
        _encryptedMessage = encryptedMessage;
        _messageSignature = messageSignature;
    }

    //--------------------------------------------------------------------------------------
    // BUILDER
    //--------------------------------------------------------------------------------------
    private static class Builder {

        private Map<String, Maybe<String>> parameterMap = MaybeMap.newMaybeMapWithAllKeysMappedToNothing();

        //--------------------------------------------------------------------------------------
        // PUBLIC
        //--------------------------------------------------------------------------------------
        public Builder() {
        }

        public void maybeSetParameterValueForKey(Maybe<String> maybeParameterValue, String parameterKey) {
            parameterMap.put(parameterKey, maybeParameterValue);
        }

        public Maybe<EncryptedMessagePacket> build() {
            if(_allParametersAreValidlySet()) {
                EncryptedMessagePacket newEncryptedMessagePacket = new EncryptedMessagePacket(parameterMap.get(_SENDER_USERCODE_KEY).object(), parameterMap.get(_ENCRYPTED_MESSAGE_KEY).object(), parameterMap.get(_MESSAGE_SIGNATURE_KEY).object());
                return Maybe.asObject(newEncryptedMessagePacket);
            } else {
                return Maybe.asNothing();
            }
        }

        //--------------------------------------------------------------------------------------
        // PRIVATE
        //--------------------------------------------------------------------------------------
        private boolean _allParametersAreValidlySet() {
            if( (parameterMap.get(_ENCRYPTED_MESSAGE_KEY).isNotNothing()) && (parameterMap.get(_MESSAGE_SIGNATURE_KEY).isNotNothing()) && (parameterMap.get(_SENDER_USERCODE_KEY).isNotNothing())) {
                return true;
            }
            return false;
        }
    }
}
