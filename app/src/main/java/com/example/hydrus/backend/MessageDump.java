package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam and Rob (2020)
//
//--------------------------------------------------------------------------------------------
import com.example.hydrus.general_purpose.Monad.Monad;
import com.example.hydrus.general_purpose.Monad.MonadicOperation;

import java.util.List;

import functional.Maybe;
import json.JSON;

public class MessageDump {

    private final List<EncryptedMessagePacket> _listOfEncryptedMessagePackets;

    //--------------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------------
    public static Maybe<MessageDump> maybeParseMessageDumpFromJSON(JSON jsonOfMessageDump) {
        Maybe<List<EncryptedMessagePacket>> maybeListOfEncryptedPacketsInMessageDump = MessageDump._maybeParseListOfEncryptedMessagePacketsFromJSON(jsonOfMessageDump);
        return maybeListOfEncryptedPacketsInMessageDump.applyGivenOperationOntoThisObjectMondically(new MonadicOperation<Monad<MessageDump>, List<EncryptedMessagePacket>, MessageDump>() {
            @Override
            public Maybe<MessageDump> performMonadicOperation(List<EncryptedMessagePacket> encryptedMessagePackets) {
                return Maybe.asObject(new MessageDump(encryptedMessagePackets));
            }
        });
    }

    //--------------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------------
    private static Maybe<List<EncryptedMessagePacket>> _maybeParseListOfEncryptedMessagePacketsFromJSON(JSON jsonOfListOfEncryptedPackets) {
        Maybe<JSON> maybeListOfEncryptedPacketsAsJSONArray =  Maybe.asObject(jsonOfListOfEncryptedPackets);
        return OperationsOnMaybeJSON.maybeMapMaybeJSONArrayIntoAListUsingGivenMaybeOperationAsMappingOperation(maybeListOfEncryptedPacketsAsJSONArray, new MonadicOperation<Monad<EncryptedMessagePacket>, JSON, EncryptedMessagePacket>() {
            @Override
            public Maybe<EncryptedMessagePacket> performMonadicOperation(JSON encryptedMessagePacketJSON) {
                return EncryptedMessagePacket.maybeParseEncryptedMessagePacketFromJSON(encryptedMessagePacketJSON);
            }
        });
    }

    //private static Maybe<JSON>

    //--------------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------------
    private MessageDump(List<EncryptedMessagePacket> listOfEncryptedMessagePackets) {
        this._listOfEncryptedMessagePackets = listOfEncryptedMessagePackets;
    }

    // The reason why we do not create the list in the constructor is because it is a maybe and
    // we cannot allow a constructor to fail.
}
