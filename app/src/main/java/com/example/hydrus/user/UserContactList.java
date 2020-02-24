package com.example.hydrus.user;
//--------------------------------------------------------------------------------------------
//
// AUTHOR
// ------
// Sam And Rob (2020)
//
//----------------------S----------------------------------------------------------------------
import com.example.hydrus.general_purpose.Pair;
import com.example.hydrus.system.ApplicationExceptionAuthority;
import com.example.hydrus.system.LocalStorage;
import com.example.hydrus.security.secure_memory.ConstantSecureMemory;
import com.example.hydrus.utilities.Base64Converter;
import com.example.hydrus.utilities.StringByteConverter;

import java.util.HashMap;
import java.util.List;

import functional.Maybe;

public class UserContactList {

    private static final String _CONTACT_LIST_LOCAL_STORAGE_KEY = "_CONTACT_LIST_LOCAL_STORAGE_KEY";
    private static final String _CONTACT_LIST_STRING_SEPERATION_CHARACTER = "@";

    private static HashMap<ConstantSecureMemory,ConstantSecureMemory> _contactNameToPublicKeyHashMap = new HashMap<ConstantSecureMemory,ConstantSecureMemory>();

    //--------------------------------------------------------------------------------------
    // PUBLIC STATIC
    //--------------------------------------------------------------------------------------
    public static void setupContactList() {
        Maybe<String> maybeContactListStorageString = LocalStorage.getStringWithKey(_CONTACT_LIST_LOCAL_STORAGE_KEY);
        if (maybeContactListStorageString.isNotNothing()) {
            _setupContactListWithContactListStorageString(maybeContactListStorageString.object());
        }
    }

    public static void setListOfUsercodeAndPublicKeyPairsAsContactList(List<Pair<String, String>> listOfPairsOfUsercodeAndPublicKey) {
        _contactNameToPublicKeyHashMap = new HashMap<ConstantSecureMemory,ConstantSecureMemory>();
        for(Pair<String, String> usercodeAndPublicKeyPair : listOfPairsOfUsercodeAndPublicKey) {
            _setupContactForUsercodeStringAndBase64PublicKey(usercodeAndPublicKeyPair.getFirst(), usercodeAndPublicKeyPair.getSecond());
        }
        _updateStoredKeys();
    }

    public static Maybe<ConstantSecureMemory> getPublicKeyForUsercode(ConstantSecureMemory usercode) {
        ConstantSecureMemory foundPublicKeyOrNullIfNotFound = _contactNameToPublicKeyHashMap.get(usercode);
        if (foundPublicKeyOrNullIfNotFound != null) {
            return Maybe.asObject(foundPublicKeyOrNullIfNotFound);
        }
        return Maybe.asNothing();
    }

    //--------------------------------------------------------------------------------------
    // PRIVATE STATIC
    //--------------------------------------------------------------------------------------
    private static void _updateStoredKeys() {
        String contactListStorageString = _getUpdatedUserContactListString();
        LocalStorage.storeStringWithKey(contactListStorageString, _CONTACT_LIST_LOCAL_STORAGE_KEY);
    }

    private static void _setupContactFromContactListStorageComponentsWithGivenIndex(String[] contactListStorageStringComponents, int contactListComponentsIndex) {
        String usercodeAsString = contactListStorageStringComponents[contactListComponentsIndex];
        String base64PublicKey = contactListStorageStringComponents[contactListComponentsIndex + 1];
        _setupContactForUsercodeStringAndBase64PublicKey(usercodeAsString, base64PublicKey);
    }

    private static void _setupContactForUsercodeStringAndBase64PublicKey(String usercodeAsString, String base64PublicKey) {
        byte[] usercodeAsBytes = StringByteConverter.convertStringToByteArray(usercodeAsString);
        byte[] publicKeyAsBytes = Base64Converter.decryptBASE64(base64PublicKey);
        ConstantSecureMemory usercodeInSecureMemory = ConstantSecureMemory.secureMemoryFromBytes(usercodeAsBytes);
        ConstantSecureMemory publicKeyInSecureMemory = ConstantSecureMemory.secureMemoryFromBytes(publicKeyAsBytes);
        _contactNameToPublicKeyHashMap.put(usercodeInSecureMemory, publicKeyInSecureMemory);
    }

    private static void _setupContactListWithContactListStorageString(String contactListStorageString) {
        String[] contactListStorageStringComponents = contactListStorageString.split(_CONTACT_LIST_STRING_SEPERATION_CHARACTER);
        for (int contactListStorageStringComponentIndex = 0; contactListStorageStringComponentIndex < contactListStorageStringComponents.length - 1; contactListStorageStringComponentIndex++) {
            _setupContactFromContactListStorageComponentsWithGivenIndex(contactListStorageStringComponents, contactListStorageStringComponentIndex);
        }
    }

    private static String _getContactStorageListStringForContactWithUsercode(ConstantSecureMemory secureUsercode) {
        ConstantSecureMemory foundPublicKeyOrNullIfNotFound = _contactNameToPublicKeyHashMap.get(secureUsercode);
        if (foundPublicKeyOrNullIfNotFound != null) {
            return _getContactStorageListStringForContactWithUsercodeAndPublicKey(secureUsercode, foundPublicKeyOrNullIfNotFound);
        } else {
            ApplicationExceptionAuthority.EXCEPTION_InvalidContactListSetupException();
            return null;
        }
    }

    private static String _getContactStorageListStringForContactWithUsercodeAndPublicKey(ConstantSecureMemory secureUsercode, ConstantSecureMemory securePublicKey) {
        String publicKeyAsString = Base64Converter.encryptBASE64(securePublicKey.getByteRepresentation());
        String usercodeAsString = StringByteConverter.convertByteArrayToString(secureUsercode.getByteRepresentation());
        return _CONTACT_LIST_STRING_SEPERATION_CHARACTER + usercodeAsString + _CONTACT_LIST_STRING_SEPERATION_CHARACTER + publicKeyAsString;

    }

    private static String _getUpdatedUserContactListString() {
        String contactListStorageString = "";
        for (ConstantSecureMemory usercode: _contactNameToPublicKeyHashMap.keySet()) {
            contactListStorageString += _getContactStorageListStringForContactWithUsercode(usercode);
        }
        return contactListStorageString;
    }
}
