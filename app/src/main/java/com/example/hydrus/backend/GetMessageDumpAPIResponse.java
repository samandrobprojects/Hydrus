package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

import java.util.Collection;

import functional.Maybe;
import functional.Monad;
import json.JSON;
import functional.MonadicOperation;
import json.JSONArray;
import json.JSONMapping;
import json.JSONString;
import mapping.MapableOperation;

public class GetMessageDumpAPIResponse extends HydrusAPIResponseObject {

    private static final String MESSAGE_DUMP_API_KEY = "message_dump";

    //--------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------
    public Maybe<MessageDump> maybeGetMessageDump() {
        Maybe<JSON> maybeJSONRepresentationofMessageDump = this._maybeGetJSONFromPayloadWithKey(MESSAGE_DUMP_API_KEY);
        return maybeJSONRepresentationofMessageDump.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<MessageDump>, JSON, MessageDump>() {
            @Override
            public Maybe<MessageDump> performMonadicOperation(JSON jsonRepresentationofMessageDump) {
                return MessageDump.maybeParseMessageDumpFromJSON(jsonRepresentationofMessageDump);
            }
        });
    }

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static GetMessageDumpAPIResponse newGetMessageDumpAPIResponseFromJson(JSON jsonPublicKeyCollectionResponse) {
        return new GetMessageDumpAPIResponse(jsonPublicKeyCollectionResponse);
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------
    private GetMessageDumpAPIResponse(JSON jsonRegisterNewUserResponse) {
        super(jsonRegisterNewUserResponse);
    }

    public static class MessageDump {

        private final Collection<EncryptedMessagePacket> _CollectionOfEncryptedMessagePackets;

        //--------------------------------------------------------------------------------------------
        // PUBLIC STATIC
        //--------------------------------------------------------------------------------------------
        public static Maybe<MessageDump> maybeParseMessageDumpFromJSON(JSON jsonOfMessageDump) {
            Maybe<Collection<EncryptedMessagePacket>> maybeCollectionOfEncryptedPacketsInMessageDump = MessageDump._maybeParseCollectionOfEncryptedMessagePacketsFromJSON(jsonOfMessageDump);
            return maybeCollectionOfEncryptedPacketsInMessageDump.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<MessageDump>, Collection<EncryptedMessagePacket>, MessageDump>() {
                @Override
                public Maybe<MessageDump> performMonadicOperation(Collection<EncryptedMessagePacket> encryptedMessagePackets) {
                    return Maybe.asObject(new MessageDump(encryptedMessagePackets));
                }
            });
        }

        //--------------------------------------------------------------------------------------------
        // PRIVATE STATIC
        //--------------------------------------------------------------------------------------------
        private static Maybe<Collection<EncryptedMessagePacket>> _maybeParseCollectionOfEncryptedMessagePacketsFromJSON(JSON jsonOfCollectionOfEncryptedPackets) {
            Maybe<JSONArray> maybeCollectionOfEncryptedPacketsAsJSONArray = JSONArray.maybeJSONArrayFromJSON(jsonOfCollectionOfEncryptedPackets);
            return maybeCollectionOfEncryptedPacketsAsJSONArray.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<Collection<EncryptedMessagePacket>>, JSONArray, Collection<EncryptedMessagePacket>>() {
                @Override
                public Monad<Collection<EncryptedMessagePacket>> performMonadicOperation(JSONArray CollectionOfEncryptedPacketsAsJSONArray) {
                    return CollectionOfEncryptedPacketsAsJSONArray.maybeMapToCollectionWithOperation(new MapableOperation<EncryptedMessagePacket, JSON>() {
                        @Override
                        public Maybe<EncryptedMessagePacket> applyMapOperation(JSON encryptedMessagePacketJSON) {
                            return EncryptedMessagePacket.maybeParseEncryptedMessagePacketFromJSON(encryptedMessagePacketJSON);
                        }
                    });
                }
            });
        }

        //--------------------------------------------------------------------------------------------
        // PRIVATE
        //--------------------------------------------------------------------------------------------
        private MessageDump(Collection<EncryptedMessagePacket> CollectionOfEncryptedMessagePackets) {
            this._CollectionOfEncryptedMessagePackets = CollectionOfEncryptedMessagePackets;
        }

        public static class EncryptedMessagePacket {

            private static final String _ENCRYPTED_MESSAGE_KEY = "encrypted_message";
            private static final String _MESSAGE_SIGNATURE_KEY = "message_signature";
            private static final String _SENDER_USERCODE_KEY = "sender_usercode";

            private final String _senderUsercode;
            private final String _encryptedMessage;
            private final String _messageSignature;

            //--------------------------------------------------------------------------------------
            // PUBLIC STATIC
            //--------------------------------------------------------------------------------------
            /*public static Maybe<EncryptedMessagePacket> maybeParseEncryptedMessagePacketFromJSON(JSON encryptedMessagePacketJSON) {
                Maybe<JSONMapping> maybeEncryptedMessagePacketJSONMapping = JSONMapping.maybeJSONMappingFromJSON(encryptedMessagePacketJSON);
                return maybeEncryptedMessagePacketJSONMapping.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<EncryptedMessagePacket>, JSONMapping, EncryptedMessagePacket>() {
                    @Override
                    public Maybe<EncryptedMessagePacket> performMonadicOperation(final JSONMapping jsonMapping) {
                        return _maybeGetStringFromJSONMappingWithKey(jsonMapping, _ENCRYPTED_MESSAGE_KEY).applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<EncryptedMessagePacket>, String, EncryptedMessagePacket>() {
                            @Override
                            public Maybe<EncryptedMessagePacket> performMonadicOperation(final String encryptedMessage) {
                                return _maybeGetStringFromJSONMappingWithKey(jsonMapping, _MESSAGE_SIGNATURE_KEY).applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<EncryptedMessagePacket>, String, EncryptedMessagePacket>() {
                                    @Override
                                    public Maybe<EncryptedMessagePacket> performMonadicOperation(final String messageSignature) {
                                        return _maybeGetStringFromJSONMappingWithKey(jsonMapping, _SENDER_USERCODE_KEY).applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<EncryptedMessagePacket>, String, EncryptedMessagePacket>() {
                                            @Override
                                            public Maybe<EncryptedMessagePacket> performMonadicOperation(String senderUsercode) {
                                                return Maybe.asObject(new EncryptedMessagePacket(senderUsercode, encryptedMessage, messageSignature));
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }*/

            public static Maybe<EncryptedMessagePacket> maybeParseEncryptedMessagePacketFromJSON(JSON encryptedMessagePacketJSON) {
                Maybe<JSONMapping> maybeEncryptedMessagePacketJSONMapping = JSONMapping.maybeJSONMappingFromJSON(encryptedMessagePacketJSON);
                return maybeEncryptedMessagePacketJSONMapping.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<EncryptedMessagePacket>, JSONMapping, EncryptedMessagePacket>() {
                    @Override
                    public Maybe<EncryptedMessagePacket> performMonadicOperation(final JSONMapping jsonMapping) {
                        return _ads(jsonMapping);
                    }
                };
            }

            public static Maybe<EncryptedMessagePacket> _ads(JSONMapping jsonMapping) {
                EncryptedMessagePacket.Builder encryptedMessagePacketBuilder = new EncryptedMessagePacket.Builder();
                _maybeGetStringFromJSONMappingWithKey(jsonMapping, _ENCRYPTED_MESSAGE_KEY).applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<EncryptedMessagePacket>, String, EncryptedMessagePacket>() {
                    @Override
                    public Maybe<EncryptedMessagePacket> performMonadicOperation(final String encryptedMessage) {
                        encryptedMessagePacketBuilder.withEncryptedMessage(encryptedMessage);
                    }
                });
                _maybeGetStringFromJSONMappingWithKey(jsonMapping, _MESSAGE_SIGNATURE_KEY).applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<EncryptedMessagePacket>, String, EncryptedMessagePacket>() {
                    @Override
                    public Maybe<EncryptedMessagePacket> performMonadicOperation(final String messageSignature) {
                        encryptedMessagePacketBuilder.withMessageSignature(messageSignature);
                    }
                });
                _maybeGetStringFromJSONMappingWithKey(jsonMapping, _SENDER_USERCODE_KEY).applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<EncryptedMessagePacket>, String, EncryptedMessagePacket>() {
                    @Override
                    public Maybe<EncryptedMessagePacket> performMonadicOperation(final String senderUsercode) {
                        encryptedMessagePacketBuilder.withSenderUsercode(senderUsercode);
                    }
                });
                return encryptedMessagePacketBuilder.build();
            }

            //--------------------------------------------------------------------------------------
            // PRIVATE STATIC
            //--------------------------------------------------------------------------------------
            private static Maybe<String> _maybeGetStringFromJSONMappingWithKey(JSONMapping jsonMappingInQuestion, String keyInQuestion) {
                return jsonMappingInQuestion.maybeGetJSONStringForGivenKeyInMapping(keyInQuestion)
                        .applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<String>, JSONString, String>() {
                            @Override
                            public Maybe<String> performMonadicOperation(JSONString jsonString) {
                                return Maybe.asObject(jsonString.getAsString());
                            }
                        });
            }

            //--------------------------------------------------------------------------------------
            // PRIVATE
            //--------------------------------------------------------------------------------------
            private EncryptedMessagePacket(String senderUsercode, String encryptedMessage, String messageSignature) {
                _senderUsercode = senderUsercode;
                _encryptedMessage = encryptedMessage;
                _messageSignature = messageSignature;
            }

            public static class Builder {

                private String _senderUsercode;
                private String _encryptedMessage;
                private String _messageSignature;

                public Builder() {
                    _senderUsercode = null;
                    _encryptedMessage = null;
                    _messageSignature = null;
                }

                public Builder withSenderUsercode(String senderUsercode){
                    _senderUsercode = senderUsercode;
                    return this;
                }

                public Builder withEncryptedMessage(String encryptedMessage){
                    _encryptedMessage = encryptedMessage;
                    return this;
                }

                public Builder withMessageSignature(String messageSignature){
                    _messageSignature = messageSignature;
                    return this;
                }

                public Maybe<EncryptedMessagePacket> build() {
                    if (_senderUsercode != null && _encryptedMessage != null &&  _messageSignature != null) {
                        return Maybe.asObject(new EncryptedMessagePacket(_senderUsercode, _encryptedMessage, _messageSignature));
                    } else {
                        return Maybe.asNothing();
                    }
                }
            }
        }
    }

}
