package com.example.hydrus.backend;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//--------------------------------------------------------------------------------------------

public class GetMessageDumpAPIResponse extends HydrusAPIResponseObject {

    public static final Maybe<MessageDump> NO_MESSAGE_DUMP_RETRIEVED_FROM_SERVER = Maybe.asNothing();

    //--------------------------------------------------------------------------------------
    // PUBLIC
    //--------------------------------------------------------------------------------------
    public Maybe<MessageDump> maybeGetMessageDump() {
        Maybe<JSON> maybeJSONRepresentationofMessageDump = this._maybeGetJSONRepresentationForKeyWithinPayloadForName("message_dump");
        if (maybeJSONRepresentationofMessageDump.isNotNothing()) {
            Maybe<MessageDump> maybeMessageDumpParsedFromJSON = MessageDump.maybeParseMessageDumpFromJSON(maybeJSONRepresentationofMessageDump.object());
            return maybeMessageDumpParsedFromJSON;
        } else {
            return NO_MESSAGE_DUMP_RETRIEVED_FROM_SERVER;
        }
    }

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static GetMessageDumpAPIResponse newGetMessageDumpAPIResponseFromJson(JSON jsonPublicKeyListResponse) {
        return new GetMessageDumpAPIResponse(jsonPublicKeyListResponse);
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE
    //--------------------------------------------------------------------------------------
    private GetMessageDumpAPIResponse(JSON jsonRegisterNewUserResponse) {
        super(jsonRegisterNewUserResponse);
    }
}
